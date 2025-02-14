package com.sokoldev.griefresort.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sokoldev.griefresort.data.models.Comment
import com.sokoldev.griefresort.data.models.GroupHug

class GroupHugViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _groupHugs = MutableLiveData<List<GroupHug>>()
    val groupHugs: LiveData<List<GroupHug>> get() = _groupHugs

    private val _groupHug = MutableLiveData<GroupHug>()
    val groupHug: LiveData<GroupHug> get() = _groupHug

    private val _comments = MutableLiveData<List<Comment>?>()
    val comments: LiveData<List<Comment>?> get() = _comments

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> get() = _success

    // Fetch all GroupHug posts
    fun getAllGroupHugs() {
        db.collection("groupHugs").get()
            .addOnSuccessListener { documents ->
                val groupHugList = documents.mapNotNull { it.toObject(GroupHug::class.java) }
              /*  groupHugList.forEach { groupHug ->
                    groupHug.id?.let {
                        getComments(it) { comments ->
                            groupHug.comments = comments
                        }
                    }
                }*/
                _groupHugs.postValue(groupHugList)
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error fetching GroupHugs: ${e.message}")
                _error.postValue("Error fetching GroupHugs")
            }
    }

    // Add hug to a GroupHug post
    fun addHug(groupHugId: String) {
        val groupHugRef = db.collection("groupHugs").document(groupHugId)
        groupHugRef.get()
            .addOnSuccessListener { document ->
                val groupHug = document.toObject(GroupHug::class.java)
                if (groupHug != null) {
                    groupHugRef.update(
                        "totalHugs", FieldValue.increment(1),
                    )
                        .addOnSuccessListener {
                            _success.postValue("Post hugged successfully")
                            getAllGroupHugs()
                            Log.d("GroupHug", "Post hugged successfully")
                        }
                        .addOnFailureListener { e ->
                            _error.postValue("Error hugging post")
                            Log.e("GroupHug", "Error hugging post: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error fetching GroupHug for hug: ${e.message}")
            }
    }

    // Add a comment to a GroupHug post with an ID
    fun addComment(groupHugId: String, newComment: Comment) {
        val groupHugRef = db.collection("groupHugs").document(groupHugId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(groupHugRef)
            val currentGroupHug = snapshot.toObject(GroupHug::class.java)

            // Generate a unique ID for the new comment
            val commentId = db.collection("groupHugs").document().id
            newComment.commentId = commentId // Set the unique comment ID

            // Update the comments list
            val updatedComments = currentGroupHug?.comments ?: ArrayList()
            updatedComments.add(newComment)

            // Update the GroupHug object
            currentGroupHug?.apply {
                comments = updatedComments
                totalComments = updatedComments.size
            }

            // Update Firestore with the new GroupHug object
            transaction.set(groupHugRef, currentGroupHug ?: GroupHug())
        }.addOnSuccessListener {
            getAllGroupHugs() // Refresh group hugs after adding a comment
            Log.d("Firestore", "Comment added successfully.")
            _success.postValue(" Comment added successfully.")
        }.addOnFailureListener { exception ->
            _error.postValue("Error adding comment: ${exception.message}")
        }
    }

    fun removeComment(groupHugId: String, commentId: String) {
        val groupHugRef = db.collection("groupHugs").document(groupHugId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(groupHugRef)
            val currentGroupHug = snapshot.toObject(GroupHug::class.java)

            // Ensure the GroupHug object exists
            currentGroupHug?.let {
                // Filter out the comment with the given commentId
                val updatedComments =
                    it.comments?.filter { comment -> comment.commentId != commentId }

                // Update the GroupHug object
                it.comments = updatedComments as ArrayList<Comment>?
                it.totalComments = updatedComments?.size ?: 0
                if (updatedComments != null) {
                    if (updatedComments.isNotEmpty()){
                        _comments.postValue(updatedComments)
                    }

                }
            }

            // Update Firestore with the modified GroupHug object
            transaction.set(groupHugRef, currentGroupHug ?: GroupHug())
        }.addOnSuccessListener {
            getAllGroupHugs() // Refresh group hugs after removing the comment
            Log.d("Firestore", "Comment removed successfully.")
            _success.postValue(" Comment removed successfully.")
        }.addOnFailureListener { exception ->
            _error.postValue(" Error removing comment: ${exception.message}")
        }
    }





    // Method to add a new GroupHug post
    fun addGroupHug(groupHug: GroupHug) {
        val groupHugRef =
            db.collection("groupHugs").document() // Automatically generates a new document ID

        // Set the default values for totalHugs and totalComments
        groupHug.totalHugs = 0
        groupHug.totalComments = 0

        // Set the document ID as the 'id' field of the GroupHug object
        groupHug.id = groupHugRef.id

        // Set the GroupHug object in Firestore with the new document ID
        groupHugRef.set(groupHug)
            .addOnSuccessListener {
                _success.postValue("GroupHug post added successfully")
                Log.d("GroupHug", "GroupHug post added successfully with ID: ${groupHugRef.id}")
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error adding GroupHug post: ${e.message}")
                _error.postValue("Error adding GroupHug post")
            }
    }

    // Delete a GroupHug post
    fun deleteGroupHug(groupHugId: String) {
        db.collection("groupHugs").document(groupHugId).delete()
            .addOnSuccessListener {
                Log.d("GroupHug", "Group Hug deleted successfully")
                // Refresh the list after deletion
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error deleting GroupHug: ${e.message}")
                _error.postValue("Error deleting Group Hug")
            }
    }

    // Edit a GroupHug post
    fun editGroupHug(groupHugId: String, updatedGroupHug: GroupHug) {
        db.collection("groupHugs").document(groupHugId).set(updatedGroupHug)
            .addOnSuccessListener {
                Log.d("GroupHug", "Group Hug updated successfully")
                // Refresh the list after editing
//                getAllGroupHugs()
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error updating GroupHug: ${e.message}")
                _error.postValue("Error updating Group Hug")
            }
    }

    // Fetch all GroupHug posts for a specific user
    fun getGroupHugsForUser(userId: String) {
        db.collection("groupHugs")
            .whereEqualTo("userId", userId) // Filter by userId
            .get()
            .addOnSuccessListener { documents ->
                val groupHugList = documents.mapNotNull { it.toObject(GroupHug::class.java) }
                _groupHugs.postValue(groupHugList)
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error fetching GroupHugs for user: ${e.message}")
                _error.postValue("Error fetching GroupHugs for user")
            }
    }



}
