package com.example.schooladministration.view.quiz

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.schooladministration.R
import com.example.schooladministration.databinding.QuizItemBinding
import com.example.schooladministration.model.QuizModel

class QuizPagerAdapter(
    private val list: List<QuizModel>,
    private val onClick:(position:Int,result:Int)->Unit):RecyclerView.Adapter<QuizPagerAdapter.QuizViewHolder>() {

    var result = 0

    class QuizViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = QuizItemBinding.bind(itemView)
    }

    private fun nextClick(binding: QuizItemBinding,position: Int) {
        binding.signinBtn.setOnClickListener {
            onClick(position,result)
            Log.d("checking",result.toString())
        }
    }

    private fun setValues(binding: QuizItemBinding, position: Int) {
        val item = list[position]
        binding.questionTV.text = item.question
        binding.option1TV.text = item.option1
        binding.option2TV.text = item.option2
        binding.option3TV.text = item.option3
        binding.option4TV.text = item.option4
    }


    private fun optionClick(binding: QuizItemBinding, position: Int) {
        binding.option1TV.setOnClickListener {
            binding.signinBtn.isEnabled = true
            allClickFalse(binding)
            if (list[position].option1==list[position].result){
                setBackground(binding.option1TV,true)
                result+=10
            } else {
                setBackground(binding.option1TV,false)
                wrong(binding, position)
            }
        }
        binding.option2TV.setOnClickListener {
            binding.signinBtn.isEnabled = true
            allClickFalse(binding)
            if (list[position].option2==list[position].result){
                setBackground(binding.option2TV,true)
                result+=10
            } else {
                setBackground(binding.option2TV,false)
                wrong(binding, position)
            }
        }
        binding.option3TV.setOnClickListener {
            binding.signinBtn.isEnabled = true
            allClickFalse(binding)
            if (list[position].option3==list[position].result){
                setBackground(binding.option3TV,true)
                result+=10
            } else {
                setBackground(binding.option3TV,false)
                wrong(binding, position)
            }
        }
        binding.option4TV.setOnClickListener {
            binding.signinBtn.isEnabled = true
            allClickFalse(binding)
            if (list[position].option4==list[position].result){
                setBackground(binding.option4TV,true)
                result+=10
            } else {
                setBackground(binding.option4TV,false)
                wrong(binding, position)
            }
        }
    }

    private fun allClickFalse(binding: QuizItemBinding) {
        binding.option1TV.isEnabled = false
        binding.option2TV.isEnabled = false
        binding.option3TV.isEnabled = false
        binding.option4TV.isEnabled = false
    }

    private fun wrong(binding: QuizItemBinding, position: Int) {
        if (binding.option1TV.text == list[position].result){
            setBackground(binding.option1TV,true)
        } else if (binding.option2TV.text == list[position].result){
            setBackground(binding.option2TV,true)
        } else if (binding.option3TV.text == list[position].result){
            setBackground(binding.option3TV,true)
        } else if (binding.option4TV.text == list[position].result){
            setBackground(binding.option4TV,true)
        }
    }


    private fun setBackground(textView: TextView, isTrue: Boolean) {
        if (isTrue)
            textView.setBackgroundResource(R.drawable.button_background)
        else
            textView.setBackgroundResource(R.drawable.button_background_red_stroke)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
//        if (position<5) {
            setValues(holder.binding, position)

            optionClick(holder.binding, position)

            nextClick(holder.binding, position)
//        }
//        else {
//            holder.binding.imageView.visibility = View.VISIBLE
//            holder.binding.textView3.visibility = View.VISIBLE
//            holder.binding.textView3.text = "Your Score: $result/50"
//            holder.binding.option1TV.visibility = View.GONE
//            holder.binding.option2TV.visibility = View.GONE
//            holder.binding.option3TV.visibility = View.GONE
//            holder.binding.option4TV.visibility = View.GONE
//            holder.binding.questionTV.visibility = View.GONE
//            holder.binding.signinBtn.visibility = View.GONE
//        }
    }
}