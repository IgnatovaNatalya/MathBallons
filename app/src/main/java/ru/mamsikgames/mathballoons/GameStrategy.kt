package ru.mamsikgames.mathballoons

class GameStrategy() {

    var iNum=0
    var generatedNumbers=0  //количество сгенерированных шариков с загаданным числом
    var first=true

    private var P: Float =
        QUANNUMBERS.toFloat() * INTERVALMS.toFloat() / (TASKTIME.toFloat() * 1000F) //вероятность появления шарика с загаданным числом (удвлетворяет условию количества загаданных шаров в задании)


    fun newTask() {
        iNum=(0..9).random()
        generatedNumbers = 0
    }

    fun newRound(){
        first=true
        newTask()
    }

    fun getRandom(): Int {    //генерируем случайное число с вероятностью, определяемой количеством нужных шаров, которые должны появиться в течение задания

        val myList = mutableListOf<Int>()
        myList.addAll(0..9)
        myList.remove(iNum)

        val ordinaryRandom = myList.random()
        var res = ordinaryRandom

        if (generatedNumbers <= QUANNUMBERS) {
            val p: Float = 0.01F * (0..100).random()
            if (p < P) {
                generatedNumbers++
                res = iNum
            }
        }
        return res
    }
}