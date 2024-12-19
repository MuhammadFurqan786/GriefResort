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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> get() = _success

    // Fetch all GroupHug posts
    fun getAllGroupHugs() {
        db.collection("groupHugs").get()
            .addOnSuccessListener { documents ->
                val groupHugList = documents.mapNotNull { it.toObject(GroupHug::class.java) }
                groupHugList.forEach { groupHug ->
                    groupHug.id?.let {
                        getComments(it) { comments ->
                            groupHug.comments = comments
                        }
                    }
                }
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

    // Retrieve comments for a GroupHug post
    private fun getComments(groupHugId: String, callback: (List<Comment>) -> Unit) {
        val commentsRef = db.collection("groupHugs").document(groupHugId).collection("comments")
        commentsRef.get()
            .addOnSuccessListener { documents ->
                val comments = documents.mapNotNull { it.toObject(Comment::class.java) }
                callback(comments)
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error fetching comments: ${e.message}")
                callback(emptyList()) // Return empty list in case of error
            }
    }

    // Add a comment to a GroupHug post
    // Add a comment to a GroupHug post with an ID
    fun addComment(groupHugId: String, comment: Comment) {
        val commentsRef = db.collection("groupHugs").document(groupHugId).collection("comments")

        // Generate a new document reference to get the ID
        val newCommentRef = commentsRef.document()

        // Set the ID field in the comment object
        comment.commentId = newCommentRef.id

        // Add the comment to Firestore
        newCommentRef.set(comment)
            .addOnSuccessListener {
                _success.postValue("Comment added successfully")
                Log.d("GroupHug", "Comment added successfully with ID: ${newCommentRef.id}")
            }
            .addOnFailureListener { e ->
                Log.e("GroupHug", "Error adding comment: ${e.message}")
                _error.postValue("Error adding comment")
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
