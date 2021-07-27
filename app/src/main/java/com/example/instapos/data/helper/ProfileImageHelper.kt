package com.example.instapos.data.helper

import com.example.instapos.data.N
import com.example.instapos.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileImageHelper(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) {

    fun sendUserImage(
        byteArray: ByteArray,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val compressedPostRef = storage.reference.child("ImageUser/${UUID.randomUUID()}")

        val uploadTask = compressedPostRef.putBytes(byteArray)
        uploadTask.addOnSuccessListener {
            compressedPostRef.downloadUrl.addOnSuccessListener {
                db.document("${N.USERS}/${auth.currentUser!!.photoUrl}").update("image", it.toString())
                    .addOnSuccessListener {
                        onSuccess.invoke()
                    }
                    .addOnFailureListener {
                        onFailure.invoke(it.localizedMessage)
                    }
            }
        }.addOnFailureListener {
            onFailure.invoke(it.localizedMessage)
        }
    }
}
