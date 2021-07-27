package com.example.instapos.ui.add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.instapos.R
import com.example.instapos.data.Resource
import com.example.instapos.databinding.FragmentAddPostBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class AddPostFragment : Fragment(R.layout.fragment_add_post) {

    companion object {
        //image pick code
        val IMAGE_PICK_CODE = 1000
    }

    private lateinit var binding: FragmentAddPostBinding
    private val viewModel: AddPostViewModel by viewModel()
    private lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPostBinding.bind(view)
        navController = findNavController()
        pickImageFromGallery()

        binding.btnPost.setOnClickListener {
            binding.ivPost.isDrawingCacheEnabled = true
            binding.ivPost.buildDrawingCache()
            val bitmap = (binding.ivPost.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val data = baos.toByteArray()
            viewModel.sendNewPost(data, binding.etDescription.text.toString())
        }
        setUpobservers()
    }

    private fun setUpobservers() {
        viewModel.post.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.ResourceState.SUCCESS -> {
                    binding.Loading.isVisible = false
                    navController.navigate(R.id.action_addPostFragment_to_homeFragment)
                }
                Resource.ResourceState.ERROR -> {
                    binding.Loading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.ResourceState.LOADING -> {
                    binding.Loading.isVisible = true
                }
            }
        }
    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ivPost.setImageURI(data?.data)
        }
    }
}