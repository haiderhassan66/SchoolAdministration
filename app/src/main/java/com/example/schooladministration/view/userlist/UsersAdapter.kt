package com.example.schooladministration.view.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.schooladministration.R
import com.example.schooladministration.databinding.UserItemBinding
import com.example.schooladministration.model.SignUpModel
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.viewmodel.UsersAdapterViewModel

class UsersAdapter(private val onClick:(SignUpModel)->Unit):RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val list:MutableList<SignUpModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val holderUserBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.user_item, parent, false
        )
        return UsersViewHolder(holderUserBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getItem(position: Int): SignUpModel {
        return list[position]
    }

    fun updateList(list: List<SignUpModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class UsersViewHolder(
        private val dataBinding: ViewDataBinding
    ):RecyclerView.ViewHolder(dataBinding.root){
        fun onBind(user:SignUpModel){
            val holderUserBinding = dataBinding as UserItemBinding
            val buyViewModel = UsersAdapterViewModel(user)

            if (Singleton.type=="admin"){
                holderUserBinding.appointmentTV.visibility = View.GONE
            }

            holderUserBinding.viewModel = buyViewModel

            holderUserBinding.appointmentTV.setOnClickListener {
                onClick(user)
            }
        }
    }
}