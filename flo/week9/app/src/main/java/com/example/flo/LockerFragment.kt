package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private val information = arrayListOf("저장한 곡", "음악파일", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        binding.lockerLoginBtnTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        binding.lockerSelectAllTv.setOnClickListener {
            val bottomSheet = SavedSongBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)

            // 텍스트 변경
            binding.lockerSelectAllTv.text = "선택해제"

            // 텍스트 색상 변경
            val selectColor = ContextCompat.getColor(requireContext(), R.color.flo)
            binding.lockerSelectAllTv.setTextColor(selectColor)
            
            //이미지 변경
            binding.btnPlaylistSelectOnImgIv.visibility = View.VISIBLE

            //닫혔을 때 다시 원래 상태로 복구
            parentFragmentManager.setFragmentResultListener("resetSelectAll", this) { _, bundle ->
                val shouldReset = bundle.getBoolean("shouldResetSelectAll")
                if (shouldReset) {
                    binding.lockerSelectAllTv.text = "전체선택"
                    binding.lockerSelectAllTv.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.colorPrimaryGrey)
                    )
                    // 아이콘 원래대로 복구
                    binding.btnPlaylistSelectOnImgIv.visibility = View.GONE
                    binding.btnPlaylistSelectOnImgIv.setImageResource(R.drawable.btn_playlist_select_off)
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }

    private fun initViews() {
        val jwt: Int = getJwt()
        if (jwt == 0) {
            binding.lockerLoginBtnTv.text = "로그인"
            binding.lockerLoginBtnTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else {
            binding.lockerLoginBtnTv.text = "로그아웃"
            binding.lockerLoginBtnTv.setOnClickListener {
                //로그아웃
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.apply()
    }
}