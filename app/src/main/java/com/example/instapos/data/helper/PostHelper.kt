package com.example.instapos.data.helper

import com.example.instapos.data.N
import com.example.instapos.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class PostHelper(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) {

    fun sendNewPost(
        byteArray: ByteArray, description: String,
        onSuccess: () -> Unit, onFailure: (msg: String?) -> Unit
    ) {

        val compresedPostRef = storage.reference.child("compresedPosts/${UUID.randomUUID()}")

        val uploadTast = compresedPostRef.putBytes(byteArray)
        uploadTast.addOnSuccessListener {
            compresedPostRef.downloadUrl.addOnSuccessListener {
                val post = Post(
                    id = UUID.randomUUID().toString(),
                    imageUrl = compresedPostRef.downloadUrl.toString(),
                    userId = auth.currentUser!!.uid,
                    description = description
                )
                db.document("${N.POSTS}/${post.id}").set(post)
                    .addOnSuccessListener {
                        onSuccess.invoke()
                    }.addOnFailureListener {
                        onFailure.invoke(it.localizedMessage)
                    }
            }
        }.addOnFailureListener {
            onFailure.invoke(it.localizedMessage)
        }
    }
}