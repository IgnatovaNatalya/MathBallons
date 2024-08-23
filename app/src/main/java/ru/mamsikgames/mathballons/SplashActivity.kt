package ru.mamsikgames.mathballoons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import ru.mamsikgames.mathballons.R

lateinit var musicIntent: Intent

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        musicIntent = Intent(this, BackgroundMusic::class.java)
        startService(musicIntent)


        startActivity(Intent(this,GameActivity::class.java))
        finish()




    }
}