package com.example.schooladministration.view.homescreen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.schooladministration.R
import com.example.schooladministration.databinding.AppointmentItemBinding
import com.example.schooladministration.databinding.UserItemBinding
import com.example.schooladministration.model.SignUpModel
import com.example.schooladministration.model.AppointmentModel
import com.example.schooladministration.viewmodel.AppointmentAdapterViewModel

class HomeScreenAdapter(private val onClick:(AppointmentModel,Int)->Unit): RecyclerView.Adapter<HomeScreenAdapter.AppointmentViewHolder>() {

    private val list:MutableList<AppointmentModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val holderUserBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.appointment_item, parent, false
        )
        return AppointmentViewHolder(holderUserBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getItem(position: Int): AppointmentModel {
        return list[position]
    }

    fun updateList(list: List<AppointmentModel>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        (holder as AppointmentViewHolder).onBind(getItem(position))
    }

    inner class AppointmentViewHolder(
        private val dataBinding: ViewDataBinding
    ): RecyclerView.ViewHolder(dataBinding.root){
        fun onBind(appointmentModel: AppointmentModel){
            val holderUserBinding = dataBinding as AppointmentItemBinding
            val buyViewModel = AppointmentAdapterViewModel(appointmentModel)

            buyViewModel.timestampToDateTimeString(appointmentModel.timeStamp)

            holderUserBinding.viewModel = buyViewModel

            holderUserBinding.cancelTV.setOnClickListener {
                onClick(appointmentModel,adapterPosition)
            }
        }
    }
}