package ru.mamsikgames.mathballoons

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mamsikgames.mathballons.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() ///
        musicIntent = Intent(this, BackgroundMusic::class.java)
        startService(musicIntent)


        val playIntent= Intent(this, GameActivity::class.java)
        startActivity(playIntent)
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
