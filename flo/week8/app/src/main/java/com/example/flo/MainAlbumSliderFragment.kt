package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.databinding.FragmentHomeAlbumsliderBinding

class MainAlbumSliderFragment(val imgRes : Int) : Fragment() {

    lateinit var binding : FragmentHomeAlbumsliderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeAlbumsliderBinding.inflate(inflater, container, false)

        binding.homeMainAlbumSliderIv.setImageResource(imgRes)
        return binding.root
    }
}