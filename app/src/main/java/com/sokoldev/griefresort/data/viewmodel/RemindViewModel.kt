package com.sokoldev.griefresort.data.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sokoldev.griefresort.data.models.RemindMe
import com.sokoldev.griefresort.notifications.ReminderWorker
import com.sokoldev.griefresort.utils.Global
import java.util.concurrent.TimeUnit


class RemindViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val db = FirebaseFirestore.getInstance()

    private val _reminders = MutableLiveData<List<RemindMe>>()
    val reminders: LiveData<List<RemindMe>> get() = _reminders

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> get() = _success


    // Fetch all reminders for a user
    fun getReminders(userId: String) {
        db.collection("users").document(userId).collection("reminders")
            .get()
            .addOnSuccessListener { documents ->
                val reminderList = documents.mapNotNull { it.toObject(RemindMe::class.java) }
                _reminders.postValue(reminderList)
            }
            .addOnFailureListener { e ->
                Log.e("ReminderViewModel", "Error fetching reminders: ${e.message}")
                _error.postValue("Error fetching reminders")
            }
    }

    // Add a new reminder
    fun addReminder(userId: String, reminder: RemindMe) {
        val reminderRef = db.collection("users").document(userId).collection("reminders").document()
        reminder.id = reminderRef.id

        reminderRef.set(reminder)
            .addOnSuccessListener {
                _success.postValue("Reminder added successfully")
                scheduleNotification(reminder)
                Log.d("ReminderViewModel", "Reminder added successfully")
            }
            .addOnFailureListener { e ->
                Log.e("ReminderViewModel", "Error adding reminder: ${e.message}")
                _error.postValue("Error adding reminder")
            }
    }

    private fun scheduleNotification(reminder: RemindMe) {
        val date = Global.convertDateToLong(reminder.date)
        val notificationTime = date - 2 * 24 * 60 * 60 * 1000 // 2 days before

        val data = Data.Builder()
            .putString("id", reminder.id)
            .putString("title", reminder.title)
            .build()

        val delay = notificationTime - System.currentTimeMillis()
        if (delay > 0) {
            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInputData(data)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }

    // Update an existing reminder
    fun updateReminder(userId: String, reminder: RemindMe) {
        reminder.id.let { id ->
            db.collection("users").document(userId).collection("reminders").document(id)
                .set(reminder)
                .addOnSuccessListener {
                    _success.postValue("Reminder updated successfully")
                    Log.d("ReminderViewModel", "Reminder updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("ReminderViewModel", "Error updating reminder: ${e.message}")
                    _error.postValue("Error updating reminder")
                }
        }
    }
}
