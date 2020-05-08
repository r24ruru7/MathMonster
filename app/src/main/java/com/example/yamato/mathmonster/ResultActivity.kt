package com.example.yamato.mathmonster

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class ResultActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool       //音声流すためのやつ
    private var GreatSound = 0    //音声ID

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

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {        //アンドロイドのバージョンがlolipop以前か
            soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
        } else {
            val attr = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            soundPool = SoundPool.Builder()
                .setAudioAttributes(attr)
                //パラメーターはリソースの数に合わせる
                .setMaxStreams(2)
                .build()
        }

        GreatSound = soundPool.load(this, R.raw.break_monster, 1)

        //音楽のロードを確認するまでループ
        var streamID = 0
        do {
            //少し待ち時間を入れる
            try {
                Thread.sleep(10)
            } catch (e: InterruptedException) {
            }

            //ボリュームをゼロにして再生して戻り値をチェック
            streamID = soundPool.play(GreatSound, 0.0f, 0.0f, 1, 0, 1.0f)
        } while (streamID == 0)
        //確認

        soundPool.play(GreatSound, 1.0f, 1.0f, 0, 0, 1.0f)

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

    override fun onDestroy() {
        soundPool.release ()
        super.onDestroy()
    }
}
