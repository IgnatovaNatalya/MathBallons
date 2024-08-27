package ru.mamsikgames.mathballoons

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class BalloonAnimGif(
    _con: Context,
    _lay: ConstraintLayout,
    _posX: Float,
    _gameS: GameStrategy,
    _gameSounds: GameSounds,
    _gameAnimations: BalloonAnimations
) : ConstraintLayout(_con) {

    constructor(
        _con: Context,
        _lay: ConstraintLayout,
        _posX: Float,
        _gameS: GameStrategy,
        _gameSounds: GameSounds,
        _gameAnimations: BalloonAnimations,
        _clr: Int
    ) : this(
        _con,
        _lay,
        _posX,
        _gameS,
        _gameSounds,
        _gameAnimations
    ) {
        balloonColor = _clr
    }

    val con: Context = _con
    var balloonNum: Int
    var balloonBtn: Button

    private val gameS = _gameS
    private val mySounds: GameSounds = _gameSounds
    private val gameAnimations = _gameAnimations
    private var balloonLayout = _lay
    private var initX = _posX
    private var initY = 1800F - 300

    //private var balloonBg: TextView
    private var balloonImage: ImageView

    private var balloonColors = arrayOf(
        R.drawable.red_00,
        R.drawable.orange_00,
        R.drawable.yellow_00,
        R.drawable.green_00,
        R.drawable.lightblue_00,
        R.drawable.blue_00,
        R.drawable.violet_00,
        R.drawable.pink_00
    )

    private var balloonGifAnimations = arrayOf(
        R.drawable.animation_burst_blue,
        R.drawable.animation_burst_blue,
        R.drawable.animation_burst_blue,
        R.drawable.animation_burst_blue,
        R.drawable.animation_burst_blue,
        R.drawable.animation_burst_blue,
        R.drawable.animation_burst_blue,
        R.drawable.animation_burst_blue,
    )

    private var balloonNums = arrayOf(
        R.drawable.num_0,
        R.drawable.num_1,
        R.drawable.num_2,
        R.drawable.num_3,
        R.drawable.num_4,
        R.drawable.num_5,
        R.drawable.num_6,
        R.drawable.num_7,
        R.drawable.num_8,
        R.drawable.num_9,
    )

    private var balloonColor = (balloonColors.indices).random()


    init {
        val layoutParam = LayoutParams(BALLONSIZE, BALLONSIZE)
        layoutParams = layoutParam

        balloonLayout.addView(this)
        x = initX
        y = initY
        balloonNum = gameS.getRandom()

        balloonImage = ImageView(_con)
        addView(balloonImage)

        balloonImage.setBackgroundResource(balloonColors[balloonColor])

        balloonBtn = Button(_con)///this?
        addView(balloonBtn)

        val buttonLayoutParams by lazy {
            LayoutParams(BUTTONWIDTH, BUTTONHEIGHT).apply {
                bottomToBottom = LayoutParams.PARENT_ID
                startToStart = LayoutParams.PARENT_ID
                topToTop = LayoutParams.PARENT_ID
                endToEnd = LayoutParams.PARENT_ID
                horizontalBias = 0.5F
            }
        }

        balloonBtn.layoutParams = buttonLayoutParams
        balloonBtn.setBackgroundResource(balloonNums[balloonNum])

        balloonBtn.text = " "
    }

    fun changeBalloon() {
        alpha = 1F
        balloonNum = gameS.getRandom()
        balloonBtn.setBackgroundResource(balloonNums[balloonNum])
        balloonColor = (balloonColors.indices).random()
        balloonImage.setBackgroundResource(balloonColors[balloonColor])
        x = initX
        y = initY
        balloonBtn.isClickable = true
    }

    fun removeBalloon() {
        alpha = 0f
        balloonBtn.isClickable = false
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
                changeBalloon()
                moveBalloon()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    fun burstBalloon() {
        //взрывается либо пукает

        val res = gameS.iNum == balloonNum

        balloonBtn.setBackgroundResource(0)
        balloonBtn.isClickable = false

        if (res)
            mySounds.playSoundBurst()
        else
            mySounds.playSoundPuk()

//        Glide.with(this).load(R.drawable.animation_burst_blue).into(balloonImage)
        Glide
            .with(this).asGif()
            .load(R.drawable.animation_burst_blue)
            .listener(object : RequestListener<GifDrawable> {
                override fun onResourceReady(
                    resource: GifDrawable,model: Any?,target: Target<GifDrawable>?,dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource.setLoopCount(1)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,model: Any?,target: Target<GifDrawable>?,isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(balloonImage)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            Glide.with(this).load(R.drawable.frame_32).into(balloonImage)
        }, 640)

    }

    fun burstMega() {
        //взрывается большим взрывом

        //balloonImage.text = ""
        balloonBtn.isClickable = false

        mySounds.playSoundBurst()

        //balloonBg.setBackgroundResource(R.drawable.balloon_yellow_mega)

        // val megaBurstAnimation: AnimationDrawable = balloonBg.background as AnimationDrawable
        //megaBurstAnimation.start()
    }


}

