package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.flo.databinding.FragmentSongBinding
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album")
        val album = Gson().fromJson(albumJson, Album::class.java)

        isLiked = isLikedAlbum(album.id)
        setInit(album)
        setOnClickListeners(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album){
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        if (isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }

    private fun likeAlbum(userId : Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId: Int): Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId: Int? = songDB.albumDao().isLikedAlbum(userId, albumId)

        return likeId != null
    }

    private fun disLikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    private fun setOnClickListeners(album : Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if (isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            } else {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
            isLiked = !isLiked
        }
    }

    override fun onResume() {
        super.onResume()

        val albumJson = arguments?.getString("album") ?: return
        val album = Gson().fromJson(albumJson, Album::class.java)

        isLiked = isLikedAlbum(album.id)  // ✅ 최신 좋아요 상태 재확인
        Log.d("AlbumFragment", "albumId=${album.id}, isLiked=$isLiked")
        // ✅ 좋아요 상태 UI 갱신
        if (isLiked) {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }



}