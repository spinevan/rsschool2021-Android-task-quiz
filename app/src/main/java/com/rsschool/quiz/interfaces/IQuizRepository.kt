package com.rsschool.quiz.interfaces

import com.rsschool.quiz.models.Quiz

interface IQuizRepository {

    var quiz: Quiz

    fun loadQuiz()

    fun getResults(answers: MutableMap<Int, String>): String

    fun getSharedText(answers: MutableMap<Int, String>): String

}