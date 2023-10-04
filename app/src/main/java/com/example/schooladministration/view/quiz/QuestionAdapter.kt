package com.example.schooladministration.view.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schooladministration.R
import com.example.schooladministration.databinding.PaperItemBinding
import com.example.schooladministration.databinding.QuestionItemBinding
import com.example.schooladministration.model.QuizModel
import com.example.schooladministration.view.paper.PaperAdapter

class QuestionAdapter(private val list:List<QuizModel>,val onClick:(position:Int)->Unit):
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_item,parent,false))
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = list[position]

        holder.binding.questionTv.text = item.question

        holder.binding.cross.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = QuestionItemBinding.bind(itemView)
    }
}