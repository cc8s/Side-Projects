package com.example.roomdbpractice

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
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
import com.example.roomdbpractice.databinding.ActivityMainBinding
import com.example.roomdbpractice.ui.theme.RoomDBPracticeTheme

class MainActivity : AppCompatActivity() {

    var list = ArrayList<Profile>()
    lateinit var customAdapter: CustomAdapter
    lateinit var db: ProfileDatabase

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ProfileDatabase.getInstance(this)!!
        Thread {
            val savedContacts = db.profileDao().getAll()
            if (savedContacts.isNotEmpty()) {
                list.addAll(savedContacts)
            }
        }.start()

        binding.button.setOnClickListener {
            Thread {
                list.add(Profile("준", "25", "010-1234-5678"))
                db.profileDao().insert(Profile("준", "25", "010-1234-5678"))

                val list = db.profileDao().getAll()
                Log.d("Insterted Primary Key", list[list.size - 1].id.toString())
            }.start()
            customAdapter.notifyDataSetChanged()
        }

        customAdapter = CustomAdapter(list, this)

        binding.mainProfileLv.adapter = customAdapter
    }
}
