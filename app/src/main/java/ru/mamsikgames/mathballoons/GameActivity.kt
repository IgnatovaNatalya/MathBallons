package ru.mamsikgames.mathballoons

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


const val ROUNDTIMES = 119      //время раунда в секундах
const val TASKTIMES = 15        //время одного задания в секундах
const val QUANNUMBERS = 10      //количество загаданных шариков на задание
const val INTERVALMS = 1000     //время между появлением шариков, миллисекунды
const val NROWS = 3             //количество рядов шариков на экране
const val NCOLS = 3            //количество шариков в одном ряду на экране !=0
const val DURATION = 25         //длительность кадра внимации взрыва, миллисекунды
const val BALLONSIZE = 1200     //размеры картинки шарика (и взрыва)
const val BUTTONWIDTH = 250     //ширина активной области шарика
const val BUTTONHEIGHT = 300    //высота активной области шарика


//private var balloonsList = mutableListOf<Balloon>()
//private var balloonsList = mutableListOf<BalloonAnimGif>()
private var balloonsList = mutableListOf<BalloonAnimated>()

//private var timerBackgrounds = arrayOf(
//    R.drawable.green_bar1,
//    R.drawable.green_bar2,
//    R.drawable.green_bar3,
//    R.drawable.green_bar4,
//    R.drawable.green_bar5,
//    R.drawable.green_bar6,
//    R.drawable.green_bar7,
//    R.drawable.green_bar8,
//    R.drawable.green_bar9,
//    R.drawable.green_bar10,
//    R.drawable.green_bar11,
//    R.drawable.green_bar12
//)

private var pointsX = mutableListOf<Int>() //положения шаров по оси X

private var correctCounter = 0  //счетчик правильных ответов
private var wrongCounter = 0    //неправильных
private var iLop = 0            //сколько лопнуто за одно задание

private lateinit var ll_game: ConstraintLayout
private lateinit var gameStrategy: GameStrategy
private lateinit var gameSounds: GameSounds
private lateinit var gameAnimations: BalloonAnimations

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        ll_game = findViewById(R.id.ll_game)
        //val con: Context = this
        gameStrategy = GameStrategy()
        gameSounds = GameSounds(this)
        gameAnimations = BalloonAnimations(this)

        //hideLoad()
        prepareXs()
        //clock()
        //makeBalloons()
        //makeOneBalloon()
        makeBalloonAnimated()
        //makeGifBalloons()
        //test()
        //play()

    }


    /* private fun hideLoad() {
         val loadBg=findViewById<View>(R.id.loadBg)
         val loadName =findViewById<View>(R.id.loadName)
         val loadText=findViewById<View>(R.id.loadText)
         val loadBar =findViewById<View>(R.id.loadProgress)

         loadBg.isVisible=false
         loadName.isVisible=false
         loadText.isVisible=false
         loadBar.isVisible=false

     }*/

    private fun prepareXs() {
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val offsetscreen = width / 10 / (NCOLS - 1)

        val dx = (width - offsetscreen * 2 - BUTTONWIDTH) / (NCOLS - 1)
        val offset = offsetscreen - (BALLONSIZE - BUTTONWIDTH) / 2

        for (i in 0..<NCOLS) {
            pointsX.add(i * dx + offset)
        }
    }

//    private fun makeGifBalloons() {
//
//        val handler = Handler(Looper.getMainLooper())
//
//        for (i in (1..NROWS)) {
//            val myListPosX = mutableListOf<Int>()
//            myListPosX.addAll(pointsX)
//            myListPosX.shuffle()
//
//            for (j in pointsX.indices) {
//                handler.postDelayed({
//                    val ball = newBalloon(myListPosX[j].toFloat())
//                    ball.x = pointsX[j].toFloat()
//                    ball.y = 1700F//1800F-300F
//                    ball.setOnClickListener { pressBalloon(ball) }
//                    balloonsList.add(ball)
//
//                    ball.moveBalloon()
//                }, ((i - 1) * INTERVALMS * pointsX.size + j * INTERVALMS).toLong())
//
//            }
//        }
//    }

//    private fun newBalloon(posX: Float): BalloonAnimGif {
//
//        val myBalloon = BalloonAnimGif(this, ll_game, posX, gameStrategy, gameSounds)
//        myBalloon.balloonBtn.setOnClickListener { pressBalloon(myBalloon) }
//
//
//        return myBalloon
//    }


    //    private fun makeBalloons() { //генерируем объем шаров достаточный для заполнения экрана и выпускаем их с заданным интервалом
