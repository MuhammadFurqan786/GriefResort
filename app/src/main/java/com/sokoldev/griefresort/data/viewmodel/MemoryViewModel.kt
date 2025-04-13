package com.sokoldev.griefresort.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sokoldev.griefresort.data.models.MemoryBox

class MemoryViewModel(application: Application) : AndroidViewModel(application) {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    private val _memoryBoxes: MutableLiveData<List<MemoryBox>> = MutableLiveData()
    val memoryBoxes: LiveData<List<MemoryBox>> get() = _memoryBoxes

    private val _response = MutableLiveData<Boolean>()
    val response: LiveData<Boolean> get() = _response


    private val _delete = MutableLiveData<Boolean>()
    val delete: LiveData<Boolean> get() = _delete


    // Fetch memory boxes from Firestore
    fun getMemoryBoxes(userId: String): LiveData<List<MemoryBox>> {
        firestore.collection("memory_box")
            .document(userId)
            .collection("media")
            .get()
            .addOnSuccessListener { result ->
                val boxes = mutableListOf<MemoryBox>()
                for (document in result) {
                    val fileName = document.id
                    val fileUrl = document.getString("fileUrl") ?: ""
                    val fileType = document.getString("fileType") ?: ""
                    boxes.add(MemoryBox(fileName, fileUrl, fileType))
                }
                _memoryBoxes.value = boxes
            }
        return _memoryBoxes
    }

    // Add a new memory box
    fun addMemoryBox(fileName: String, fileUrl: String, fileType: String, userId: String) {
        val mediaData = hashMapOf(
            "fileUrl" to fileUrl,
            "fileType" to fileType,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("memory_box")
            .document(userId)
            .collection("media")
            .document(fileName)
            .set(mediaData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _response.value = true
                } else {
                    _response.value = false
                }
            }
    }

    // Delete a memory box by position
    fun deleteMemoryBox(position: Int, userId: String) {
        val memoryBox = memoryBoxes.value?.get(position)
        memoryBox?.let {
            val fileName = it.fileName
            // Delete from Firestore
            firestore.collection("memory_box")
                .document(userId)
                .collection("media")
                .document(fileName) // Using the unique file name as the document ID
                .delete()
                .addOnSuccessListener {
                    // Successfully deleted from Firestore, now delete from Storage
                    val fileRef = storage.child("memory_box/$userId/$fileName")
                    fileRef.delete().addOnSuccessListener {
                        _delete.value = true
                        getMemoryBoxes(userId)
                    }.addOnFailureListener {
                        // Handle Storage delete failure
                        _delete.value = false
                    }
                }
                .addOnFailureListener {
                    // Handle Firestore delete failure
                    _delete.value = false
                }
        }
    }


}
