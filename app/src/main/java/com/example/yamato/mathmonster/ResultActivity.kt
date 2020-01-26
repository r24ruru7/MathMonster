package com.example.yamato.mathmonster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var m_sel = intent.getIntExtra("clear_math", 1)
        var false_count = intent.getIntExtra("breakAct_false", 0)
        var time_receive = intent.getLongExtra("Timer", 0)

        val move_title = Intent(this@ResultActivity, MainActivity::class.java)
        val move_level = Intent(this@ResultActivity, LevelActivity::class.java)
        move_level.putExtra("BACK_LEVEL_RESULT", m_sel)

        count2.text = false_count.toString()

        var match_time = 0L
        for(i in 1..time_receive){
            if(i % 60  == 0L){
                match_time ++
            }
        }
        time_receive = time_receive - (match_time * 60L)
        textView3.text = match_time.toString() + "ふん" + time_receive.toString() + "びょう"
        move_level.putExtra("Timer", 0)
        move_title.putExtra("Timer", 0)

        titleBtn.setOnClickListener {
            startActivity(move_title)
        }

        levBtn.setOnClickListener {
            startActivity(move_level)
        }
    }
}
