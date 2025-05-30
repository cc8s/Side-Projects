package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums()) // songDB에서 album list를 가져옵니다.

        //더미데이터와 Adapter연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)

        // 리사이클러뷰에 어댑터를 연결
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

            override fun onPlayClick(album: Album) {
                val song = songDB.songDao().getSongs().firstOrNull { it.albumIdx == album.id }

                if (song != null) {
                    val editor = requireContext().getSharedPreferences("song", AppCompatActivity.MODE_PRIVATE).edit()
                    editor.putInt("songId", song.id)
                    editor.apply()

                    song.isPlaying = true
                    (context as MainActivity).setMiniPlayer(song)
                    (context as MainActivity).song = song
                    (context as MainActivity).sendServiceCommand("play", song.music)
                } else {
                    Toast.makeText(context, "앨범에 수록된 곡이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })
        // 레이아웃 매니저 설정
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeMainViewpagerIv.adapter = bannerAdapter
        binding.homeMainViewpagerIv.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val albumSliderAdapter = AlbumSliderVPAdapter(this)
        albumSliderAdapter.addFragment(MainAlbumSliderFragment(R.drawable.img_first_album_default))
        albumSliderAdapter.addFragment(MainAlbumSliderFragment(R.drawable.img_second_album_default))
        albumSliderAdapter.addFragment(MainAlbumSliderFragment(R.drawable.img_third_album_default))
        binding.homePannelBackgroundVp.adapter = albumSliderAdapter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // Indicator에 viewPager 설정
        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}