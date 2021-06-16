package com.rsschool.quiz.repositoryes

import android.content.res.Resources
import com.rsschool.quiz.R
import com.rsschool.quiz.interfaces.IQuizRepository
import com.rsschool.quiz.models.Quiz
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class QuizRepository(val resources: Resources) : IQuizRepository {

    override lateinit var quiz: Quiz

    init {
        loadQuiz()
    }

    override fun loadQuiz() {

        val jsonString = loadFromJson()
        quiz = Json.decodeFromString<Quiz>(jsonString)

    }

    override fun getResults(answers: MutableMap<Int, String>): String {

        var trueAnswersCount = 0

        for ( item in answers ) {
            if ( quiz.questions[item.key].correctAnswer == item.value.toInt() )
                trueAnswersCount++
        }

        return "$trueAnswersCount / ${quiz.questions.size}"

    }

    private fun loadFromJson(): String {
        val text = resources.openRawResource(R.raw.quiz1)
            .bufferedReader().use { it.readText() }

        return text

    }


}