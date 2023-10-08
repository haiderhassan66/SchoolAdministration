package com.example.schooladministration.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import com.example.schooladministration.model.QuizModel

class Util {
    @SuppressLint("StaticFieldLeak")
    companion object{

        private lateinit var dialog:ProgressDialog

        fun quizList():List<QuizModel>{
            val list = mutableListOf<QuizModel>()

//            list.apply {
//                add(QuizModel
//                    ("Grand Central Terminal, Park Avenue, New York is the world's",
//                    "largest railway station",
//                    "highest railway station",
//                    "longest railway station",
//                    "None of the above",
//                    "largest railway station"))
//                add(QuizModel(
//                    "Entomology is the science that studies",
//                    "Behavior of human beings",
//                    "Insects",
//                    "The origin and history of technical and scientific terms",
//                    "The formation of rocks",
//                    "Insects"))
//                add(QuizModel(
//                    "Eritrea, which became the 182nd member of the UN in 1993, is in the continent of",
//                    "Asia","Africa","Europe","Australia","Africa"))
//                add(QuizModel(
//                    "For which of the following disciplines is Nobel Prize awarded?",
//                    "Physics and Chemistry",
//                    "Physiology or Medicine",
//                    "Literature, Peace and Economics","All of the above","All of the above"))
//                add(QuizModel(
//                    "Epsom (England) is the place associated with",
//                    "Horse racing",
//                    "Polo","Shooting","Snooker","Horse racing"))
//            }

            return list
        }

        fun show_loader(context: Context){
            dialog = ProgressDialog(context)
            dialog.setMessage("Loading...!")
            dialog.show()
        }

        fun hide_loader(){
            dialog.dismiss()
        }
    }
}