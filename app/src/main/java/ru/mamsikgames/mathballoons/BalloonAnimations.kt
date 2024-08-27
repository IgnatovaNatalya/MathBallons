
package ru.mamsikgames.mathballoons

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.content.res.AppCompatResources
import ru.mamsikgames.mathballoons.R
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future

class BalloonAnimations(_con: Context) {

    var loaded = false
    private val con: Context = _con

    private val balloonRedFrames = arrayOf(
        R.drawable.red_00,
        R.drawable.red_01,
        R.drawable.red_02,
        R.drawable.red_03,
        R.drawable.red_04,
        R.drawable.red_05,
        R.drawable.red_06,
        R.drawable.red_07,
        R.drawable.red_08,
        R.drawable.red_09,
        R.drawable.red_10,
        R.drawable.red_11,
        R.drawable.red_12,
        R.drawable.red_13,
        R.drawable.red_14,
        R.drawable.red_15,
        R.drawable.red_16,
        R.drawable.red_17,
        R.drawable.red_18,
        R.drawable.red_19,
        R.drawable.red_20,
        R.drawable.red_21,
        R.drawable.red_22,
        R.drawable.red_23,
        R.drawable.red_24,
        R.drawable.red_25,
        R.drawable.red_26,
        R.drawable.red_27,
        R.drawable.red_28,
        R.drawable.red_29,
        R.drawable.red_30,
        R.drawable.red_31,
        R.drawable.frame_32
    )
    private val balloonOrangeFrames = arrayOf(
        R.drawable.orange_00,
        R.drawable.orange_01,
        R.drawable.orange_02,
        R.drawable.orange_03,
        R.drawable.orange_04,
        R.drawable.orange_05,
        R.drawable.orange_06,
        R.drawable.orange_07,
        R.drawable.orange_08,
        R.drawable.orange_09,
        R.drawable.orange_10,
        R.drawable.orange_11,
        R.drawable.orange_12,
        R.drawable.orange_13,
        R.drawable.orange_14,
        R.drawable.orange_15,
        R.drawable.orange_16,
        R.drawable.orange_17,
        R.drawable.orange_18,
        R.drawable.orange_19,
        R.drawable.orange_20,
        R.drawable.orange_21,
        R.drawable.orange_22,
        R.drawable.orange_23,
        R.drawable.orange_24,
        R.drawable.orange_25,
        R.drawable.orange_26,
        R.drawable.orange_27,
        R.drawable.orange_28,
        R.drawable.orange_29,
        R.drawable.orange_30,
        R.drawable.orange_31,
        R.drawable.frame_32
    )
    private val balloonYellowFrames = arrayOf(
        R.drawable.yellow_00,
        R.drawable.yellow_01,
        R.drawable.yellow_02,
        R.drawable.yellow_03,
        R.drawable.yellow_04,
        R.drawable.yellow_05,
        R.drawable.yellow_06,
        R.drawable.yellow_07,
        R.drawable.yellow_08,
        R.drawable.yellow_09,
        R.drawable.yellow_10,
        R.drawable.yellow_11,
        R.drawable.yellow_12,
        R.drawable.yellow_13,
        R.drawable.yellow_14,
        R.drawable.yellow_15,
        R.drawable.yellow_16,
        R.drawable.yellow_17,
        R.drawable.yellow_18,
        R.drawable.yellow_19,
        R.drawable.yellow_20,
        R.drawable.yellow_21,
        R.drawable.yellow_22,
        R.drawable.yellow_23,
        R.drawable.yellow_24,
        R.drawable.yellow_25,
        R.drawable.yellow_26,
        R.drawable.yellow_27,
        R.drawable.yellow_28,
        R.drawable.yellow_29,
        R.drawable.yellow_30,
        R.drawable.yellow_31,
        R.drawable.frame_32
    )
    private var balloonGreenFrames = arrayOf(
        R.drawable.green_00,
        R.drawable.green_01,
        R.drawable.green_02,
        R.drawable.green_03,
        R.drawable.green_04,
        R.drawable.green_05,
        R.drawable.green_06,
        R.drawable.green_07,
        R.drawable.green_08,
        R.drawable.green_09,
        R.drawable.green_10,
        R.drawable.green_11,
        R.drawable.green_12,
        R.drawable.green_13,
        R.drawable.green_14,
        R.drawable.green_15,
        R.drawable.green_16,
        R.drawable.green_17,
        R.drawable.green_18,
        R.drawable.green_19,
        R.drawable.green_20,
        R.drawable.green_21,
        R.drawable.green_22,
        R.drawable.green_23,
        R.drawable.green_24,
        R.drawable.green_25,
        R.drawable.green_26,
        R.drawable.green_27,
        R.drawable.green_28,
        R.drawable.green_29,
        R.drawable.green_30,
        R.drawable.green_31,
        R.drawable.frame_32
    )
    private var balloonLightblueFrames = arrayOf(
        R.drawable.lightblue_00,
        R.drawable.lightblue_01,
        R.drawable.lightblue_02,
        R.drawable.lightblue_03,
        R.drawable.lightblue_04,
        R.drawable.lightblue_05,
        R.drawable.lightblue_06,
        R.drawable.lightblue_07,
        R.drawable.lightblue_08,
        R.drawable.lightblue_09,
        R.drawable.lightblue_10,
        R.drawable.lightblue_11,
        R.drawable.lightblue_12,
        R.drawable.lightblue_13,
        R.drawable.lightblue_14,
        R.drawable.lightblue_15,
        R.drawable.lightblue_16,
        R.drawable.lightblue_17,
        R.drawable.lightblue_18,
        R.drawable.lightblue_19,
        R.drawable.lightblue_20,
        R.drawable.lightblue_21,
        R.drawable.lightblue_22,
        R.drawable.lightblue_23,
        R.drawable.lightblue_24,
        R.drawable.lightblue_25,
        R.drawable.lightblue_26,
        R.drawable.lightblue_27,
        R.drawable.lightblue_28,
        R.drawable.lightblue_29,
        R.drawable.lightblue_30,
        R.drawable.lightblue_31,
        R.drawable.frame_32
    )
    private var balloonBlueFrames = arrayOf(
        R.drawable.blue_00,
        R.drawable.blue_01,
        R.drawable.blue_02,
        R.drawable.blue_03,
        R.drawable.blue_04,
        R.drawable.blue_05,
        R.drawable.blue_06,
        R.drawable.blue_07,
        R.drawable.blue_08,
        R.drawable.blue_09,
        R.drawable.blue_10,
        R.drawable.blue_11,
        R.drawable.blue_12,
        R.drawable.blue_13,
        R.drawable.blue_14,
        R.drawable.blue_15,
        R.drawable.blue_16,
        R.drawable.blue_17,
        R.drawable.blue_18,
        R.drawable.blue_19,
        R.drawable.blue_20,
        R.drawable.blue_21,
        R.drawable.blue_22,
        R.drawable.blue_23,
        R.drawable.blue_24,
        R.drawable.blue_25,
        R.drawable.blue_26,
        R.drawable.blue_27,
        R.drawable.blue_28,
        R.drawable.blue_29,
        R.drawable.blue_30,
        R.drawable.blue_31,
        R.drawable.frame_32
    )
    private var balloonVioletFrames = arrayOf(
        R.drawable.violet_00,
        R.drawable.violet_01,
        R.drawable.violet_02,
        R.drawable.violet_03,
        R.drawable.violet_04,
        R.drawable.violet_05,
        R.drawable.violet_06,
        R.drawable.violet_07,
        R.drawable.violet_08,
        R.drawable.violet_09,
        R.drawable.violet_10,
        R.drawable.violet_11,
        R.drawable.violet_12,
        R.drawable.violet_13,
        R.drawable.violet_14,
        R.drawable.violet_15,
        R.drawable.violet_16,
        R.drawable.violet_17,
        R.drawable.violet_18,
        R.drawable.violet_19,
        R.drawable.violet_20,
        R.drawable.violet_21,
        R.drawable.violet_22,
        R.drawable.violet_23,
        R.drawable.violet_24,
        R.drawable.violet_25,
        R.drawable.violet_26,
        R.drawable.violet_27,
        R.drawable.violet_28,
        R.drawable.violet_29,
        R.drawable.violet_30,
        R.drawable.violet_31,
        R.drawable.frame_32
    )
    private var balloonPinkFrames = arrayOf(
        R.drawable.pink_00,
        R.drawable.pink_01,
        R.drawable.pink_02,
        R.drawable.pink_03,
        R.drawable.pink_04,
        R.drawable.pink_05,
        R.drawable.pink_06,
        R.drawable.pink_07,
        R.drawable.pink_08,
        R.drawable.pink_09,
        R.drawable.pink_10,
        R.drawable.pink_11,
        R.drawable.pink_12,
        R.drawable.pink_13,
        R.drawable.pink_14,
        R.drawable.pink_15,
        R.drawable.pink_16,
        R.drawable.pink_17,
        R.drawable.pink_18,
        R.drawable.pink_19,
        R.drawable.pink_20,
        R.drawable.pink_21,
        R.drawable.pink_22,
        R.drawable.pink_23,
        R.drawable.pink_24,
        R.drawable.pink_25,
        R.drawable.pink_26,
        R.drawable.pink_27,
        R.drawable.pink_28,
        R.drawable.pink_29,
        R.drawable.pink_30,
        R.drawable.pink_31,
        R.drawable.frame_32
    )

