package com.example.yamato.mathmonster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this@MainActivity, LevelActivity::class.java)

        plusbtn.setOnClickListener {
            intent.putExtra("number", 1)
            startActivity(intent)
        }

        minusbtn.setOnClickListener {
            intent.putExtra("number", 2)
            startActivity(intent)
        }

    }
}
