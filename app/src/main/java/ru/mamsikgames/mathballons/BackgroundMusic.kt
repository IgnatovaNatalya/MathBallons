package ru.mamsikgames.mathballoons

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import ru.mamsikgames.mathballons.R


class BackgroundMusic : Service() {
    private lateinit var mPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        mPlayer = MediaPlayer.create(this, R.raw.glitter_blast)
        mPlayer.isLooping = true // Set looping
        mPlayer.setVolume(0.05f, 0.05f)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mPlayer.stop()
        mPlayer.release()
    }

}