package com.example.instapos.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instapos.data.helper.AuthHelper
import com.example.instapos.data.Resource

class SignUpViewModel(private val authHelper: AuthHelper) : ViewModel() {
    private var mutableSignUpStatus: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val signUpStatus: LiveData<Resource<Any?>>
        get() = mutableSignUpStatus

    fun signUp(email: String, password: String) {
        mutableSignUpStatus.value = Resource.loading()
        authHelper.signUp(email, password,
            {
                addUserToDb()
            },
            {
                mutableSignUpStatus.value = Resource.error(it)
            })
    }

    private fun addUserToDb(){
        authHelper.addUserToDb(
            {
                mutableSignUpStatus.value = Resource.success(null)
            },
         {
             mutableSignUpStatus.value = Resource.error(it)
        })
    }
}