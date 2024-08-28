package ru.mamsikgames.mathballoons

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() ///

        //musicIntent = Intent(this, BackgroundMusic::class.java)
        //startService(musicIntent)

        val gameIntent = Intent(this, GameActivity::class.java)

//        val ll_main = findViewById<ConstraintLayout>(R.id.ll_main)
//        val startBalloon = BalloonAnimGif(
//            _con = this,
//            _lay = ll_main,
//            _posX = 100F,
//            _gameS = GameStrategy(),
//            _gameSounds = GameSounds(this),
//            _gameAnimations = BalloonAnimations(this)
//        )

        val startBalloon =findViewById<ImageView>(R.id.yellow_launcher)
        startBalloon.setOnClickListener {
            Glide
                .with(this).asGif()
                .load(R.drawable.animation_burst_blue)
                .listener(object : RequestListener<GifDrawable> {
                    override fun onResourceReady(
                        resource: GifDrawable, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        println("animation ready")
                        resource.setLoopCount(1)
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(startBalloon)

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                startActivity(gameIntent)
            }, 2000)

        }


        //Glide.with(this).load(R.drawable.frame_32).into(startBalloon)
        //startBalloon.moveBalloon()

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



