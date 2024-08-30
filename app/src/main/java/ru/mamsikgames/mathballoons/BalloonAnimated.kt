package ru.mamsikgames.mathballoons

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator


class BalloonAnimated(context: Context?, val num: Int) : View(context) {

    private var bitmap: Bitmap
    private var frameWidth = 0// 600 // ширина одного кадра
    private var frameHeight = 0// 600 // высота кадра
    private var frameRect: Rect
    private val dstRect: Rect = Rect(0, 0, BALLONSIZE, BALLONSIZE)

    //private var currentFrame = 2 // Текущий кадр
    var frameCount = 32
    //var frameDuration = 25 // Длительность кадра, миллисекунды
    var isBursting = false

    //private var bitmaps: MutableList<Bitmap> = mutableListOf()

    private var textNum = num.toString()
    private val paint = Paint()

    private var textWidth = 0F
    private var textNumX = 0F
    private var textNumY = 0F

    private val balloonFrames = arrayOf(
        R.drawable.a_frames_blue,
        R.drawable.a_frames_orange,
        R.drawable.a_frames_lightblue,
        R.drawable.a_frames_pink,
        R.drawable.a_frames_red,
        R.drawable.a_frames_violet,
        R.drawable.a_frames_yellow)

    init {

        val options = BitmapFactory.Options()
        options.inSampleSize = 2
        bitmap = BitmapFactory.decodeResource(resources, balloonFrames[balloonFrames.indices.random()], options)

        frameWidth = bitmap.width / frameCount
        frameHeight = bitmap.height
        frameRect = Rect(0, 0, frameWidth, frameHeight)

        paint.isAntiAlias = true
        paint.setColor(Color.WHITE)
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        paint.textSize = FONTSIZE

        textWidth = paint.measureText(textNum)
        textNumX = (BALLONSIZE - textWidth) / 2F
        textNumY = BALLONSIZE * 0.53F
    }

     fun burstBalloon() {
        isBursting = true

        val handler = Handler(Looper.getMainLooper())

        for (currentFrame in (1..frameCount)) {
            handler.postDelayed({
                val leftCorner = currentFrame * frameWidth
                val rightCorner = leftCorner + frameWidth
                frameRect = Rect(leftCorner, 0, rightCorner, frameHeight)
                invalidate()
            }, (currentFrame * DURATION))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawColor(Color.GREEN)
        canvas.drawBitmap(bitmap, frameRect, dstRect, null)

        if (!isBursting)
            canvas.drawText(textNum, textNumX, textNumY, paint)
    }


    fun moveBalloon() {

        val posStartX = this.x
        val posStartY = this.y

        val rand: Int = (-20..20).random()

        val myPath1 = Path().apply {
            moveTo(posStartX + rand, posStartY)
            rLineTo(rand.toFloat(), (-100 + 2.5 * rand).toFloat())
        }

        myPath1.apply {
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
            rLineTo(10F, -100F)
            rLineTo(-10F, -100F)
        }

        val animator = ObjectAnimator.ofFloat(this, View.X, View.Y, myPath1).apply {
            duration = 15000
            start()
        }

        animator.interpolator = LinearInterpolator()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                //changeBalloon()
                //moveBalloon()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }
}