    private var balloonFrames = arrayOf(
        balloonRedFrames,
        balloonOrangeFrames,
        balloonYellowFrames,
        balloonGreenFrames,
        balloonLightblueFrames,
        balloonBlueFrames,
        balloonVioletFrames,
        balloonPinkFrames
    )

    private val animationsList = mutableListOf<AnimationDrawable>()


    internal class CallableClass(_color:Int,_ba:BalloonAnimations) : Callable<AnimationDrawable> {

        private val color=_color
        private var ba=_ba

        override fun call(): AnimationDrawable {
                return ba.addAnimation(color)
        }
    }


    init {

        val executor = Executors.newFixedThreadPool(3)

        val futures: List<Future<AnimationDrawable>>

        futures = ArrayList()

        for (i in balloonFrames.indices) {

            val callable = CallableClass(i,this)

            val future: Future<AnimationDrawable> = executor.submit(callable)
            futures.add(future)
        }

        for (future in futures) {
            try {
                animationsList.add(future.get())
                println("анимация "+ animationsList.lastIndex + " загружена")
            } catch (e: InterruptedException) {
            } catch (e: ExecutionException) {
            }
        }
        executor.shutdown()

            /*for (i in balloonFrames.indices) {
            thread {
                animationsList.add(addAnimation(i))
            }
        }*/
    }


    fun burstAnimation( color:Int):AnimationDrawable {
        val myAnimation =animationsList[color]
        myAnimation.setVisible(true, false)
        return myAnimation
    }

    private fun addAnimation(color:Int):AnimationDrawable {

        val myAnimation = AnimationDrawable()
        val arrFrames=balloonFrames[color]

        for (i in  arrFrames.indices) {
            myAnimation.addFrame(
                AppCompatResources.getDrawable(
                    con,
                    arrFrames[i]
                ) as BitmapDrawable, DURATION
            )
        }

        myAnimation.isOneShot = true

        return myAnimation
    }

}