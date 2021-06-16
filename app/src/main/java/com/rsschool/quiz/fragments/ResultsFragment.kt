package com.rsschool.quiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentMyQizBinding
import com.rsschool.quiz.databinding.FragmentResultsBinding
import com.rsschool.quiz.interfaces.mainActivityInterface


class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private var mainActivityListener: mainActivityInterface? = null

    private var resString: String? = ""

    companion object {

        @JvmStatic
        fun newInstance(resString: String) =
            ResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(RES_STRING_KEY, resString)
                }
            }

        private const val RES_STRING_KEY = "RES_STRING_KEY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resString = it.getString(RES_STRING_KEY)
        }
        mainActivityListener = context as mainActivityInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultText.text = "${resources.getString(R.string.your_results)} $resString"

        binding.quitBtn.setOnClickListener {
            mainActivityListener?.closeApp()
        }

        binding.repeatBtn.setOnClickListener {
            mainActivityListener?.repeatQuiz()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}