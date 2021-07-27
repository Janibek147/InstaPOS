package com.example.instapos.ui.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instapos.data.Resource
import com.example.instapos.data.helper.ProfileImageHelper
import com.example.instapos.data.helper.ProfileHelper
import com.example.instapos.data.model.User

class EditProfileViewModel(private val profileHelper: ProfileHelper): ViewModel() {

    private var mutableUser: MutableLiveData<Resource<String>> = MutableLiveData()
    val user: LiveData<Resource<String>> get() = mutableUser

    fun getCurrentUser(){

        mutableUser.value = Resource.loading()
        profileHelper.getProfileData(
            {
             mutableUser.value = Resource.success(it)
            },
            {
                mutableUser.value = Resource.error(it)
            })

    }


    private var mutableProfileEdit : MutableLiveData<Resource<String>> = MutableLiveData()
    var profileEdit: LiveData<Resource<String>> get() = mutableProfileEdit
        set(value) {}

    fun editProfile(user: User){
        mutableProfileEdit.value = Resource.loading()
        profileHelper.editProfile(user,
            {
                mutableProfileEdit.value = Resource.success("success")
            },
            {
                mutableProfileEdit.value = Resource.error(it)
            })
    }

    private var mutableUserImage: MutableLiveData<Resource<String>> = MutableLiveData()
    val Image: LiveData<Resource<String>> get() = mutableUserImage

    fun sendUserImage(byteArray: ByteArray) {
        mutableUserImage.value = Resource.loading()
        ProfileImageHelper.sendUserImage(byteArray,
            {
                mutableUserImage.value = Resource.success("success")
            },
            {
                mutableUserImage.value = Resource.error(it)
            })
    }
}