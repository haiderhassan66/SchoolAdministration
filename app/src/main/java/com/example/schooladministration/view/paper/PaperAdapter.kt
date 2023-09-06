package com.example.schooladministration.view.paper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schooladministration.R
import com.example.schooladministration.databinding.PaperItemBinding
import java.util.zip.Inflater

class PaperAdapter(private val list:List<String>,val onClick:(position:Int)->Unit):RecyclerView.Adapter<PaperAdapter.PaperViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaperViewHolder {
        return PaperViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paper_item,parent,false))
    }

    override fun onBindViewHolder(holder: PaperViewHolder, position: Int) {
        val item = list[position]

        holder.binding.paperTv.text = item

        holder.binding.paperTv.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PaperViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = PaperItemBinding.bind(itemView)
    }
}