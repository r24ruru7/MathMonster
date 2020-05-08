package com.example.yamato.mathmonster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_monster.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.animation.RotateAnimation
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build





class MonsterActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool       //音声流すためのやつ
    private var MonsterSound = 0    //音声ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster)

        var m_sel = intent.getIntExtra("Number", 1)
        var select = intent.getIntExtra("selLev", 1)
        var level_cnt = intent.getIntExtra("PLAYLEV", 1)
        var false_count = intent.getIntExtra("BREAKACT_FALSE", 0)
        var time_receive = intent.getLongExtra("Timer", 0)
        Levels(select, m_sel, level_cnt)

        val intent = Intent(this@MonsterActivity, MathActivity::class.java)
        intent.putExtra("SelLev", select)
        intent.putExtra("NUmber", m_sel)
        intent.putExtra("playlev", level_cnt)
        intent.putExtra("monsterAct_false", false_count)
        intent.putExtra("Timer", time_receive)

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

       MonsterSound = soundPool.load(this, R.raw.monster, 1)       //モンスター登場

        val rotate = RotateAnimation(
            0f,
            15f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ) // imgの中心を軸に、0度から360度にかけて回転
        val rotate2 = RotateAnimation(
            15f,
            -15f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        val rotate3 = RotateAnimation(
            -15f,
            0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotate.duration = 500 // 3000msかけてアニメーションする
        rotate2.duration = 1000
        rotate3.duration = 500

        //音楽のロードを確認するまでループ
        var streamID = 0
        do {
            //少し待ち時間を入れる
            try {
                Thread.sleep(10)
            } catch (e: InterruptedException) {
            }

            //ボリュームをゼロにして再生して戻り値をチェック
            streamID = soundPool.play(MonsterSound, 0.0f, 0.0f, 1, 0, 1.0f)
        } while (streamID == 0)
        //確認

        soundPool.play(MonsterSound, 1.0f, 1.0f, 0, 0, 1.0f)

        subimage.startAnimation(rotate) // アニメーション適用
        Handler().postDelayed(Runnable {
            subimage.startAnimation((rotate2))
            Handler().postDelayed(Runnable{
                subimage.startAnimation((rotate3))
            }, 1000)
        }, 550)

        var whichs = 0
        whichs = fadein()
        if (whichs == 1) {
            ground0.setOnClickListener {
                startActivity(intent)
            }
        }


    }

    private  fun Levels(select:Int, m_sel:Int, level_cnt:Int){
        if(m_sel == 1) {
            when (select) {
                1 -> {
                    leveltext.text = "レベル1"
                    subimage.setImageResource(R.drawable.monster1)
                }
                2 -> {
                    leveltext.text = "レベル2"
                    subimage.setImageResource(R.drawable.monster2)
                }
                3 -> {
                    leveltext.text = "レベル3"
                    subimage.setImageResource(R.drawable.monster3)
                }
                4 -> {
                    leveltext.text = "レベル4"
                    subimage.setImageResource(R.drawable.monster4)
                }
                5 -> {
                    leveltext.text = "レベル5"
                    subimage.setImageResource(R.drawable.monster5)
                }
                6 -> {
                    when(level_cnt){
                        1 -> {
                            subimage.setImageResource(R.drawable.monster1)
                            leveltext.text = "レベル1"
                        }
                        2 -> {
                            subimage.setImageResource(R.drawable.monster2)
                            leveltext.text = "レベル２"
                        }
                        3 -> {
                            subimage.setImageResource(R.drawable.monster3)
                            leveltext.text = "レベル３"
                        }
                        4 -> {
                            subimage.setImageResource(R.drawable.monster4)
                            leveltext.text = "レベル４"
                        }
                        5 -> {
                            subimage.setImageResource(R.drawable.monster5)
                            leveltext.text = "レベル５"
                        }
                    }
                }
            }
        }else if(m_sel == 2){
            when (select) {
                1 -> {
                    leveltext.text = "レベル1"
                    subimage.setImageResource(R.drawable.monster6)
                }
                2 -> {
                    leveltext.text = "レベル2"
                    subimage.setImageResource(R.drawable.monster7)
                }
                3 -> {
                    leveltext.text = "レベル3"
                    subimage.setImageResource(R.drawable.monster8)
                }
                4 -> {
                    leveltext.text = "レベル4"
                    subimage.setImageResource(R.drawable.monster9)
                }
                5 -> {
                    leveltext.text = "レベル5"
                    subimage.setImageResource(R.drawable.monster10)
                }
                6 -> {
                    when(level_cnt){
                        1 -> {
                            subimage.setImageResource(R.drawable.monster6)
                            leveltext.text = "レベル1"
                        }
                        2 -> {
                            subimage.setImageResource(R.drawable.monster7)
                            leveltext.text = "レベル２"
                        }
                        3 -> {
                            subimage.setImageResource(R.drawable.monster8)
                            leveltext.text = "レベル３"
                        }
                        4 -> {
                            subimage.setImageResource(R.drawable.monster9)
                            leveltext.text = "レベル４"
                        }
                        5 -> {
                            subimage.setImageResource(R.drawable.monster10)
                            leveltext.text = "レベル５"
                        }
                    }
                }
            }
        }
    }

    private fun fadeout(): Int {
        // 透明度を1から0に変化
        val alphaFadeout = AlphaAnimation(1.0f, 0.0f)
        // animation時間 msec
        alphaFadeout.duration = 3000
        // animationが終わったそのまま表示にする
        alphaFadeout.fillAfter = true

        touchtext.startAnimation(alphaFadeout)
        return 1
    }

    private fun fadein(): Int {
        // 透明度を1から0に変化
        val alphaFadeout = AlphaAnimation(0.0f, 1.0f)
        // animation時間 msec
        alphaFadeout.duration = 3000
        // animationが終わったそのまま表示にする
        alphaFadeout.fillAfter = true

        touchtext.startAnimation(alphaFadeout)
        return 1
    }

    override fun onDestroy() {
        soundPool.release ()
        super.onDestroy()
    }
}
