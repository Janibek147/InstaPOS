package com.example.instapos.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instapos.R
import com.example.instapos.data.Resource
import com.example.instapos.databinding.FragmentProfileBinding
import com.example.instapos.ui.main.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: Fragment(R.layout.fragment_profile) {

        private val viewModel: ProfileViewModel by viewModel()
        private lateinit var binding: FragmentProfileBinding
        private lateinit var parentNavController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        setUpObservers()
        viewModel.getProfileData()
        parentNavController = (parentFragment?.parentFragment as MainFragment ).findNavController()

        binding.btnEdit.setOnClickListener {
            parentNavController.navigate(R.id.action_mainFragment_to_editProfileFragment)
        }
    }

    private fun setUpObservers(){
        viewModel.profile.observe(viewLifecycleOwner,{
            when(it.status){
                Resource.ResourceState.LOADING ->{
                    setLoading(true)
                }
                Resource.ResourceState.SUCCESS ->{
                    setLoading(false)
                    binding.apply {
                        val data = it.data
                        tvUserName.text = data!!.email
                        tvBiography.text = data.biography
                        tvFollowersCount.text = data.followersCount.toString()
                        tvFollowingCount.text = data.followersCount.toString()
                        tvPostCount.text = data.postCount.toString()
                        Glide.with(this@ProfileFragment)
                            .load(data.image)
                            .centerCrop()
                            .into(avatar)
                    }
                }
                
            }
        })
    }

    private fun setLoading(isLoading: Boolean){
        binding.apply {
            Loading.isVisible= isLoading
            btnEdit.isEnabled = isLoading
            rvPosts.isEnabled = isLoading
            tvUserName.isEnabled = isLoading
        }
    }
}