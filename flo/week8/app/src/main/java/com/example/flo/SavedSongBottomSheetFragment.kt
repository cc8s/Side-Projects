package com.example.flo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.flo.databinding.BottomsheetLockerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog


class SavedSongBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding : BottomsheetLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetLockerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomSheetIv1.setOnClickListener {
            Toast.makeText(requireActivity(), "듣기 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.bottomSheetIv2.setOnClickListener {
            Toast.makeText(requireActivity(), "재생목록 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.bottomSheetIv3.setOnClickListener {
            Toast.makeText(requireActivity(), "내 리스트 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.bottomSheetIv4.setOnClickListener {
            val result = Bundle().apply {
                putBoolean("shouldResetSelectAll", true)
            }
            parentFragmentManager.setFragmentResult("resetSelectAll", result)
            dismiss()
        }
    }
}