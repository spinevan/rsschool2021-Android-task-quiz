package com.rsschool.quiz.models

import kotlinx.serialization.Serializable

@Serializable
data class Quiz(val questions: Array<Question>)
