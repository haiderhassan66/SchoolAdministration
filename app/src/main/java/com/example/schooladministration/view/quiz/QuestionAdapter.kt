package com.example.schooladministration.view.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schooladministration.R
import com.example.schooladministration.databinding.QuestionItemBinding
import com.example.schooladministration.model.QuizModel
import com.example.schooladministration.utils.Singleton

class QuestionAdapter(val onClick:(quiz:QuizModel)->Unit):
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    private val list:MutableList<QuizModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_item,parent,false))
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = list[position]

        holder.binding.questionTv.text = item.question
        if (Singleton.type=="student"){
            holder.binding.cross.visibility = View.GONE
        }

        holder.binding.cross.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<QuizModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = QuestionItemBinding.bind(itemView)
    }
}