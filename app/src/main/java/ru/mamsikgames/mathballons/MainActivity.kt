package ru.mamsikgames.mathballons

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import ru.mamsikgames.mathballoons.GameActivity
import ru.mamsikgames.mathballoons.GameSounds


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() ///


        //musicIntent = Intent(this, BackgroundMusic::class.java)
        //startService(musicIntent)

        val gameSounds = GameSounds(this)


        val startBalloon = BalloonAnimated( this,5)

        val ll_main = findViewById<ConstraintLayout>(R.id.ll_main)
        ll_main.addView(startBalloon)

        startBalloon.x = 160F
        startBalloon.y = 1200F

        startBalloon.moveBalloon()
//        val balloonLayoutParams by lazy {
//            LayoutParams(600, 600).apply {
//                bottomToBottom = LayoutParams.PARENT_ID
//                startToStart = LayoutParams.PARENT_ID
//                topToTop = LayoutParams.PARENT_ID
//                endToEnd = LayoutParams.PARENT_ID
//            }
//        }
//        startBalloon.layoutParams = balloonLayoutParams

        val gameIntent = Intent(this, GameActivity::class.java)

        startBalloon.setOnClickListener {
            gameSounds.playSoundBurst()
            startBalloon.burst()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                startActivity(gameIntent)
            }, 500)
        }



        //val startBalloon = findViewById<ImageView>(R.id.yellow_launcher)


//        startBalloon.setOnClickListener {
//
//            gameSounds.playSoundBurst()
//            //lide.with(this).load(R.drawable.animation_burst_blue).into(startBalloon)
//
//            val handler = Handler(Looper.getMainLooper())
//            handler.postDelayed({
//                startActivity(playIntent)
//            }, 100)
//        }
    }


    fun pressPlay(view: android.view.View) {

        val playIntent= Intent(this, GameActivity::class.java)

        /*val playBtn = findViewById<View>(R.id.play_imageView)

        val a1= ObjectAnimator.ofFloat(playBtn,"translationY",10F)
        val a2=ObjectAnimator.ofFloat(playBtn,"translationY",-10F)
        a1.duration = 100
        a2.duration = 100


        a1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                mySounds.playSoundPlay()
            }
            override fun onAnimationEnd(animator: Animator) {
                a2.start()
                startActivity(playIntent)
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        a1.start()*/
    }

}
