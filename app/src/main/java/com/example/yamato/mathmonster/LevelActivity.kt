package com.example.yamato.mathmonster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_level.*
import android.content.Intent

class LevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        var select = intent.getIntExtra("number", 0)
        var back_sel = intent.getIntExtra("backMath", 0)
        var result_back = intent.getIntExtra("BACK_LEVEL_RESULT", 0)
        var select_level = 0
        if(select == 1 || back_sel == 1 || result_back == 1){
            choice_math.text = "たしざん"
        }else if (select == 2 || back_sel == 2 || result_back == 2){
            choice_math.text = "ひきざん"
        }

        if(select == 0){
            select = back_sel
        }

        val intent = Intent(this@LevelActivity, MonsterActivity::class.java)
        val back = Intent(this@LevelActivity, MainActivity::class.java)
        intent.putExtra("Number", select)

        lev1.setOnClickListener {
            select_level = 1
            intent.putExtra("selLev", select_level)
            startActivity(intent)
        }
        lev2.setOnClickListener {
            select_level = 2
            intent.putExtra("selLev", select_level)
            startActivity(intent)
        }
        lev3.setOnClickListener {
            select_level = 3
            intent.putExtra("selLev", select_level)
            startActivity(intent)
        }
        lev4.setOnClickListener {
            select_level = 4
            intent.putExtra("selLev", select_level)
            startActivity(intent)
        }
        lev5.setOnClickListener {
            select_level = 5
            intent.putExtra("selLev", select_level)
            startActivity(intent)
        }
        levmix.setOnClickListener {
            select_level = 6
            intent.putExtra("selLev", select_level)
            startActivity(intent)
        }
        backBtn.setOnClickListener {
            startActivity(back)
        }

    }
}
