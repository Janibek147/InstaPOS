package com.example.instapos.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.instapos.R
import com.example.instapos.data.Settings
import com.example.instapos.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject

class MainFragment : Fragment(R.layout.fragment_main) {

    private val settings: Settings by inject()
    private lateinit var navControler: NavController
    private lateinit var binding: FragmentMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings.signedIn = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        navControler = Navigation.findNavController(requireActivity(), R.id.FragmentContainer)
        binding.bnv.setupWithNavController(navControler)

    }
}