package com.rsschool.quiz.models

import kotlinx.serialization.Serializable

@Serializable
data class Answer(val title: String, val isTrue: Boolean)
