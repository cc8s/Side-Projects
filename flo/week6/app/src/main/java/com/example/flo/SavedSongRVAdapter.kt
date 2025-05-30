package com.example.flo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemLockerAlbumBinding

class SavedSongRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    // RecyclerView 뷰를 재활용(recycle)하는 특성 때문에 발생하는 UI 상태 보존 문제(일시정지 스크롤->재생) 계속 초기화됨
    //재생 중인 곡(아이템) 위치 저장 //*wallnut은 재생/일정을 Lockerfh 나눠줌 <- 고려해볼것
    private var playingPosition = -1

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }
    //removeitem 문제해결(한칸씩 밀려올라가며 일시정지 그대로 남아있음)
    fun removeItem(position: Int){
        albumList.removeAt(position)

        //현재 재생 중인 아이템 삭제하면 초기화
        if (position == playingPosition)
            playingPosition = -1
        else if (position < playingPosition){
        // 삭제된 아이템이 현재 재생 아이템보다 위에 있으면 인덱스 조정
            playingPosition -= 1
    }
        notifyDataSetChanged()
        notifyItemRangeChanged(position, albumList.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedSongRVAdapter.ViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position], position)
//        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumList[position]) }
        //...누르면 삭제 코드
        holder.binding.itemAlbumMoreIv.setOnClickListener{ mItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album, position: Int){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumImgIv.setImageResource(album.coverImg!!)

// 상태 초기화 (뷰 재활용 대응)
            if (position == playingPosition) {
                binding.itemAlbumPlayIv.visibility = View.GONE
                binding.itemAlbumPauseIv.visibility = View.VISIBLE
            } else {
                binding.itemAlbumPlayIv.visibility = View.VISIBLE
                binding.itemAlbumPauseIv.visibility = View.GONE
            }

            binding.itemAlbumPlayIv.setOnClickListener {
                val previousPosition = playingPosition
                playingPosition = position
                notifyItemChanged(previousPosition) // 이전 재생 아이템 UI 복구
                notifyItemChanged(playingPosition)  // 현재 재생 아이템 UI 갱신
            }

            binding.itemAlbumPauseIv.setOnClickListener {
                val previousPosition = playingPosition
                playingPosition = -1
                notifyItemChanged(previousPosition)
            }
        }
    }

}