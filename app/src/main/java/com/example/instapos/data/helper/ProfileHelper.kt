package com.example.instapos.data.helper

import com.example.instapos.data.N
import com.example.instapos.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileHelper( private val auth: FirebaseAuth, private val db: FirebaseFirestore ) {

     fun getProfileData(onSuccess: (user: User) -> Unit, onFailure: (msg: String?) -> Unit ){
        db.collection(N.USERS).document(auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val res = it.toObject(User::class.java)
                res?.let {user->
                    onSuccess.invoke(user)
                } ?: onFailure.invoke(" User data is empty")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
     }

    fun editProfile(user: User,onSuccess:() -> Unit, onFailure: (msg: String?) -> Unit){
        db.document("${N.USERS}/${user.uid}").set(user)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}