package com.rsschool.quiz

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ThemeUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rsschool.quiz.fragments.MyQizFragment
import com.rsschool.quiz.fragments.ResultsFragment
import com.rsschool.quiz.interfaces.mainActivityInterface
import com.rsschool.quiz.models.Quiz
import com.rsschool.quiz.repositoryes.QuizRepository

class MainActivity : AppCompatActivity(), mainActivityInterface {



    private lateinit var quizRepository: QuizRepository
    private var usersAnswers: MutableMap<Int, String> = mutableMapOf()
    private var currenPage: Int = 0
    private var totalPages: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizRepository = QuizRepository(resources)
        totalPages = quizRepository.quiz.questions.size

        openQuizFragment()

    }

    override fun openQuizFragment() {

        val firstFragment: Fragment = MyQizFragment.newInstance(
            currenPage,
            totalPages,
            quizRepository.quiz.questions[currenPage].question,
            quizRepository.quiz.questions[currenPage].answers,
            usersAnswers.getOrElse(currenPage) { "" }
        )

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, firstFragment).commit()
    }



    override fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }

    override fun nextQuizFragment() {

        if ( currenPage == totalPages - 1  ) {

          val resString = quizRepository.getResults( usersAnswers )

          val resultsFragment: Fragment = ResultsFragment.newInstance( resString )
          val transaction = supportFragmentManager.beginTransaction()
          transaction.replace(R.id.container, resultsFragment).commit()
        } else {
            currenPage++
            openQuizFragment()
        }

    }

    override fun previousQuizFragment() {
        currenPage--
        openQuizFragment()
    }

    override fun saveUserAnswer(pageIndex: Int, answerIndex: Int) {

        usersAnswers[pageIndex] = answerIndex.toString()
        println(usersAnswers) //TODO
    }

    override fun closeApp() {
        finish()
    }

    override fun repeatQuiz() {
        currenPage = 0
        usersAnswers = mutableMapOf()
        openQuizFragment()
    }
}