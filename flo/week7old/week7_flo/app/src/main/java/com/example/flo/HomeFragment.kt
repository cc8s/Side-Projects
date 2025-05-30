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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //데이터 리스트 생성 더미 데이터 !중요 miniplayer로 넘겨줘야 하므로 Album.kt뿐만이 아니라 song.kt에도 동시에 저장
        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp, arrayListOf(Song("Butter", "방탄소년단 (BTS)", 0, 210, false, "music_butter"))))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2, arrayListOf(Song("라일락", "아이유 (IU)", 0, 210, false, "music_lilac"))))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3, arrayListOf(Song("Next Level", "에스파 (AESPA)", 0, 210, false, "music_next"))))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4, arrayListOf(Song("Boy with Luv", "방탄소년단 (BTS)", 0, 210, false, "music_boy"))))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5, arrayListOf(Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 210, false, "music_bboom"))))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6, arrayListOf(Song("Weekend", "태연 (Tae Yeon)", 0, 210, false, "music_flu"))))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
            //miniplayer 빈영 구현
            override fun onPlayClick(album: Album) {
                val song = album.songs?.firstOrNull() ?: return
                val songWithCover = song.copy(coverImg = album.coverImg)

                val songDao = SongDatabase.getInstance(requireContext())!!.songDao()

                val existingSong = songDao.getSongs().find {
                    it.title == songWithCover.title && it.singer == songWithCover.singer
                }

                val songToPlay = existingSong ?: run {
                    songDao.insert(songWithCover)
                    songDao.getSongs().last()
                }

                val sharedPreferences = requireActivity().getSharedPreferences("song", AppCompatActivity.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putInt("songId", songToPlay.id)
                    apply()
                }

                (activity as MainActivity).song = songToPlay
                (activity as MainActivity).playSong(songToPlay)
            }
        })



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