//
//        val handler = Handler(Looper.getMainLooper())
//
//        for (i in (1..NROWS)) {
//            val myListPosX = mutableListOf<Int>()
//            myListPosX.addAll(pointsX)
//            myListPosX.shuffle()
//
//            for (j in pointsX.indices) {
//                handler.postDelayed({
//                    //val ball = newBalloon(myListPosX[j].toFloat())
//                    val ball = BalloonAnimated( this,7)
//                    ball.x = pointsX[j].toFloat()
//                    ball.y = 1700F//1800F-300F
//                    ball.setOnClickListener { pressBalloon(ball) }
//                    balloonsList.add(ball)
//                    ll_game.addView(ball)
//                    ball.moveBalloon()
//                }, INTERVALMS.toLong()) //( (i - 1) * INTERVALMS * pointsX.size + j * INTERVALMS).toLong())
//            }
//        }
//    }
    @SuppressLint("ClickableViewAccessibility")
    private fun makeBalloonAnimated() {

        val handler = Handler(Looper.getMainLooper())

        for (i in (1..NROWS)) {
            //val myListPosX = mutableListOf<Int>()
            //myListPosX.addAll(pointsX)
            // myListPosX.shuffle()

            for (j in pointsX.indices) {
                handler.postDelayed({

                    val balloonAnimated = BalloonAnimated(this, j)//

                    balloonAnimated.x = pointsX[j].toFloat()
                    balloonAnimated.y = 1800F//1800F-300F

                    balloonsList.add(balloonAnimated)
                    balloonAnimated.setLayoutParams(ConstraintLayout.LayoutParams(1000, 1000));
                    ll_game.addView(balloonAnimated)
                    balloonAnimated.moveBalloon()

                    //balloonAnimated.setOnClickListener {  pressBalloon(balloonAnimated) }

                    balloonAnimated.setOnTouchListener { _, event ->
                        var isProcessed = false
                        if (event.action == MotionEvent.ACTION_DOWN) {
                            if (!balloonAnimated.isBursting && event.x > 400F && event.x < 600F && event.y > 300F && event.y < 700F) {
                                balloonAnimated.burstBalloon()
                                val handler = Handler(Looper.getMainLooper())
                                handler.postDelayed({
                                    ll_game.removeView(balloonAnimated)
                                }, balloonAnimated.frameCount * balloonAnimated.frameDuration)
                                isProcessed = true
                            }
                        }
                        isProcessed
                    }
                }, ((i - 1) * INTERVALMS * pointsX.size + j * INTERVALMS).toLong())
            }
        }
    }

    //private fun pressBalloon(ball: Balloon) {
    private fun pressBalloon(ball: BalloonAnimated) {
        //private fun pressBalloon(ball: BalloonAnimGif) {
        ball.burstBalloon()
    }

    private fun test() {

        val rounds = ROUNDTIMES / TASKTIMES - 1

        val handlerSay = Handler(Looper.getMainLooper())

        for (r in (0..rounds)) {           //обновляем задачу "Лопни все шарики..." в заданный интервал
            handlerSay.postDelayed({
                gameStrategy.newTask()
                setDop(gameStrategy.iNum)
                sayLopni()
            }, (r * TASKTIMES * 1000).toLong())
        }
    }

    private fun play() {

        val rounds = ROUNDTIMES / TASKTIMES - 1

        val handlerSay = Handler(Looper.getMainLooper())

        for (r in (0..rounds)) {           //обновляем задачу "Лопни все шарики..." в заданный интервал
            handlerSay.postDelayed({
                gameStrategy.newTask()
                setDop(gameStrategy.iNum)
                sayLopni()
            }, (r * TASKTIMES * 1000).toLong())
        }

        handlerSay.postDelayed({ //окончание раунда
            winScreen(true)
        }, ((rounds + 1) * TASKTIMES * 1000).toLong())

    }

    private fun sayLopni() {

        iLop = 0
        gameSounds.playSoundNum(gameStrategy.iNum)

        if (!gameStrategy.first) {
            gameSounds.playSoundNewtask()
        }
    }


//    private fun clock() { //обновляем статусбар с оставшимся временем каждую секунду
//
//        val timerBg = findViewById<TextView>(R.id.timer_text)
//        var i=0
//        val handlerTimer = Handler(Looper.getMainLooper())

    /*val restTimeS=roundTimeS-roundTimeM*60
    for (m in 0..roundTimeM-1) {
        for (s in 0..59) {
            handlerTimer.postDelayed({

                setTimer(1-m, 59-s)

                if ((m*60 + s)%10 ==0) {
                    timerBg.setBackgroundResource(timerBackgrounds[i])
                    i++
                }
            }, ((m*60 + s)*1000).toLong())
        }
    }*/

