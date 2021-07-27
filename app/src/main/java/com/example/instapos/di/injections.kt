package com.example.instapos.di

import com.example.instapos.data.helper.AuthHelper
import com.example.instapos.data.Settings
import com.example.instapos.data.helper.PostHelper
import com.example.instapos.data.helper.ProfileHelper
import com.example.instapos.ui.add.AddPostViewModel
import com.example.instapos.ui.auth.signin.SignInViewModel
import com.example.instapos.ui.auth.signup.SignUpViewModel
import com.example.instapos.ui.profile.ProfileViewModel
import com.example.instapos.ui.profile.edit.EditProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { AuthHelper(get(),get()) }
    single { Settings(androidContext()) }
    single { ProfileHelper(get(), get())}
    single { PostHelper(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get())}
    viewModel { AddPostViewModel(get())}
}
