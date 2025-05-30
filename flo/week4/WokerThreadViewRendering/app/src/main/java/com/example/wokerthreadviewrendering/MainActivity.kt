package com.example.wokerthreadviewrendering

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wokerthreadviewrendering.databinding.ActivityMainBinding
import com.example.wokerthreadviewrendering.ui.theme.WokerThreadViewRenderingTheme

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = Handler(Looper.getMainLooper())

        val imageList = arrayListOf<Int>()

        imageList.add(R.drawable.bear)
        imageList.add(R.drawable.duke)
        imageList.add(R.drawable.just)
        imageList.add(R.drawable.bear)
        imageList.add(R.drawable.duke)
        imageList.add(R.drawable.just)
        imageList.add(R.drawable.bear)
        imageList.add(R.drawable.duke)
        imageList.add(R.drawable.just)

        Thread{
            for (image in imageList) {
                runOnUiThread(){
                    binding.iv.setImageResource(image)
                }

                Thread.sleep(2000)
            }
        }.start()

    }
}