package com.sokoldev.griefresort.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.sokoldev.griefresort.data.models.User
import com.sokoldev.griefresort.preference.PreferenceHelper
import java.util.Date

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val preference = PreferenceHelper.getPref(application)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    // LiveData for registration and login status
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _isPremium = MutableLiveData<Boolean>()
    val isPremium: LiveData<Boolean> get() = _isPremium

    private val _remainingTrialDays = MutableLiveData<Int?>()
    val remainingTrialDays: LiveData<Int?> get() = _remainingTrialDays

    private val _isUserCreated = MutableLiveData<Boolean>()
    val isUserCreated: LiveData<Boolean> get() = _isUserCreated

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn


    // Register a new user
    fun registerUser(
        firstName: String,
        lastName: String,
        userName: String,
        email: String,
        password: String
    ) {
        _status.value = "Registering..."
        checkUserNameAvailability(userName) { isAvailable ->
            if (isAvailable) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                            saveUserData(uid, firstName, lastName, userName, email)
                        } else {
                            _status.value = "Registration failed: ${task.exception?.message}"
                            _isUserCreated.value = false
                        }
                    }
            } else {
                _status.value = "Username already taken."
                _isUserCreated.value = false
            }
        }
    }

    // Login an existing user
    fun loginUser(email: String, password: String) {
        _status.value = "Logging in..."
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                    fetchAndSaveUserData(uid)
                } else {
                    _status.value = "Login failed: ${task.exception?.message}"
                    _isLoggedIn.value = false
                }
            }
    }

    // Forgot password
    fun forgotPassword(email: String) {
        _status.value = "Sending password reset email..."
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = "Password reset email sent."
                } else {
                    _status.value = "Error: ${task.exception?.message}"
                }
            }
    }

    // Change password
    fun changePassword(newPassword: String) {
        val user = auth.currentUser
        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = "Password changed successfully."
                } else {
                    _status.value = "Error: ${task.exception?.message}"
                }
            }
    }

    // Check username availability
    fun checkUserNameAvailability(userName: String, callback: (Boolean) -> Unit) {
        db.collection("users").whereEqualTo("userName", userName).get()
            .addOnSuccessListener { documents ->
                callback(documents.isEmpty)
            }
            .addOnFailureListener { e ->
                _status.value = "Error checking username: ${e.message}"
                callback(false)
            }
    }

    // Save user data with trial info
    private fun saveUserData(
        uid: String,
        firstName: String,
        lastName: String,
        userName: String,
        email: String
    ) {
        val trialStartDate = Date().time
        val trialEndDate = trialStartDate + (7 * 24 * 60 * 60 * 1000) // 7 days trial
        FirebaseMessaging.getInstance().token.addOnSuccessListener { fcmToken ->
            val user = User(
                userId = uid,
                firstName = firstName,
                lastName = lastName,
                userName = userName,
                email = email,
                isPremium = false,
                trialStartDate = trialStartDate,
                trialEndDate = trialEndDate,
                fcmToken = fcmToken,
                reminders = emptyList()
            )
            preference.saveCurrentUser(user)

            db.collection("users").document(uid).set(user)
                .addOnSuccessListener {
                    _status.value = "Registration successful."
                    _isUserCreated.value = true
                }
                .addOnFailureListener { e ->
                    _status.value = "Error saving user data: ${e.message}"
                    _isUserCreated.value = false
                }
        }
    }

    // Check the user's premium status
    private fun checkPremiumStatus() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val isPremium = document.getBoolean("isPremium") ?: false
                val trialEndDate = document.getLong("trialEndDate") ?: 0L

                when {
                    isPremium -> {
                        // User is premium
                        _isPremium.value = true
                        _remainingTrialDays.value = null // No trial for premium users
                    }

                    isTrialActive(trialEndDate) -> {
                        // Trial is active
                        _isPremium.value = false
                        _remainingTrialDays.value = getRemainingTrialDays(trialEndDate)
                    }

                    else -> {
                        // Trial has ended
                        _isPremium.value = false
                        _remainingTrialDays.value = 0
                    }
                }
            }
            .addOnFailureListener { e ->
                _status.value = "Error checking premium status: ${e.message}"
            }
    }

    // Helper function to check if the trial is active
    private fun isTrialActive(trialEndDate: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return trialEndDate > currentTime
    }

    // Helper function to calculate remaining trial days
    private fun getRemainingTrialDays(trialEndDate: Long): Int {
        val currentTime = System.currentTimeMillis()
        val remainingTime = trialEndDate - currentTime
        return if (remainingTime > 0) {
            (remainingTime / (24 * 60 * 60 * 1000)).toInt() // Convert milliseconds to days
        } else {
            0 // Trial expired
        }
    }

    // Update user data (firstName, lastName, etc.)
    fun updateUser(
        updatedFirstName: String,
        updatedLastName: String,
        updatedUserName: String
    ) {
        _status.value = "Updating user data..."

        // Check if the new username is available
        checkUserNameAvailability(updatedUserName) { isAvailable ->
            if (isAvailable) {
                val user = auth.currentUser
                val uid = user?.uid ?: return@checkUserNameAvailability

                // Fetch the current user data
                db.collection("users").document(uid).get()
                    .addOnSuccessListener { document ->
                        val currentUser = document.toObject(User::class.java)
                        if (currentUser != null) {
                            // Update only the four fields
                            val updatedUser = currentUser.copy(
                                firstName = updatedFirstName,
                                lastName = updatedLastName,
                                userName = updatedUserName
                            )

                            // Update the user data in Firestore
                            db.collection("users").document(uid).set(updatedUser)
                                .addOnSuccessListener {
                                    _status.value = "User updated successfully."
                                    preference.saveCurrentUser(updatedUser) // Update the current user in shared preferences
                                }
                                .addOnFailureListener { e ->
                                    _status.value = "Error updating user: ${e.message}"
                                }
                        } else {
                            _status.value = "Error: Unable to fetch current user data."
                        }
                    }
                    .addOnFailureListener { e ->
                        _status.value = "Error fetching current user data: ${e.message}"
                    }
            } else {
                _status.value = "Username already taken."
            }
        }
    }


    private fun fetchAndSaveUserData(uid: String) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Map Firestore document to User object
                    val user = document.toObject(User::class.java)
                    if (user != null) {
                        // Save user to preferences
                        preference.saveCurrentUser(user)
                        preference.setUserLogin(true)
                        // Check premium status
                        checkPremiumStatus()

                        // Update status and logged-in state
                        _status.value = "Login successful."
                        _isLoggedIn.value = true
                    } else {
                        _status.value = "Error: Unable to fetch user data."
                        _isLoggedIn.value = false
                    }
                } else {
                    _status.value = "Error: User not found in database."
                    _isLoggedIn.value = false
                }
            }
            .addOnFailureListener { e ->
                _status.value = "Error fetching user data: ${e.message}"
                _isLoggedIn.value = false
            }
    }

    fun logout() {
        _status.value = "Logging out..."

        val user = auth.currentUser
        if (user != null) {
            // Sign out the user
            auth.signOut()

            preference.setUserLogin(false)
            // Clear the saved user data from preferences
            preference.clearSharedPref()

            // Update the status and logged-in state
            _status.value = "Logged out successfully."
            _isLoggedIn.value = false
        } else {
            _status.value = "No user is logged in."
        }
    }


}
