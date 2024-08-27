package ru.mamsikgames.mathballoons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

lateinit var musicIntent: Intent

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        musicIntent = Intent(this, BackgroundMusic::class.java)
        startService(musicIntent)


        startActivity(Intent(this,GameActivity::class.java))
        finish()




    }
}