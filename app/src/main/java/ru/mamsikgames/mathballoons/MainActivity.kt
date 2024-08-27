package ru.mamsikgames.mathballoons

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() ///

        //musicIntent = Intent(this, BackgroundMusic::class.java)
        //startService(musicIntent)

        //val gameIntent = Intent(this, GameActivity::class.java)

        val ll_main = findViewById<ConstraintLayout>(R.id.ll_main)
        val startBalloon = BalloonAnimGif(
            _con = this,
            _lay = ll_main,
            _posX = 100F,
            _gameS = GameStrategy(),
            _gameSounds = GameSounds(this),
            _gameAnimations = BalloonAnimations(this)
        )

        startBalloon.setOnClickListener { startBalloon.burstBalloon() }

        startBalloon.moveBalloon()

    }
}


//        startBalloon.setOnClickListener {
//            gameSounds.playSoundBurst()
//            val handler = Handler(Looper.getMainLooper())
//            handler.postDelayed({
//                startActivity(gameIntent)
//            }, 100)
//

//
//        val balloonLayoutParams by lazy {
//            LayoutParams(600, 600).apply {
//                bottomToBottom = LayoutParams.PARENT_ID
//                startToStart = LayoutParams.PARENT_ID
//                topToTop = LayoutParams.PARENT_ID
//                endToEnd = LayoutParams.PARENT_ID
//            }
//        }
//        startBalloon.layoutParams = balloonLayoutParams



//        startBalloon.setOnClickListener {
//            gameSounds.playSoundBurst()
//            startBalloon.burst()
//            val handler = Handler(Looper.getMainLooper())
//            handler.postDelayed({
//                startActivity(gameIntent)
//            }, 500)
//        }



