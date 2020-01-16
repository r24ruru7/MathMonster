package com.example.yamato.mathmonster

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_break)

        var lev = intent.getIntExtra("SELLEv", 1)
        var m_sel = intent.getIntExtra("NUMber", 1)
        var level_cnt = intent.getIntExtra("PLAYlev", 1)
        var false_count = intent.getIntExtra("mathAct_false", 0)

        val intent = Intent(this@BreakActivity, LevelActivity::class.java)
        val mix = Intent(this@BreakActivity, MonsterActivity::class.java)
        val result = Intent(this@BreakActivity, ResultActivity::class.java)
        intent.putExtra("backMath",m_sel)
        mix.putExtra("Number", m_sel)
        mix.putExtra("selLev", lev)
        mix.putExtra("BREAKACT_FALSE", false_count)
        result.putExtra("clear_math", m_sel)
        result.putExtra("breakAct_false", false_count)

        Levels(lev, m_sel, level_cnt)

        YARARETA()
        KURUKURU()

        level_cnt +=1
        if(level_cnt ==6)lev = 10
        mix.putExtra("PLAYLEV", level_cnt)

        var whichs = 0
        whichs = fadein()
        Handler().postDelayed(Runnable {
            if(whichs == 1) {
                lastground.setOnClickListener {
                    when(lev){
                        1,2,3,4,5 -> startActivity(intent)
                        6 -> startActivity(mix)
                        10 -> startActivity(result)
                    }
                }
            }
        },4000)



    }

    private  fun Levels(select:Int, m_sel:Int, level_cnt:Int){
        if(m_sel == 1) {
            when (select) {
                1 -> {
                    breakimage.setImageResource(R.drawable.c01)
                }
                2 -> {
                    breakimage.setImageResource(R.drawable.c02)
                }
                3 -> {
                    breakimage.setImageResource(R.drawable.c03)
                }
                4 -> {
                    breakimage.setImageResource(R.drawable.c04)
                }
                5 -> {
                    breakimage.setImageResource(R.drawable.c05)
                }
                6 -> {
                    when(level_cnt){
                        1 -> {
                            breakimage.setImageResource(R.drawable.c01)
                        }
                        2 -> {
                            breakimage.setImageResource(R.drawable.c02)
                        }
                        3 -> {
                            breakimage.setImageResource(R.drawable.c03)
                        }
                        4 -> {
                            breakimage.setImageResource(R.drawable.c04)
                        }
                        5 -> {
                            breakimage.setImageResource(R.drawable.c05)
                        }
                    }
                }
            }
        }else if(m_sel == 2){
            when (select) {
                1 -> {
                    breakimage.setImageResource(R.drawable.c06)
                }
                2 -> {
                    breakimage.setImageResource(R.drawable.c05)
                }
                3 -> {
                    breakimage.setImageResource(R.drawable.c04)
                }
                4 -> {
                    breakimage.setImageResource(R.drawable.c03)
                }
                5 -> {
                    breakimage.setImageResource(R.drawable.c02)
                }
                6 -> {
                    when(level_cnt){
                        1 -> {
                            breakimage.setImageResource(R.drawable.c05)
                        }
                        2 -> {
                            breakimage.setImageResource(R.drawable.c04)
                        }
                        3 -> {
                            breakimage.setImageResource(R.drawable.c03)
                        }
                        4 -> {
                            breakimage.setImageResource(R.drawable.c02)
                        }
                        5 -> {
                            breakimage.setImageResource(R.drawable.c01)
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
}
