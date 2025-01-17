package com.rsschool.quiz.fragments

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.isVisible
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentMyQizBinding
import com.rsschool.quiz.interfaces.mainActivityInterface
import com.rsschool.quiz.models.ThemeGenerator


class MyQizFragment : Fragment() {

    private var mainActivityListener: mainActivityInterface? = null
    private var _binding: FragmentMyQizBinding? = null
    private val binding get() = _binding!!

    private var currenPage: Int? = 0
    private var totalPages: Int? = 0
    private var question:String? = ""
    private var answers: Array<String>? = null
    private var indexOfSelectedAnswer: String? = null

    companion object {
        @JvmStatic
        fun newInstance(currenPage: Int, totalPages: Int, question: String, answers: Array<String>, indexOfSelectedAnswer: String) =
            MyQizFragment().apply {
                arguments = Bundle().apply {
                    putInt(CURRENT_PAGE_KEY, currenPage)
                    putInt(TOTAL_PAGES_KEY, totalPages)
                    putString(QUESTION_KEY, question)
                    putStringArray(ANSWERS_ARRAY_KEY, answers)
                    putString(INDEX_OF_SELECTED_ANSWER_KEY, indexOfSelectedAnswer)
                }
            }

        private const val CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY"
        private const val TOTAL_PAGES_KEY = "TOTAL_PAGES_KEY"
        private const val QUESTION_KEY = "QUESTION_KEY"
        private const val ANSWERS_ARRAY_KEY = "ANSWERS_ARRAY_KEY"
        private const val INDEX_OF_SELECTED_ANSWER_KEY = "INDEX_OF_SELECTED_ANSWER_KEY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currenPage = it.getInt(CURRENT_PAGE_KEY)
            totalPages = it.getInt(TOTAL_PAGES_KEY)
            question = it.getString(QUESTION_KEY)
            answers = it.getSerializable(ANSWERS_ARRAY_KEY) as Array<String>
            indexOfSelectedAnswer = it.getString(INDEX_OF_SELECTED_ANSWER_KEY)
        }

        mainActivityListener = context as mainActivityInterface

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val contextThemeWrapper = ContextThemeWrapper(activity, ThemeGenerator().getThemeById(currenPage!!))
        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        val typedValue = TypedValue()
        if (contextThemeWrapper.theme.resolveAttribute(R.attr.colorPrimaryVariant, typedValue, true) ) {
            mainActivityListener?.setStatusBarColor(typedValue.data)
        }

        _binding = FragmentMyQizBinding.inflate(localInflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "Question - ${currenPage?.plus(1)}"
        binding.question.text = question
        tunePreviousButton()
        tuneNextButton()

        if ( answers != null ) {

            for (answer in answers as Array<String> ) {

                val radioButton = RadioButton( context )
                radioButton.setPadding(20, 20,20,20)
                radioButton.text = answer
                radioButton.id = answers!!.indexOf(answer)


                if ( indexOfSelectedAnswer != null && indexOfSelectedAnswer != "" ) {
                    if ( indexOfSelectedAnswer!!.toInt() == answers!!.indexOf(answer) ) {
                        radioButton.isChecked = true
                        binding.nextButton.isEnabled = true
                    }
                }

                radioButton.setOnClickListener {
                    println( answers!!.indexOf(answer) )
                    binding.nextButton.isEnabled = true

                    mainActivityListener?.saveUserAnswer(currenPage!!, answers!!.indexOf(answer))

                }

                binding.radioGroup.addView(radioButton, answers!!.indexOf(answer))

            }

        }

        binding.nextButton.setOnClickListener {
            mainActivityListener?.nextQuizFragment()
        }

        binding.previousButton.setOnClickListener {
            mainActivityListener?.previousQuizFragment()
        }

        binding.toolbar.setNavigationOnClickListener {
            mainActivityListener?.previousQuizFragment()
        }

    }

    private fun tunePreviousButton() {

        binding.previousButton.isEnabled = currenPage!! > 0

        if ( currenPage!! > 0 ) {
            binding.toolbar.setNavigationIcon( R.drawable.ic_baseline_chevron_left_24 )
        } else {
            binding.toolbar.navigationIcon = null
        }

    }

    private fun tuneNextButton() {

        if ( currenPage == totalPages!! - 1 ) {
            binding.nextButton.text = getString( R.string.submit )
        } else {
            binding.nextButton.text = getString( R.string.next )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}