package ru.mamsikgames.mathballoons

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ROUNDS = 10        //сколько раундов играем
const val TASKTIME = 15         //время одного задания в секундах
const val ROUNDTIME = ROUNDS * TASKTIME //общее время раунда в секундах
const val QUANNUMBERS = 10      //количество загаданных шариков на задание
const val INTERVALMS = 1000L    //время между появлением шариков, миллисекунды
const val NROWS = 3             //количество рядов шариков на экране
const val NCOLS = 4             //количество шариков в одном ряду на экране !=0
const val FRAME_DURATION = 30L  //длительность кадра внимации взрыва, миллисекунды
const val FLY_DURATION = 15000L //время полета шара через весь экран, миллисекунды
const val BALLONSIZE = 1200     //размеры картинки шарика (и взрыва)
const val BUTTONWIDTH = 250     //ширина активной области шарика
const val BUTTONHEIGHT = 300    //высота активной области шарика
const val FONTSIZE = 80.0f      //надпись c цифрой на шарике

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
private var maxY = 0F                        //них экрана

private var correctCounter = 0  //счетчик правильных ответов
private var wrongCounter = 0    //неправильных
private var iLop = 0            //сколько лопнуто за одно задание

private lateinit var ll_game: ConstraintLayout
private lateinit var gameStrategy: GameStrategy
private lateinit var gameSounds: GameSounds

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        musicIntent = Intent(this, BackgroundMusic::class.java)
        startService(musicIntent)

        ll_game = findViewById(R.id.ll_game)
        gameStrategy = GameStrategy()
        gameSounds = GameSounds(this)

        //hideLoad()
        prepareCoords()
        //clock()
        makeBalloonsAnimated()
        //test()
        play()
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

    private fun prepareCoords() {
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val offsetscreen = width / 10 / (NCOLS - 1)

        val dx = (width - offsetscreen * 2 - BUTTONWIDTH) / (NCOLS - 1)
        val offset = offsetscreen - (BALLONSIZE - BUTTONWIDTH) / 2

        for (i in 0..<NCOLS) {
            pointsX.add(i * dx + offset)
        }
        maxY = dm.heightPixels.toFloat() - 500F
    }

    private fun makeBalloonsAnimated() {

        val handler = Handler(Looper.getMainLooper())

        for (i in (1..NROWS)) {

            val shuffledPosX = mutableListOf<Int>()
            shuffledPosX.addAll(pointsX)
            shuffledPosX.shuffle()

            for (j in pointsX.indices) {
                handler.postDelayed({
                    makeBalloon(shuffledPosX[j].toFloat(), maxY)
                }, ((i - 1) * 2 * INTERVALMS * pointsX.size + j * 2 * INTERVALMS))
            }
        }
    }

    private fun remakeBalloon(posX:Float) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            makeBalloon(posX, maxY)
        }, INTERVALMS)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun makeBalloon(posX: Float, posY: Float) {
        val context = this
        lifecycleScope.launch(Dispatchers.Main) {
            // получить из контекста
            val balloonAnimated =
                withContext(Dispatchers.Default) {
                    BalloonAnimated(
                        context,
                        gameStrategy.getRandom()
                    )
                }
            // Возвращаемся в главный поток

            balloonAnimated.x = posX
            balloonAnimated.y = posY

            balloonsList.add(balloonAnimated)
            balloonAnimated.setLayoutParams(ConstraintLayout.LayoutParams(BALLONSIZE, BALLONSIZE))
            ll_game.addView(balloonAnimated)
            balloonAnimated.moveBalloon(maxY)

            balloonAnimated.setOnTouchListener { _, event ->
                var isProcessed = false
                if (event.action == MotionEvent.ACTION_DOWN) {
                    if (!balloonAnimated.isBursting &&
                        event.x > ((BALLONSIZE - BUTTONWIDTH) / 2) && event.x < ((BALLONSIZE + BUTTONWIDTH) / 2) &&
                        event.y > ((BALLONSIZE - BUTTONHEIGHT) / 2) && event.y < ((BALLONSIZE + BUTTONHEIGHT) / 2)
                    ) {
                        balloonAnimated.burstBalloon()
                        if (balloonAnimated.balloonNum == gameStrategy.iNum) {
                            gameSounds.playSoundBurst()
                            gameSounds.playSoundCor()
                            setCorrect(++correctCounter)
                        }
                        else {
                            gameSounds.playSoundPuk()
                            setWrong(++wrongCounter)
                        }
                        val handlerRemove = Handler(Looper.getMainLooper())
                        handlerRemove.postDelayed({
                            ll_game.removeView(balloonAnimated)
                            remakeBalloon(balloonAnimated.x)
                        }, balloonAnimated.frameCount * FRAME_DURATION)
                        isProcessed = true
                    }
                }
                isProcessed
            }
        }
    }

    private fun test() {

        val rounds = ROUNDTIME / TASKTIME - 1

        val handlerSay = Handler(Looper.getMainLooper())

        for (r in (0..rounds)) {           //обновляем задачу "Лопни все шарики..." в заданный интервал
            handlerSay.postDelayed({
                gameStrategy.newTask()
                setDop(gameStrategy.iNum)
                sayLopni()
            }, (r * TASKTIME * 1000).toLong())
        }
    }

    private fun play() {


        val handlerSay = Handler(Looper.getMainLooper())

        for (r in (0..ROUNDS)) {           //обновляем задачу "Лопни все шарики..." в заданный интервал
            handlerSay.postDelayed({
                gameStrategy.newTask()
                setDop(gameStrategy.iNum)
                sayLopni()
            }, (r * TASKTIME * 1000).toLong())
        }

        handlerSay.postDelayed({ //окончание раунда
            winScreen(true)
        }, ((ROUNDS + 1) * TASKTIME * 1000).toLong())

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

    private fun burstNum(defNum: Int) {
        val handler = Handler(Looper.getMainLooper())

        var i = 0
        for (ball in balloonsList) {
            if (ball.balloonNum == defNum) {
                handler.postDelayed({
                    ball.burstBalloon()///
                }, (i * 100).toLong())
                i++
            }
        }
    }

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
        val text = "$timeM:$timeS"
        myTextView.text = text
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