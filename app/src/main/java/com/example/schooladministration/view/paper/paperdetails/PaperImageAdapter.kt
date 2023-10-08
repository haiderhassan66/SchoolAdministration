package com.example.schooladministration.view.paper.paperdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.schooladministration.R
import com.example.schooladministration.databinding.PaperItemBinding
import com.example.schooladministration.model.PaperImageModel
import com.example.schooladministration.utils.Singleton

class PaperImageAdapter(val onClick:(paper:PaperImageModel)->Unit):RecyclerView.Adapter<PaperImageAdapter.PaperViewHolder>() {

    private val list:MutableList<PaperImageModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaperViewHolder {
        return PaperViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paper_item,parent,false))
    }

    override fun onBindViewHolder(holder: PaperViewHolder, position: Int) {
        val item = list[position]

        if (Singleton.type=="student"){
            holder.binding.crossIv.visibility = View.GONE
        }

        Glide.with(holder.itemView).load(item.paperImg).into(holder.binding.paperIv)

        holder.binding.crossIv.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<PaperImageModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class PaperViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = PaperItemBinding.bind(itemView)
    }
}