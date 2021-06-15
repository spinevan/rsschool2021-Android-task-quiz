package com.rsschool.quiz.interfaces

interface mainActivityInterface {

    fun openQuizFragment()
    fun setStatusBarColor(color: Int)
    fun nextQuizFragment()
    fun previousQuizFragment()
    fun saveUserAnswer(pageIndex: Int, answerIndex: Int)

}