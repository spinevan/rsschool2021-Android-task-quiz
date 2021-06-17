package com.rsschool.quiz.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentResultsBinding
import com.rsschool.quiz.interfaces.mainActivityInterface


class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private var mainActivityListener: mainActivityInterface? = null

    private var resString: String? = ""
    private var sharedText: String? = ""

    companion object {

        @JvmStatic
        fun newInstance(resString: String, sharedText: String) =
            ResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(RES_STRING_KEY, resString)
                    putString(SHARED_TEXT_KEY, sharedText)
                }
            }

        private const val RES_STRING_KEY = "RES_STRING_KEY"
        private const val SHARED_TEXT_KEY = "SHARED_TEXT_KEY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resString = it.getString(RES_STRING_KEY)
            sharedText = it.getString(SHARED_TEXT_KEY)
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
        binding.resultText.text = "${getString(R.string.your_result)} $resString"

        binding.quitBtn.setOnClickListener {
            mainActivityListener?.closeApp()
        }

        binding.repeatBtn.setOnClickListener {
            mainActivityListener?.repeatQuiz()
        }

        binding.shareBtn.setOnClickListener {
            println( sharedText )
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Quiz results");
            context?.startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to) ))

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}