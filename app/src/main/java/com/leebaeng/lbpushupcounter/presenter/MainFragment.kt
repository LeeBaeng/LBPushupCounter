package com.leebaeng.lbpushupcounter.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.leebaeng.lbpushupcounter.R
import com.leebaeng.lbpushupcounter.databinding.FragmentHistoryBinding
import com.leebaeng.lbpushupcounter.databinding.FragmentMainBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.fragment = this

        view.findViewById<AdView>(R.id.adView).loadAd(AdRequest.Builder().build())
    }

    fun onClick(view: View) {
        when(view) {
            binding.btnStartExercise -> navController.navigate(R.id.action_mainFragment_to_pushUpFragment)
            binding.btnShowHistory -> navController.navigate(R.id.action_mainFragment_to_historyFragment)
            binding.btnAppInfo -> navController.navigate(R.id.action_mainFragment_to_adFragment)
        }
    }
}