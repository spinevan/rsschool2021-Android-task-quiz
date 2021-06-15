package com.rsschool.quiz.interfaces

import com.rsschool.quiz.models.Quiz

interface IQuizRepository {

    var quiz: Quiz

    fun loadQuiz()

}