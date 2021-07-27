package com.example.instapos.ui.profile.edit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instapos.R
import com.example.instapos.data.Resource
import com.example.instapos.databinding.FragmentProfileEditBinding
import com.example.instapos.ui.add.AddPostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class EditProfileFragment : Fragment(R.layout.fragment_profile_edit) {

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
    }

    private lateinit var binding: FragmentProfileEditBinding
    private val viewModel: EditProfileViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCurrentUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileEditBinding.bind(view)
        navController = findNavController()
        binding.apply {

            cancelBtn.setOnClickListener {
                navController.popBackStack()
            }

            doneBtn.setOnClickListener {
                binding.doneBtn.isDrawingCacheEnabled = true
                binding.doneBtn.buildDrawingCache()
                val bitmap = (binding.doneBtn.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos)
                val data = baos.toByteArray()
                viewModel.sendUserImage(data)
            }

            tvEditImage.setOnClickListener {
                pickImageFromGallery()
            }
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.user.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.ResourceState.LOADING -> {
                    binding.loading.isVisible = true
                }
                Resource.ResourceState.SUCCESS -> {
                    binding.apply {
                        loading.isVisible = false
                        val user = it.data!!
                        Glide.with(this@EditProfileFragment)
                    }
                }
                Resource.ResourceState.ERROR -> {
                    binding.loading.isVisible = false
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AddPostFragment.IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == AddPostFragment.IMAGE_PICK_CODE) {
            binding.tvEditImage.setImageURI(data?.data)
        }
    }
}