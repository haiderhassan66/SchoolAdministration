package com.example.schooladministration.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.schooladministration.model.AppointmentModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppointmentAdapterViewModel(val data:AppointmentModel):ViewModel() {
    var nameObservable = ObservableField(data.studentName + "'s appointment with "+data.consultantName)
    var timeObservable = ObservableField<String>()

    fun timestampToDateTimeString(timestamp: Long) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        val date = Date(timestamp)
        timeObservable.set(dateFormat.format(date))
    }

}