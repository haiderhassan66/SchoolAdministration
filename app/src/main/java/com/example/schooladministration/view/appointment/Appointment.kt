package com.example.schooladministration.view.appointment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentAppointmentBinding
import com.example.schooladministration.model.SignUpModel
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.utils.Util
import com.example.schooladministration.viewmodel.AppointmentNav
import com.example.schooladministration.viewmodel.AppointmentViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class Appointment : Fragment() {

    private var _binding: FragmentAppointmentBinding? = null
    val binding get() = _binding!!
    val viewModel: AppointmentViewModel by viewModels()
    private val args: AppointmentArgs by navArgs()
    private val calendar = Calendar.getInstance()
    private lateinit var user: SignUpModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        user = Gson().fromJson(args.user, SignUpModel::class.java)

        viewModel.nameObservable.set(user.name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    private fun observer() {
        viewModel.navigate.observe(viewLifecycleOwner) {
            when (it) {
                AppointmentNav.CONFIRM -> {
                    val timeStamp = convertDateAndTime(
                        viewModel.dateObservable.get(),
                        viewModel.timeObservable.get()
                    )
                    timeStamp?.let {
                        Util.show_loader(requireContext())
                        viewModel.saveAppointment(
                            Singleton.user?.name!!,
                            Singleton.user?.userId!!,
                            user.name,
                            user.userId,
                            timeStamp
                        )

                    }
                }

                AppointmentNav.DATE -> {
                    showDatePickerDialog()
                }

                AppointmentNav.TIME -> {
                    showTimePickerDialog()
                }
            }
        }

        viewModel.response.observe(viewLifecycleOwner){
            Util.hide_loader()
            if (it.status){
                findNavController().navigate(R.id.action_appointment_to_homeScreen)
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertDateAndTime(date: String?, time: String?): Long? {
        val dateTime = "$date $time"

        try {
            val dateFormat = SimpleDateFormat("dd / MM / yyyy hh:mm a", Locale.getDefault())
            val parsedDate: Date = dateFormat.parse(dateTime)

            Log.d("checking", parsedDate.time.toString())

            val time = timestampToDateTimeString(parsedDate.time)

            Log.d("checking", time)

            // If parsing is successful, convert the Date to a timestamp in milliseconds
            return parsedDate.time
        } catch (e: Exception) {
            Log.d("checking", "error")
            // Handle parsing errors here
            e.printStackTrace()
            return null
        }
    }

    fun timestampToDateTimeString(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date)
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { view: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(year, month, dayOfMonth)
                updateDateTextView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = System.currentTimeMillis() // Optional: Set max date
        datePicker.show()
    }

    private fun updateDateTextView() {
        val dateFormat = SimpleDateFormat("dd / MM / yyyy", Locale.getDefault())
        viewModel.dateObservable.set(dateFormat.format(calendar.time))
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateTimeTextView()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // 24-hour format (change to true for 24-hour format)
        )
        timePicker.show()
    }

    private fun updateTimeTextView() {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        viewModel.timeObservable.set(timeFormat.format(calendar.time))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}