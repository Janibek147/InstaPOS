package com.example.instapos.ui.splаsh

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.instapos.MainActivity
import com.example.instapos.R
import com.example.instapos.data.Settings
import com.example.instapos.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentSplashBinding.bind(view)
        (requireActivity() as AppCompatActivity).actionBar?.hide()

        val settings = Settings(requireContext())

        binding.lottieView.setMaxProgress(0.6f)
        binding.lottieView.addAnimatorListener(object: Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if(settings.signedIn){
                    navController.navigate(R.id.action_splashFragment_to_mainFragment)
                } else {
                    navController.navigate(R.id.action_splashFragment_to_signInFragment)
                }
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })
    }

}