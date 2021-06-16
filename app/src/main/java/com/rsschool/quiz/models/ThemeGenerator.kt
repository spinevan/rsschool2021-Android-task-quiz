package com.rsschool.quiz.models

import com.rsschool.quiz.R
import com.rsschool.quiz.interfaces.IThemeGenerator

class ThemeGenerator: IThemeGenerator {
    override fun getThemeById(id: Int): Int {

        val arrayThemes = arrayListOf(
            R.style.Theme_Quiz_First,
            R.style.Theme_Quiz_Third,
            R.style.Theme_Quiz_Fourth,
            R.style.Theme_Quiz_Fifth,
            R.style.Theme_Quiz_Second
        )

        val maxIndex = arrayThemes.size - 1

        if ( id > maxIndex ) {
            return arrayThemes[ (0..maxIndex).random() ]
        }

        return arrayThemes[id]


    }
}