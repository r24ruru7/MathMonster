package com.example.yamato.mathmonster

import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_level.*
import kotlinx.android.synthetic.main.activity_math.*
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import kotlinx.android.synthetic.main.activity_math.view.*


class MathActivity : AppCompatActivity() {

    var num_a = 0       //上側の問題
    var num_b = 0       //下側の問題
    var total = 0       //問題の答え
    var answer = 0      //解答
    var suc_cnt = 0     //正解数
    var repeat_a = 0    //前の問題の数字(num_a)
    var repeat_b = 0    //前の問題の数字(num_b)
    var on_off_click = 1    //ボタンを押せるタイミング
    var false_count = 0 //間違えた回数
    var now_level = 0   //現在出題しているレベル
    var hint_stage = 0 //ヒントの段階

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math)

        var lev = intent.getIntExtra("SelLev", 0)
        var m_sel = intent.getIntExtra("NUmber", 0)
        var level_cnt = intent.getIntExtra("playlev",1)
        var false_C = intent.getIntExtra("monsterAct_false", 0)
        val intent = Intent(this@MathActivity, BreakActivity::class.java)
        val back = Intent(this@MathActivity, LevelActivity::class.java)
        val mix = Intent(this@MathActivity, BreakActivity::class.java)
        back.putExtra("backMath", m_sel)
        back.putExtra("backLev", lev)
        intent.putExtra("NUMber", m_sel)
        intent.putExtra("SELLEv", lev)
        intent.putExtra("PLAYlev", level_cnt)


        false_count = false_C
        if(m_sel == 2) Number_plus.text = "－"

        Levels(lev, level_cnt)
        JudgmentQ(lev, m_sel, level_cnt)

        backBTN.setOnClickListener {
            startActivity(back)
        }
        lev_Btn.setOnClickListener {
            startActivity(intent)
        }

        button1.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 1
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button2.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 2
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button3.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 3
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button4.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 4
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button5.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 5
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button6.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 6
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button7.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 7
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button8.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 8
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button9.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 9
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }
        button0.setOnClickListener {
            if(on_off_click == 1) {
                on_off_click = 0
                answer = 0
                Number_total.text = answer.toString()
                ClickBtn(lev, m_sel, intent, level_cnt, mix)
            }
        }

        button11.setOnClickListener {
            DynamicHint(1)
            if(hint_stage == 1) button12.setEnabled(true)
        }


        button12.setOnClickListener {
            DynamicHint(2)
        }
    }

    private fun incdecStage(receiveBtn:Int){
        when(receiveBtn){
            1 -> hint_stage++
            2 -> hint_stage--
        }
    }

    private fun DynamicHint(userClickBtn: Int){
        when(now_level){
            1 -> HintPlus1(userClickBtn)
            2 -> HintPlus2(userClickBtn)
            3 -> HintPlus3(userClickBtn)
            4 -> HintPlus4(userClickBtn)
            5 -> HintPlus5(userClickBtn)
            6 -> HintMinus1(userClickBtn)
            7 -> HintMinus2(userClickBtn)
        }
    }

    private fun HintMinus2(userClickBtn: Int){
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        incdecStage(userClickBtn)   //押したボタンによってhint_stageを増減

        val alphaFadeout = AlphaAnimation(1.0f, 0.0f)   //フェードアウトの設定
        alphaFadeout.duration = 500     //0.5秒後
        val alphaFadein = AlphaAnimation(0.0f, 1.0f)    //フェードイン処理
        alphaFadein.duration = 500     //0.5秒後

        for(i in 1..num_a){
            when (decideColor(2, i)) {
                1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)
                2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)
            }
        }
        for(i in 1..num_b){
            undertile[i-1].setBackgroundResource(R.drawable.wakusen3)
        }


        when(hint_stage) {
            0 -> {
                deleteTile()
            }
        }
    }

    private fun HintMinus1(userClickBtn: Int){
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        incdecStage(userClickBtn)   //押したボタンによってhint_stageを増減

        val alphaFadeout = AlphaAnimation(1.0f, 0.0f)   //フェードアウトの設定
        alphaFadeout.duration = 500     //0.5秒後
        val alphaFadein = AlphaAnimation(0.0f, 1.0f)    //フェードイン処理
        alphaFadein.duration = 500     //0.5秒後

        for(i in 1..num_a){
            when (decideColor(2, i)) {
                1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)
                2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)
            }
        }
        for(i in 1..num_b){
            undertile[i-1].setBackgroundResource(R.drawable.wakusen3)
        }


        when(hint_stage) {
            0 -> {
                deleteTile()
            }
            1 -> {
                if(userClickBtn == 1 || userClickBtn == 2 && num_a ==5){
                    button12.setEnabled(true)
                    if(num_a == 5){
                        uptile[9].setVisibility(View.VISIBLE)
                        for(i in 1..num_b){
                            undertile[i-1].setVisibility(View.VISIBLE)
                        }
                    }else{
                        HintMinus1(1)
                    }
                }else{
                    HintMinus1(2)
                }
            }
            2 -> {
                if(userClickBtn == 1){
                    if(num_a == 5){
                        uptile[9].startAnimation(alphaFadeout)
                        uptile[9].setVisibility(View.INVISIBLE)
                        for(i in 1..num_a){
                            uptile[i-1].startAnimation(alphaFadein)
                        }
                        HintMinus1(1)
                    }else{
                        HintMinus1(1)
                    }
                }else{
                    HintMinus1(2)
                }
            }
            3 -> {
                for(i in 1..num_a){
                    if(num_a == 5) uptile[i - 1].setVisibility(View.VISIBLE)
                    else uptile[i - 1].setVisibility(View.VISIBLE)
                }
                for(i in 1..num_b){
                    undertile[i-1].setVisibility(View.VISIBLE)
                }
            }
            4 -> {
                if(userClickBtn == 1){
                    for(i in 1..num_b){
                        var objectAnimator = ObjectAnimator.ofFloat(uptile[i-1], "translationY", 30.0f)
                        objectAnimator.duration = 500
                        objectAnimator.repeatCount = 0
                        objectAnimator.start()
                    }
                    HintMinus1(1)
                }else{
                    settingTile()
                    HintMinus1(2)
                }
            }
            5 -> {
                for(i in 1..num_b){
                    uptile[i-1].setTranslationY(30.0f)
                    uptile[i-1].setVisibility(View.VISIBLE)
                    undertile[i-1].setVisibility(View.VISIBLE)
                }
            }
            6 -> {
                if(userClickBtn == 1){
                    for(i in 1..num_b){
                        undertile[i-1].startAnimation(alphaFadeout)
                        undertile[i-1].setVisibility(View.INVISIBLE)
                    }
                    for(i in 1..num_b){
                        var objectAnimator = ObjectAnimator.ofFloat(uptile[i-1], "translationY", 302.5f)
                        objectAnimator.duration = 500
                        objectAnimator.repeatCount = 0
                        objectAnimator.start()
                        Handler().postDelayed(Runnable {
                            uptile[i-1].startAnimation(alphaFadeout)
                            uptile[i-1].setVisibility(View.INVISIBLE)
                        },1500)
                    }
                    HintMinus1(1)
                }else{
                    settingTile()
                    button11.setEnabled(true)
                    HintMinus1(2)
                }
            }
            7 -> {
                button11.setEnabled(false)
            }
        }
    }

    private fun resetHint(){    //ヒント初期化処理
        hint_stage = 0
        button11.setEnabled(true)
        button12.setEnabled(false)
    }

    private fun settingTile(){  //タイルをもとの位置に戻す
        val Tilearray = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2, under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2, up_zero, under_zero)
        for(i in 1..22){
            Tilearray[i-1].setTranslationY(0.0f)
        }
    }

    private fun resetTile(){       //タイルの配置を戻して非表示にする
        val Tilearray = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2, under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2, up_zero, under_zero)
        for(i in 1..22){
            Tilearray[i-1].setVisibility(View.GONE)
            Tilearray[i-1].setTranslationY(0.0f)
        }
    }

    private fun deleteTile(){       //タイルの配置を戻して非表示にしたうえで戻るボタンを押せなくする
        val Tilearray = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2, under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2, up_zero, under_zero)
        for(i in 1..22){
            Tilearray[i-1].setVisibility(View.GONE)
            Tilearray[i-1].setTranslationY(0.0f)
        }
        button12.setEnabled(false)
    }

    private fun hideTile(hideORappear:Int){     //移動後のタイルを表示/非表示にする、非表示にするときにフェードアウトさせる
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        val alphaFadeout = AlphaAnimation(1.0f, 0.0f)   //フェードアウトの設定
        alphaFadeout.duration = 500     //0.5秒後

        when(hideORappear){
            1 -> {
                for(i in 1..num_a){
                    uptile[i-1].startAnimation(alphaFadeout)    //フェードアウト実行
                    Handler().postDelayed(Runnable {
                        uptile[i - 1].setVisibility(View.INVISIBLE)
                    },500)
                }
                for(i in 1..num_b){
                    undertile[i-1].startAnimation(alphaFadeout)     //フェードアウト実行
                    Handler().postDelayed(Runnable {
                        undertile[i - 1].setVisibility(View.INVISIBLE)
                    },500)
                }
            }
            2 -> {
                for(i in 1..num_a){
                    uptile[i-1].setVisibility(View.VISIBLE)
                }
                for(i in 1..num_b){
                    undertile[i-1].setVisibility(View.VISIBLE)
                }
            }
            3 -> {  //上側だけ
                for(i in 1..num_a){
                    uptile[i-1].startAnimation(alphaFadeout)    //フェードアウト実行
                    Handler().postDelayed(Runnable {
                        uptile[i - 1].setVisibility(View.INVISIBLE)
                    },500)
                }
            }
            4 -> {  //下側だけ
                for(i in 1..num_b){
                    undertile[i-1].startAnimation(alphaFadeout)     //フェードアウト実行
                    Handler().postDelayed(Runnable {
                        undertile[i - 1].setVisibility(View.INVISIBLE)
                    },500)
                }
            }
        }
    }

    private fun decideColor(tilecolor:Int, i:Int):Int{
        var colored = 0
        when(tilecolor){
            1 -> {  //上側のタイル(レベル1用)
                if(num_b%2 == 1){   //下側が奇数
                    if(i%2 == 1) colored = 2    //奇数番の時は黄色
                    else colored = 1            //偶数番の時はオレンジ色
                }else{  //下側が偶数
                    if(i%2 == 1) colored = 1    //奇数番の時はオレンジ色
                    else colored = 2            //偶数番の時は黄色
                }
            }
            2 -> {  //下側のタイル(レベル1用)
                if(i%2 == 1)colored = 1     //奇数番の時はオレンジ色
                else colored = 2            //偶数番の時は黄色
            }
            3 -> {  //下が5のとき(レベル2用)
                if(i%2 == 1)colored = 2     //奇数番の時は黄色
                else colored = 1            //偶数番の時はオレンジ色
            }
            4 -> {  //上が5の時(レベル2用)
                if(num_b % 2 == 0){ //下の数字が偶数
                    if(i % 2 == 1)colored = 1   //奇数番はオレンジ色
                    else colored = 2            //偶数番は黄色
                } else{ //下の数字が奇数
                    if(i % 2 == 1)colored = 2    //奇数番は黄色
                    else colored = 1            //偶数番はオレンジ色
                }
            }
            5 -> {  //上側が5以上(レベル3用、上側)
                if(num_a % 2 == 0) {  //上の数字が偶数
                    if (i % 2 == 1) colored = 2 //奇数番は黄色(iの値がnum_a-5になっていることに注意)
                    else colored = 1            //偶数番はオレンジ色(6の場合はiは1,7の場合はiが1と2が飛んでくる)
                }else{  //上の数字が奇数
                    if (i % 2 == 1) colored = 1
                    else colored = 2
                }
            }
            6 -> {  //上側が5以上(レベル3用、下側)
                if(num_a % 2 == 0 && num_b % 2 == 0){
                    if(i % 2 == 1) colored = 2
                    else colored = 1
                }else if(num_a % 2 == 0 && num_b % 2 == 1){
                    if(i % 2 == 1) colored = 1
                    else colored = 2
                }else if(num_a % 2 == 1 && num_b % 2 == 0){
                    if(i % 2 == 1) colored = 1
                    else colored = 2
                }else{
                    if(i % 2 == 1) colored = 2
                    else colored = 1
                }
            }
        }
        return colored
    }


    /*****     レベル判別とそれに応じたテキストの処理     *****/

    private fun Levels(select:Int, level_cnt: Int){
        when (select) {
            1 -> lev_Btn.text = "レベル1"
            2 -> lev_Btn.text = "レベル2"
            3 -> lev_Btn.text = "レベル3"
            4 -> lev_Btn.text = "レベル4"
            5 -> lev_Btn.text = "レベル5"
            6 -> {
                when(level_cnt){
                    1 -> lev_Btn.text = "レベル1"
                    2 -> lev_Btn.text = "レベル2"
                    3 -> lev_Btn.text = "レベル3"
                    4 -> lev_Btn.text = "レベル4"
                    5 -> lev_Btn.text = "レベル5"
                }
            }
        }
    }

    private fun JudgmentQ(Level:Int, Math:Int, level_cnt:Int){
        when(Math){
            1 -> PlusQ(Level, level_cnt)
            2 -> MinusQ(Level, level_cnt)
        }
    }

    private fun PlusQ(Level: Int, level_cnt: Int){
        deleteTile()
        when(Level){
            1 -> CreatePlus1()
            2 -> CreatePlus2()
            3 -> CreatePlus3()
            4 -> CreatePlus4()
            5 -> CreatePlus5()
            6 -> CreatePlusMix(level_cnt)
        }
    }

    private fun MinusQ(Level: Int, level_cnt: Int){
        deleteTile()
        when(Level){
            1 -> CreateMinus1()
            2 -> CreateMinus2()
            3 -> CreateMinus3()
            4 -> CreateMinus4()
            5 -> CreateMinus5()
            6 -> CreateMinusMix(level_cnt)
        }
    }


    /*****     テキスト表示処理     *****/

    private fun SendNumPlus(){
        total = num_a + num_b
        repeat_a = num_a
        repeat_b = num_b
        Edit_text()
    }

    private fun SendNumMinus(){
        total = num_a - num_b
        repeat_a = num_a
        repeat_b = num_b
        Edit_text()
    }

    private fun Edit_text(){
        Number_a.text = num_a.toString()
        Number_b.text = num_b.toString()
    }


    /*****     足し算の問題表示     *****/

    private fun CreatePlus1(){
        resetHint()
        now_level = 1
        do {
            num_a = (1..4).random()
            num_b = (1..4).random()
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = 100
            }
        }
        while((num_a + num_b) > 5)

        SendNumPlus()
    }

    private fun CreatePlus2(){
        resetHint()
        now_level = 2
        var sel_five = (0..1).random()  //上と下どっちを5にするか
        do{
            when(sel_five){
                0 -> {
                    num_a = 5
                    num_b = (1..4).random()
                }
                1 -> {
                    num_a = (1..4).random()
                    num_b = 5
                }
            }
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = 100
            }
        }while((num_a + num_b) > 9)

        SendNumPlus()
    }

    private fun CreatePlus3(){
        resetHint()
        now_level = 3
        var sel_five = (0..1).random()  //上と下どっちを5以上にするか
        do{
            when(sel_five){
                0 -> {
                    num_a = (6..8).random()
                    num_b = (1..3).random()
                }
                1 -> {
                    num_a = (1..3).random()
                    num_b = (6..8).random()
                }
            }
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = 100
            }
        }while((num_a + num_b) > 9)

        SendNumPlus()
    }

    private fun CreatePlus4(){
        resetHint()
        now_level = 4
        do {
            num_a = (2..4).random()
            num_b = (2..4).random()
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = -100
            }
        }
        while((num_a + num_b) < 6)

        SendNumPlus()
    }

    private fun CreatePlus5(){
        resetHint()
        now_level = 5
        var sel_five = (0..1).random()  //上と下どっちを0にするか
        do{
            when(sel_five){
                0 -> {
                    num_a = 0
                    num_b = (1..9).random()
                }
                1 -> {
                    num_a = (1..9).random()
                    num_b = 0
                }
            }
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = 100
            }
        }while((num_a + num_b) > 9)

        SendNumPlus()
    }

    private fun CreatePlusMix(level_cnt: Int){
        var offer_que = (1..5).random()
        when(offer_que){
            1 -> CreatePlus1()
            2 -> CreatePlus2()
            3 -> CreatePlus3()
            4 -> CreatePlus4()
            5 -> CreatePlus5()
        }
    }


    /*****     引き算の問題表示     *****/

    private fun CreateMinus1(){
        resetHint()
        now_level = 6
        do {
            num_a = (1..5).random()
            num_b = (1..5).random()
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = -100
            }
        }
        while((num_a - num_b) < 1)

        SendNumMinus()
    }

    private fun CreateMinus2(){
        resetHint()
        now_level = 7
        var sel_five = (0..1).random()  //下に５を入れるか
        do{
            when(sel_five){
                0 -> {
                    num_a = (6..9).random()
                    num_b = (1..4).random()
                }
                1 -> {
                    num_a = (6..9).random()
                    num_b = 5
                }
            }
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = -100
            }
        }while((num_a - num_b) != 5 && sel_five == 0 || (num_a - num_b) == 5 && sel_five == 1)

        SendNumMinus()
    }

    private fun CreateMinus3(){
        resetHint()
        now_level = 8
        var sel_five = (0..1).random()  //下の数字の大きさ
        do{
            when(sel_five){
                0 -> {
                    num_a = (6..9).random()
                    num_b = (1..3).random()
                }
                1 -> {
                    num_a = (6..9).random()
                    num_b = (6..8).random()
                }
            }
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = -100
            }
        }while((num_a - num_b) < 6 && sel_five == 0 || (num_a - num_b) <= 0 && sel_five == 1)

        SendNumMinus()
    }

    private fun CreateMinus4(){
        resetHint()
        now_level = 9
        do {
            num_a = (6..8).random()
            num_b = (2..4).random()
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = 100
            }
        }
        while((num_a - num_b) > 4)

        SendNumMinus()
    }

    private fun CreateMinus5(){
        resetHint()
        now_level = 10
        var sel_five = (0..1).random()  //0を入れるか答えになるか
        do{
            when(sel_five){
                0 -> {
                    num_a = (1..9).random()
                    num_b = (1..9).random()
                }
                1 -> {
                    num_a = (1..9).random()
                    num_b = 0
                }
            }
            if(num_a == repeat_a && num_b == repeat_b){
                num_a = -100
            }
        }while((num_a - num_b) != 0 && sel_five == 0 || (num_a - num_b) < 0 && sel_five == 1)

        SendNumMinus()
    }

    private fun CreateMinusMix(level_cnt: Int){
        var offer_que = (1..5).random()
        when(offer_que){
            1 -> CreateMinus1()
            2 -> CreateMinus2()
            3 -> CreateMinus3()
            4 -> CreateMinus4()
            5 -> CreateMinus5()
        }
    }


    /*****     数字ボタンを押したときの処理     *****/

    private fun ClickBtn(lev:Int, m_sel:Int, intent: Intent, level_cnt: Int, mix:Intent){
        if(answer == total){
            suc_cnt++
            when(suc_cnt){
                1 -> suc_count1.setImageResource(R.drawable.circle2)
                2 -> suc_count2.setImageResource(R.drawable.circle2)
                3 -> suc_count3.setImageResource(R.drawable.circle2)
                4 -> suc_count4.setImageResource(R.drawable.circle2)
                5 -> suc_count5.setImageResource(R.drawable.circle2)
                6 -> {
                    suc_count6.setImageResource(R.drawable.circle2)
                    Handler().postDelayed(Runnable {
                        intent.putExtra("mathAct_false", false_count)
                        startActivity(intent)
                    },800)
                }
            }
            if(suc_cnt < 6) {
                Handler().postDelayed(Runnable {
                    Number_total.text = null
                    JudgmentQ(lev, m_sel, level_cnt)
                    on_off_click = 1
                }, 800)
            }
        }else if(answer != total){
            on_off_click = 1
            Handler().postDelayed({
                false_count += 1
            },50)
            Handler().postDelayed(Runnable {
                Number_total.text = null
            },3000)
        }
    }


    /*****     ヒントボタンと戻るボタンの処理     *****/
    //レベル1(足し算)のヒント
    private fun HintPlus1(userClickBtn:Int){
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        incdecStage(userClickBtn)   //押したボタンによってhint_stageを増減

        when(hint_stage){
            0 -> {      //ヒント表示なし
                deleteTile()
            }
            1 -> {      //タイル表示
                button11.setEnabled(true)   //hintstage=3から帰ってきたとき用
                resetTile()
                for(i in 1..num_a) {    //上の数字のタイル表示と色変更
                    uptile[i - 1].setVisibility(View.VISIBLE)   //上側のタイルを表示する
                    when (decideColor(1, i)) {
                        1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)    //タイルの色を設定(オレンジ色)
                        2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)   //タイルの色を設定(黄色)
                    }
                }
                for(i in 1..num_b){    //下の数字のタイル表示と色変更
                    undertile[i-1].setVisibility(View.VISIBLE)
                    when(decideColor(2, i)) {
                        1 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen)
                        2 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                    }
                }
            }
            2 -> {      //タイルが動くアニメーション
                if(userClickBtn == 1){
                    for(i in 1..num_a){
                        var objectAnimator = ObjectAnimator.ofFloat(uptile[i-1], "translationY", (320 - 29 * num_b).toFloat())  //uptileのY軸を指定された分だけ下げる
                        objectAnimator.duration = 500   //0.5秒後
                        objectAnimator.repeatCount = 0  //リピートしない
                        objectAnimator.start()  //実行
                    }
                    HintPlus1(1)    //ヒント3を実行(ヒントボタンを押したときの処理)
                }else if(userClickBtn == 2){
                    HintPlus1(2)    //ヒント1を実行(戻るボタンを押したときの処理)
                }
            }
            3 -> {      //タイル移動後の配置処理
                hideTile(2)
                undertile[9].setVisibility(View.INVISIBLE)     //ヒント4から戻ってきたときに5の塊のタイルを隠す
                var movetile = (320 - 29 * num_b).toFloat()     //タイルの配置を変えてる
                for (i in 1..num_a) {
                    uptile[i - 1].setTranslationY(movetile)
                }
                button11.setEnabled(true)   //hintstage=4から帰ってきたとき用
                if(num_a + num_b !=5) button11.setEnabled(false)
            }
            4 -> {      //タイルの合計が5だった時の処理
                hideTile(1)
                val alphaFadein = AlphaAnimation(0.0f, 1.0f)    //フェードイン処理
                alphaFadein.duration = 500     //0.5秒後
                undertile[9].startAnimation(alphaFadein)   //フェードイン実行
                Handler().postDelayed(Runnable {
                    undertile[9].setVisibility(View.VISIBLE)
                },500)
                button11.setEnabled(false)
            }
        }
    }

    //レベル2(足し算)のヒント
    private fun HintPlus2(userClickBtn: Int){
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        incdecStage(userClickBtn)   //押したボタンによってhint_stageを増減

        when(hint_stage){
            0 -> {  //ヒント表示なし
                deleteTile()
            }
            1 -> {  //タイル表示
                button11.setEnabled(true)   //hintstage=3から帰ってきたとき用
                resetTile()
                if(num_a == 5) {    //上側が5の時
                    uptile[9].setVisibility(View.VISIBLE)   //5の塊タイルを表示
                    for(i in 1..num_b){     //下側表示と色変更
                        undertile[i-1].setVisibility(View.VISIBLE)
                        when(decideColor(4, i)) {
                            1 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                }
                else if(num_b == 5) {   //下側が5の時
                    undertile[9].setVisibility(View.VISIBLE)
                    for(i in 1..num_a) {    //上の数字のタイル表示と色変更
                        uptile[i - 1].setVisibility(View.VISIBLE)
                        when (decideColor(3, i)) {
                            1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                }
            }
            2 -> {  //タイルが動くアニメーション
                if(userClickBtn == 1){
                    button11.setEnabled(true)   //ヒントボタン押せるようになる
                    if(num_b == 5){
                        for(i in 1..num_a){     //移動アニメーション(詳細はHintPlus1へ)
                            var objectAnimator = ObjectAnimator.ofFloat(uptile[i-1], "translationY", (322 - 30 * num_b).toFloat())
                            objectAnimator.duration = 500
                            objectAnimator.repeatCount = 0
                            objectAnimator.start()
                        }
                    }else if(num_a == 5){
                        var objectAnimator = ObjectAnimator.ofFloat(uptile[9], "translationY", (322 - 30 * num_b).toFloat())
                        objectAnimator.duration = 500
                        objectAnimator.repeatCount = 0
                        objectAnimator.start()
                    }
                    HintPlus2(1)    //ヒント3を実行(ヒントボタンを押したときの処理)
                }else if(userClickBtn == 2){
                    HintPlus2(2)    //ヒント1を実行(戻るボタンを押したときの処理)
                }
            }
            3 -> {
                button11.setEnabled(false)
            }
        }
    }

    //レベル3(足し算)のヒント
    private fun HintPlus3(userClickBtn: Int){
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        incdecStage(userClickBtn)   //押したボタンによってhint_stageを増減

        when(hint_stage){
            0 -> {
                deleteTile()
            }
            1 -> {
                //タイル表示
                button11.setEnabled(true)   //hintstage=3から帰ってきたとき用
                resetTile()
                if(num_a >= 5){ //上側が5以上の時
                    uptile[9].setVisibility(View.VISIBLE)
                    uptile[9].setTranslationY((-28.5*(num_a-5)).toFloat())  //5の塊のタイルを上にずらす、表示微調整のため28.5の倍で計算
                    for(i in (6-5)..(num_a-5)){     //5の塊のタイルの下に配置するように
                        uptile[i - 1].setVisibility(View.VISIBLE)
                        when (decideColor(5, i)) {
                            1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                    for(i in 1..num_b){
                        undertile[i-1].setVisibility(View.VISIBLE)
                        when(decideColor(6, i)){
                            1 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                }else{  //下側が5以上の時
                    undertile[9].setVisibility(View.VISIBLE)
                    for(i in 6..num_b){
                        undertile[i-1].setVisibility(View.VISIBLE)
                        when(decideColor(2, i)){    //タイルカラーはレベル1のやり方を流用
                            1 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                    for(i in 1..num_a){
                        uptile[i-1].setVisibility(View.VISIBLE)
                        when(decideColor(1, i)){    //タイルカラーはレベル1のやり方を流用
                            1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                }
            }
            2 -> {
                if(userClickBtn == 1){
                    button11.setEnabled(true)   //ヒントボタン押せるようになる
                    if(num_a >= 5){
                        for(i in 1..(num_a-5)){     //移動アニメーション(詳細はHintPlus1へ)
                            var objectAnimator = ObjectAnimator.ofFloat(uptile[i-1], "translationY", (322 - 30 * num_b).toFloat())
                            objectAnimator.duration = 500
                            objectAnimator.repeatCount = 0
                            objectAnimator.start()
                        }
                        var objectAnimator2 = ObjectAnimator.ofFloat(uptile[9], "translationY", (322 - 29.0 * (num_b + num_a - 5)).toFloat())
                        objectAnimator2.duration = 500
                        objectAnimator2.repeatCount = 0
                        objectAnimator2.start()
                    }else {
                        for(i in 1..num_a){     //移動アニメーション(詳細はHintPlus1へ)
                            var objectAnimator = ObjectAnimator.ofFloat(uptile[i-1], "translationY", (322 - 29.5 * num_b).toFloat())
                            objectAnimator.duration = 500
                            objectAnimator.repeatCount = 0
                            objectAnimator.start()
                        }
                    }
                    HintPlus3(1)    //ヒント3を実行(ヒントボタンを押したときの処理)
                }else if(userClickBtn == 2){
                    HintPlus3(2)    //ヒント1を実行(戻るボタンを押したときの処理)
                }
            }
            3 -> {
                button11.setEnabled(false)
            }
        }
    }

    //レベル4(足し算)のヒント
    private fun HintPlus4(userClickBtn: Int){
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        incdecStage(userClickBtn)   //押したボタンによってhint_stageを増減

        val alphaFadeout = AlphaAnimation(1.0f, 0.0f)   //フェードアウトの設定
        alphaFadeout.duration = 500     //0.5秒後
        val alphaFadein = AlphaAnimation(0.0f, 1.0f)    //フェードイン処理
        alphaFadein.duration = 500     //0.5秒後

        when(hint_stage) {
            0 -> {
                deleteTile()
            }
            1 -> {  //タイル表示のやり方はレベル1と一緒
                button11.setEnabled(true)   //hintstage=3から帰ってきたとき用
                resetTile()
                for(i in 1..num_a) {    //上の数字のタイル表示と色変更
                    uptile[i - 1].setVisibility(View.VISIBLE)   //上側のタイルを表示する
                    when (decideColor(1, i)) {
                        1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)    //タイルの色を設定(オレンジ色)
                        2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)   //タイルの色を設定(黄色)
                    }
                }
                for(i in 1..num_b){    //下の数字のタイル表示と色変更
                    undertile[i-1].setVisibility(View.VISIBLE)
                    when(decideColor(2, i)) {
                        1 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen)
                        2 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                    }
                }
            }
            2 -> {
                settingTile()
                if(num_a > num_b){
                    var blank = 5 - num_a
                    for(i in (num_a + 1)..(num_a + blank)){
                        uptile[i-1].setVisibility(View.VISIBLE)
                        uptile[i-1].setBackgroundResource(R.drawable.wakusen3)
                    }
                }else{
                    var blank = 5 - num_b
                    for(i in (num_b + 1)..(num_b + blank)){
                        undertile[i-1].setVisibility(View.VISIBLE)
                        undertile[i-1].setBackgroundResource(R.drawable.wakusen3)
                    }
                }
            }
            3 -> {
                if(userClickBtn == 1){
                    if(num_a > num_b){
                        var objectAnimator = ObjectAnimator.ofFloat(undertile[num_b-1], "translationY", -30.0f)
                        objectAnimator.duration = 500
                        objectAnimator.repeatCount = 0
                        objectAnimator.start()
                    }else{
                        var blank = 5 - num_b
                        for(i in 1..blank){
                            var objectAnimator2 = ObjectAnimator.ofFloat(uptile[i-1], "translationY", 30.0f)
                            objectAnimator2.duration = 500
                            objectAnimator2.repeatCount = 0
                            objectAnimator2.start()
                        }
                    }
                    HintPlus4(1)
                }else{
                    HintPlus4(2)
                }
            }
            4 -> {
                if(num_a > num_b){
                    undertile[num_b-1].setTranslationY(-30.0f)
                    when(num_b){
                        2 -> {
                            undertile[num_b-1].setBackgroundResource(R.drawable.wakusen2)
                            undertile[num_b-2].setBackgroundResource(R.drawable.wakusen)
                        }
                        3 -> {
                            undertile[num_b-1].setBackgroundResource(R.drawable.wakusen)
                        }
                    }
                    uptile[4].setVisibility(View.VISIBLE)
                }else{
                    var blank = 5 - num_b
                    for(i in 1..blank) {
                        uptile[i-1].setTranslationY(30.0f)
                        undertile[i+num_b-1].setVisibility(View.VISIBLE)
                    }
                }
            }
            5 -> {
                if(userClickBtn == 1){
                    if(num_a > num_b){
                        var objectAnimator = ObjectAnimator.ofFloat(undertile[num_b-1], "translationY", (-318 - (5 - num_b) * 28.5).toFloat())
                        objectAnimator.duration = 500
                        objectAnimator.repeatCount = 0
                        objectAnimator.start()
                        uptile[4].startAnimation(alphaFadeout)
                    }else{
                        var blank = 5 - num_b
                        for(i in 1..blank){
                            var objectAnimator2 = ObjectAnimator.ofFloat(uptile[i-1], "translationY", (322 - 30 * num_b).toFloat())
                            objectAnimator2.duration = 500
                            objectAnimator2.repeatCount = 0
                            objectAnimator2.start()
                            undertile[i+num_b-1].startAnimation(alphaFadeout)
                        }
                    }
                    HintPlus4(1)
                }else{
                    HintPlus4(2)
                }
            }
            6 -> {
                hideTile(2)
                uptile[9].setVisibility(View.INVISIBLE)
                undertile[9].setVisibility(View.INVISIBLE)
                if(num_a > num_b){
                    undertile[num_b-1].setTranslationY((-318 - (5 - num_b) * 28.5).toFloat())     //上の対応するタイルまで移動後残りのタイル分移動する
                    when(num_b){
                        2 -> {
                            undertile[num_b-1].setBackgroundResource(R.drawable.wakusen)
                            undertile[num_b-2].setBackgroundResource(R.drawable.wakusen2)
                        }
                        3 -> {
                            undertile[num_b-1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                    uptile[4].setVisibility(View.INVISIBLE)
                }else{
                    var blank = 5 - num_b
                    for(i in 1..blank) {
                        uptile[i-1].setTranslationY((322 - 30 * num_b).toFloat())
                        undertile[i+num_b-1].setVisibility(View.INVISIBLE)
                    }
                }
            }
            7 -> {
                if(userClickBtn == 1){
                    if(num_a > num_b){
                        hideTile(3)
                        undertile[num_b-1].startAnimation(alphaFadeout)
                        Handler().postDelayed(Runnable {
                            undertile[num_b-1].setVisibility(View.INVISIBLE)
                        },500)
                        uptile[9].startAnimation(alphaFadein)   //フェードイン実行
                        Handler().postDelayed(Runnable {
                            uptile[9].setVisibility(View.VISIBLE)
                        },500)
                    }else{
                        hideTile(4)
                        var blank = 5 - num_b
                        for(i in 1..blank) {
                            uptile[i-1].startAnimation(alphaFadeout)
                            Handler().postDelayed(Runnable {
                                uptile[i-1].setVisibility(View.INVISIBLE)
                            },500)
                        }
                        undertile[9].startAnimation(alphaFadein)   //フェードイン実行
                        Handler().postDelayed(Runnable {
                            undertile[9].setVisibility(View.VISIBLE)
                        },500)
                    }
                }else{
                    if(num_a > num_b){
                        uptile[9].setVisibility(View.VISIBLE)
                    }else{
                        undertile[9].setVisibility(View.VISIBLE)
                    }
                }
            }
            8 -> {
                if(userClickBtn == 1){
                    if(num_a > num_b){
                        var objectAnimator3 = ObjectAnimator.ofFloat(uptile[9], "translationY", (322 - 30.0 * (num_b - 1)).toFloat())
                        objectAnimator3.duration = 500
                        objectAnimator3.repeatCount = 0
                        objectAnimator3.start()
                    }else{
                        var nextmovetile = 5 - num_b    //上側が失ったタイルの数
                        for(i in (1 + nextmovetile)..num_a){    //上側が動かすタイル
                            var objectAnimator4 = ObjectAnimator.ofFloat(uptile[i-1], "translationY", (322 - 30.0 * (num_b)).toFloat())
                            objectAnimator4.duration = 500
                            objectAnimator4.repeatCount = 0
                            objectAnimator4.start()
                        }
                    }
                    HintPlus4(1)
                }else{
                    button11.setEnabled(true)
                    if(num_a > num_b){
                        uptile[9].setTranslationY(0.0f)
                    }else{
                        var nextmovetile = 5 - num_b    //上側が失ったタイルの数
                        for(i in (1 + nextmovetile)..num_a) {    //上側が動かすタイル
                            uptile[i-1].setTranslationY(0.0f)
                        }
                    }
                    HintPlus4(2)
                }
            }
            9 -> {
                button11.setEnabled(false)
            }
        }
    }

    //レベル5(足し算)のヒント
    private fun HintPlus5(userClickBtn: Int){
        val uptile = listOf(up_tile1, up_tile2, up_tile3, up_tile4, up_tile5, up_tile6, up_tile7, up_tile8, up_tile9, up_tile5_2)   //ヒントタイルの配列(上側)
        val undertile = listOf(under_tile1, under_tile2, under_tile3, under_tile4, under_tile5, under_tile6, under_tile7, under_tile8, under_tile9, under_tile5_2) //ヒントタイルの配列(下側)

        incdecStage(userClickBtn)   //押したボタンによってhint_stageを増減

        when(hint_stage){
            0 -> {
                deleteTile()
            }
            1 -> {
                resetTile()
                if(num_a == 0) {
                    up_zero.setVisibility(View.VISIBLE)
                    for(i in 1..num_b){
                        undertile[i-1].setVisibility(View.VISIBLE)
                        when(decideColor(2, i)) {
                            1 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> undertile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                    if(num_b >= 5){
                        for(i in 1..5){
                            undertile[i-1].setVisibility(View.GONE)
                        }
                        undertile[9].setVisibility(View.VISIBLE)
                    }
                } else {
                    under_zero.setVisibility(View.VISIBLE)
                    for(i in 1..num_a){
                        uptile[i-1].setVisibility(View.VISIBLE)
                        when(decideColor(2, i)) {
                            1 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen)
                            2 -> uptile[i - 1].setBackgroundResource(R.drawable.wakusen2)
                        }
                    }
                    if(num_a >= 5){
                        for(i in 1..5){
                            uptile[i-1].setVisibility(View.GONE)
                        }
                        uptile[9].setVisibility(View.VISIBLE)
                    }
                }
            }
            2 -> {
                if(userClickBtn == 1){
                    if(num_a == 0){
                        var objectAnimator = ObjectAnimator.ofFloat(up_zero, "translationY", 302.5f)
                        objectAnimator.duration = 500
                        objectAnimator.repeatCount = 0
                        objectAnimator.start()
                    }else{
                        if(num_a < 5){
                            for(i in 1..num_a){
                                var objectAnimator2 = ObjectAnimator.ofFloat(uptile[i-1], "translationY", 302.5f)
                                objectAnimator2.duration = 500
                                objectAnimator2.repeatCount = 0
                                objectAnimator2.start()
                            }
                        }else{
                            var objectAnimator3 = ObjectAnimator.ofFloat(uptile[9], "translationY", 302.5f)
                            objectAnimator3.duration = 500
                            objectAnimator3.repeatCount = 0
                            objectAnimator3.start()
                            if(num_a > 5){
                                for(i in 6..num_a){
                                    var objectAnimator4 = ObjectAnimator.ofFloat(uptile[i-1], "translationY", 302.5f)
                                    objectAnimator4.duration = 500
                                    objectAnimator4.repeatCount = 0
                                    objectAnimator4.start()
                                }
                            }
                        }
                    }
                    HintPlus5(1)
                }else{
                    button11.setEnabled(true)
                    HintPlus5(2)
                }
            }
            3 -> {
                button11.setEnabled(false)
            }
        }
    }
}


