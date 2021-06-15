package com.rsschool.quiz.models

import kotlinx.serialization.Serializable

@Serializable
data class Question(val question: String, val answers: Array<Answer>)
