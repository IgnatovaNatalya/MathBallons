package ru.mamsikgames.mathballoons
import android.content.Context
import android.media.SoundPool
import android.media.AudioAttributes
import android.media.SoundPool.*
import ru.mamsikgames.mathballons.R


class GameSounds(_con: Context) {
    var con= _con

    private var streamID = 0

    private var soundPlay= R.raw.button_play_click_wav
    private var soundButtons = R.raw.menu_button_click
    private var soundBurst = R.raw.lopnul2
    private var soundPuk = R.raw.fart
    private var soundLevel = R.raw.nextlevel
    private var soundNewtask = R.raw.orchestra
    private var soundCorrect= R.raw.rise06

    private var sPlay=0
    private var sButton = 0
    private var sBurst = 0
    private var sPuk = 0
    private var sLevel =0
    private var sNewtask=0
    private var sCor=0

    private var lopniNumSounds = arrayOf(
        R.raw.lopni_zero,
        R.raw.lopni_one,
        R.raw.lopni_two,
        R.raw.lopni_three,
        R.raw.lopni_four,
        R.raw.lopni_five,
        R.raw.lopni_six,
        R.raw.lopni_seven,
        R.raw.lopni_eight,
        R.raw.lopni_nine
    )
    //private var sNumSounds = Array<Int>(10,0)
    private var sNumSounds = arrayOf(0,0,0,0,0,0,0,0,0,0)

    var  sPool:SoundPool

    init {

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        sPool = Builder()
            .setAudioAttributes(attributes)
            .setMaxStreams(6)
            .build()
        sPlay = sPool.load(con, soundPlay, 1)
        sButton = sPool.load(con, soundButtons, 1)
        sBurst = sPool.load(con, soundBurst, 1 )
        sPuk = sPool.load(con, soundPuk, 1)
        sLevel = sPool.load(con, soundLevel, 2)
        sNewtask=sPool.load(con, soundNewtask, 2)
        sCor=sPool.load(con, soundCorrect, 2)

        for ( i in lopniNumSounds.indices) {
            sNumSounds[i] = sPool.load(con, lopniNumSounds[i],2)
        }
    }

    fun playSoundPlay() {
        playSound(sPlay)
    }

    fun playSoundButton() {
        playSound(sButton)
    }

    fun playSoundBurst() {
        playSound(sBurst)
    }

    fun playSoundPuk() {
        playSound(sPuk)
    }

    fun playSoundNum(num:Int) {
        playSound(sNumSounds[num])
    }

    fun playSoundLevel() {
        playSound(sLevel)
    }

    fun playSoundNewtask() {
        playSound(sNewtask)
    }

    fun playSoundCor() {
        playSound(sCor)
    }

    private fun playSound(sound: Int): Int {
        if (sound > 0) {
            streamID = sPool.play(sound, 1F, 1F, 1, 0, 1F)
        }
        return streamID
    }

}

