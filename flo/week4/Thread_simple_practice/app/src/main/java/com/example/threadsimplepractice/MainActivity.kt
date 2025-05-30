package com.example.threadsimplepractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.threadsimplepractice.ui.theme.ThreadSimplePracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a = A()
        val b = B()

        a.start()
        a.join()
        b.start()
    }

    class A : Thread(){
        override fun run() {
            super.run()
            for (i in 1..1000){
                Log.d("test", "first : $i")
            }
        }
    }

    class B : Thread(){
        override fun run() {
            super.run()
            for (i in 1000 downTo 1){
                Log.d("test", "second : $i")
            }
        }
    }
}