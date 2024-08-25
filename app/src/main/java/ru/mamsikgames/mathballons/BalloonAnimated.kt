package ru.mamsikgames.mathballons

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
import android.view.View
import android.view.animation.LinearInterpolator

class BalloonAnimated(context: Context?, val num: Int) : View(context) {

    private var frameWidth = 600 // ширина одного кадра
    private var frameHeight = 600 // высота кадра
    private var frameRect: Rect = Rect(0, 0, frameWidth, frameHeight)
    private var currentFrame = 0 // Текущий кадр
    private var frameCount = 0
    private var isBursting = false

    private var bitmaps: MutableList<Bitmap> = mutableListOf()


    private var textNum = num.toString()
    private val paint = Paint()

    private var textNumX = 0F
    private var textNumY = 0F

    init {
        currentFrame = 0
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_00))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_01))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_02))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_03))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_04))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_05))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_06))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_07))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_08))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_09))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_10))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_11))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_12))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_13))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_14))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_15))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_16))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_17))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_18))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_19))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_20))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_21))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_22))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_23))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_24))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_25))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_26))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_27))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_28))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_29))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_30))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.red_31))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.frame_32))

        frameCount = bitmaps.size

        paint.isAntiAlias = true
        paint.setColor(Color.WHITE)
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.textSize = 80.0f

        val textWidth = paint.measureText(textNum);

        textNumX = (frameWidth - textWidth) / 2F
        textNumY = frameWidth * 0.53F

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        if (isBursting)
//            if (currentFrame + 1 < frameCount) {
//                currentFrame++
//                postInvalidateOnAnimation()
//            }
//            else
//                isBursting = false

        canvas.drawBitmap(bitmaps[currentFrame], null, frameRect, null);

        if (currentFrame<=2)
            canvas.drawText(textNum, textNumX, textNumY, paint)
    }

    fun burst() {
        isBursting = true
        currentFrame = 0
    }

    fun moveBalloon() {

        val posStartX = this.x
        val posStartY = this.y

        var rand: Int = (-20..20).random()

        var myPath1 = Path().apply {
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



