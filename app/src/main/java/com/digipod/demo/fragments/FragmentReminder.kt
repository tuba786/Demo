package com.digipod.demo.fragments


import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.R
import com.digipod.demo.ReminderReceiver
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FragmentReminder : Fragment() {

    private lateinit var reminderList: ArrayList<Reminder>
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reminderList = ArrayList()
        adapter = ReminderAdapter(reminderList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_reminder, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val fab: FloatingActionButton = rootView.findViewById(R.id.addReminderFab)
        fab.setOnClickListener {
            showAddReminderDialog()
        }

        createNotificationChannel()

        return rootView
    }

    private fun showAddReminderDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_reminder_dialog, null)
        val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle(getString(R.string.add_reminder))
        val alertDialog = alertDialogBuilder.show()

        val cancelButton: Button = dialogView.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        val saveButton: Button = dialogView.findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val nameEditText: EditText = dialogView.findViewById(R.id.nameEditText)
            val doseEditText: EditText = dialogView.findViewById(R.id.doseEditText)
            val timePicker: TimePicker = dialogView.findViewById(R.id.timePicker)

            val name = nameEditText.text.toString()
            val dose = doseEditText.text.toString()
            val hour = timePicker.hour
            val minute = timePicker.minute

            val reminder = Reminder(name, dose, hour, minute)
            reminderList.add(reminder)
            adapter.notifyItemInserted(reminderList.size - 1)

            setAlarm(reminder)

            alertDialog.dismiss()
        }
    }

    private fun setAlarm(reminder: Reminder) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), ReminderReceiver::class.java)
        intent.putExtra("name", reminder.name)
        intent.putExtra("dose", reminder.dose)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            reminderList.indexOf(reminder),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, reminder.hour)
            set(Calendar.MINUTE, reminder.minute)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    inner class ReminderAdapter(private val reminders: ArrayList<Reminder>) :
        RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
            val doseTextView: TextView = itemView.findViewById(R.id.doseTextView)
            val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
            val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val reminder = reminders[position]
            holder.nameTextView.text = reminder.name
            holder.doseTextView.text = reminder.dose
            holder.timeTextView.text =
                SimpleDateFormat("hh:mm a", Locale.getDefault()).format(reminder.time)

            holder.deleteButton.setOnClickListener {
                val reminderToDelete = reminders[position]
                reminders.remove(reminderToDelete)
                adapter.notifyItemRemoved(position)

                cancelAlarm(reminderToDelete)
            }
        }

        override fun getItemCount(): Int {
            return reminders.size
        }
    }

    private fun cancelAlarm(reminder: Reminder) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            reminderList.indexOf(reminder),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                setShowBadge(false)
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

data class Reminder(
    val name: String,
    val dose: String,
    val hour: Int,
    val minute: Int
) {
    val time: Date
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.time
        }
}



