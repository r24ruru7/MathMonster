package com.example.yamato.mathmonster

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_break.*
import android.view.animation.Animation
import android.view.animation.AlphaAnimation
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.view.animation.AnimationSet

class BreakActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool       //音声流すためのやつ
    private var MonsterSound = 0    //音声ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_break)

        var lev = intent.getIntExtra("SELLEv", 1)
        var m_sel = intent.getIntExtra("NUMber", 1)
        var level_cnt = intent.getIntExtra("PLAYlev", 1)
        var false_count = intent.getIntExtra("mathAct_false", 0)
        var time_receive = intent.getLongExtra("Timer", 0)

        val intent = Intent(this@BreakActivity, LevelActivity::class.java)
        val mix = Intent(this@BreakActivity, MonsterActivity::class.java)
        val result = Intent(this@BreakActivity, ResultActivity::class.java)
        intent.putExtra("backMath",m_sel)
        mix.putExtra("Number", m_sel)
        mix.putExtra("selLev", lev)
        mix.putExtra("BREAKACT_FALSE", false_count)
        mix.putExtra("Timer", time_receive)
        result.putExtra("clear_math", m_sel)
        result.putExtra("breakAct_false", false_count)
        result.putExtra("Timer", time_receive)

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

        MonsterSound = soundPool.load(this, R.raw.break_monster, 1)

        Levels(lev, m_sel, level_cnt)

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

        YARARETA()
        KURUKURU()

        level_cnt +=1
        if(level_cnt ==6)lev = 10
        mix.putExtra("PLAYLEV", level_cnt)

        var whichs = 0
        whichs = fadein()
        if(whichs == 1) {
            lastground.setOnClickListener {
                when(lev){
                    1,2,3,4,5 -> startActivity(intent)
                    6 -> startActivity(mix)
                    10 -> startActivity(result)
                }
            }
        }



    }

    private  fun Levels(select:Int, m_sel:Int, level_cnt:Int){
        if(m_sel == 1) {
            when (select) {
                1 -> {
                    breakimage.setImageResource(R.drawable.monster1)
                }
                2 -> {
                    breakimage.setImageResource(R.drawable.monster2)
                }
                3 -> {
                    breakimage.setImageResource(R.drawable.monster3)
                }
                4 -> {
                    breakimage.setImageResource(R.drawable.monster4)
                }
                5 -> {
                    breakimage.setImageResource(R.drawable.monster5)
                }
                6 -> {
                    when(level_cnt){
                        1 -> {
                            breakimage.setImageResource(R.drawable.monster1)
                        }
                        2 -> {
                            breakimage.setImageResource(R.drawable.monster2)
                        }
                        3 -> {
                            breakimage.setImageResource(R.drawable.monster3)
                        }
                        4 -> {
                            breakimage.setImageResource(R.drawable.monster4)
                        }
                        5 -> {
                            breakimage.setImageResource(R.drawable.monster5)
                        }
                    }
                }
            }
        }else if(m_sel == 2){
            when (select) {
                1 -> {
                    breakimage.setImageResource(R.drawable.monster6)
                }
                2 -> {
                    breakimage.setImageResource(R.drawable.monster7)
                }
                3 -> {
                    breakimage.setImageResource(R.drawable.monster8)
                }
                4 -> {
                    breakimage.setImageResource(R.drawable.monster9)
                }
                5 -> {
                    breakimage.setImageResource(R.drawable.monster10)
                }
                6 -> {
                    when(level_cnt){
                        1 -> {
                            breakimage.setImageResource(R.drawable.monster6)
                        }
                        2 -> {
                            breakimage.setImageResource(R.drawable.monster7)
                        }
                        3 -> {
                            breakimage.setImageResource(R.drawable.monster8)
                        }
                        4 -> {
                            breakimage.setImageResource(R.drawable.monster9)
                        }
                        5 -> {
                            breakimage.setImageResource(R.drawable.monster10)
                        }
                    }
                }
            }
        }
    }

    private fun fadein(): Int {
        // 透明度を1から0に変化
        val alphaFadeout = AlphaAnimation(0.0f, 1.0f)
        // animation時間 msec
        alphaFadeout.duration = 8000
        // animationが終わったそのまま表示にする
        alphaFadeout.fillAfter = true

        touchTEXT.startAnimation(alphaFadeout)
        return 1
    }

    private fun YARARETA(){
        val rotate = RotateAnimation(
            0f,
            15f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
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

        yararetaText.startAnimation(rotate) // アニメーション適用
        Handler().postDelayed(Runnable {
            yararetaText.startAnimation((rotate2))
            Handler().postDelayed(Runnable{
                yararetaText.startAnimation((rotate3))
            }, 1000)
        }, 550)
    }

    private fun KURUKURU(){
        val set = AnimationSet(true)
        val pic_rotate = RotateAnimation(
            0f,
            180f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        pic_rotate.duration = 4000
        set.addAnimation(pic_rotate)

        val pic_tra = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 2f
        )
        pic_tra.duration = 4000
        set.addAnimation(pic_tra)
        breakimage.startAnimation(set)
        Handler().postDelayed(Runnable {
            breakimage.setImageDrawable(null)
        }, 4000)
    }

    override fun onDestroy() {
        soundPool.release ()
        super.onDestroy()
    }
}