//        for (s in 0..ROUNDTIMES) {
//            handlerTimer.postDelayed({
//
//                setTimer(ROUNDTIMES-s)
//
//                if (s%10 == 0) {
//                    timerBg.setBackgroundResource(timerBackgrounds[i])
//                    i++
//                }
//            },(s*1000).toLong())
//        }
//    }


    private fun winScreen(mode: Boolean) {  //отображение или скрытие экрана окончания раунда

        gameSounds.playSoundLevel()

        var shift = 2000F

        if (!mode) {
            shift = -2000F
        }

        //val myText = findViewById<TextView>(R.id.win_text)

        //if (mode) {myText.text ="$correctCounter баллов!"}

        /*val winObjects = arrayOf(
            findViewById<ImageView>(R.id.win_star_1),
            findViewById<ImageView>(R.id.win_star_2),
            findViewById<ImageView>(R.id.win_star_3),
            findViewById<Button>(R.id.win_replay_btn),
            findViewById<Button>(R.id.win_awards_btn),
            findViewById<Button>(R.id.win_play_btn),
            myText
        )*/

        /*for (i in 0..winObjects.size -1) {
            ObjectAnimator.ofFloat(winObjects[i],"translationY",shift).start()
        }*/

    }

//    private fun burstNum(defNum: Int) {
//        val handler = Handler(Looper.getMainLooper())
//
//        var i = 0
//        for (ball in balloonsList) {
//            if (ball.balloonNum==defNum) {
//                handler.postDelayed({
//                    ball.burstBalloon()///
//                }, (i * 100).toLong())
//                i++
//            }
//        }
//    }

//    private fun burstAndPlay() {
//        val handler = Handler(Looper.getMainLooper())
//
//        var i = 0
//        for (ball in balloonsList) {
//            if (i>=3) ball.removeBalloon()
//            handler.postDelayed({
//                if ( i<3) ball.burstMega()///
//
//            }, (i * 100).toLong())
//            i++
//        }
//
//        handler.postDelayed({
//            gameStrategy.newRound()
//            clock()
//            play()
//        },(i*100).toLong())
//    }


    private fun setCorrect(num: Int) {
        val myTextView = findViewById<TextView>(R.id.correct_text)
        myTextView.text = num.toString()
    }

    private fun setWrong(num: Int) {
        val myTextView = findViewById<TextView>(R.id.wrong_text)
        myTextView.text = num.toString()
    }

    private fun setDop(num: Int) {
        val myTextView = findViewById<TextView>(R.id.dop_text)
        myTextView.text = num.toString()
    }

    private fun setDop2(num: Int) {
        val myTextView = findViewById<TextView>(R.id.dop2_text)
        myTextView.text = num.toString()
    }

    private fun setTimer(timeM: Int, timeS: Int) {
        val myTextView = findViewById<TextView>(R.id.timer_text)
        myTextView.text = timeM.toString() + ":" + timeS.toString()
    }

    private fun setTimer(timeS: Int) {
        val myTextView = findViewById<TextView>(R.id.timer_text)
        //TODO перевести секунды в формат времени
        //myTextView.text =

    }

    /*fun pressAwards(view: View) {
        val myBtn= findViewById<ImageButton>(R.id.win_awards_btn)
        animateButtons(myBtn)
    }

    fun pressWinPlay(view:View) {
        val myBtn= findViewById<ImageButton>(R.id.win_play_btn)
        animateButtons(myBtn)
        burstAndPlay()
    }
    fun pressWinReplay(view:View) {
        val myBtn= findViewById<ImageButton>(R.id.win_replay_btn)
        animateButtons(myBtn)
        burstAndPlay()
    }*/

    private fun animateButtons(myBtn: ImageButton) {

        val set = AnimatorSet()

        set.playSequentially(
            ObjectAnimator.ofFloat(myBtn, "translationY", 2000F + 10F),
            ObjectAnimator.ofFloat(myBtn, "translationY", 2000F - 10F)
        )
        set.duration = 150

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                gameSounds.playSoundButton()
            }

            override fun onAnimationEnd(animator: Animator) {
                winScreen(false)
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        set.start()
    }

}