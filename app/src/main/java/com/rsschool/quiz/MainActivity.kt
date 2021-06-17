package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.fragments.MyQizFragment
import com.rsschool.quiz.fragments.ResultsFragment
import com.rsschool.quiz.interfaces.mainActivityInterface
import com.rsschool.quiz.repositoryes.QuizRepository

class MainActivity : AppCompatActivity(), mainActivityInterface {



    private lateinit var quizRepository: QuizRepository
    private var usersAnswers: MutableMap<Int, String> = mutableMapOf()
    private var currenPage: Int = 0
    private var totalPages: Int = 0
    private var isTestEnded: Boolean = false

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

    override fun onBackPressed() {

        if ( isTestEnded || currenPage == 0 ) {
            super.onBackPressed()
        } else {
            previousQuizFragment()
        }

    }


    override fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }

    override fun nextQuizFragment() {

        if ( currenPage == totalPages - 1  ) {

           isTestEnded = true

          val resString = quizRepository.getResults( usersAnswers )
          val sharedText = quizRepository.getSharedText( usersAnswers )

          val resultsFragment: Fragment = ResultsFragment.newInstance( resString, sharedText )
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