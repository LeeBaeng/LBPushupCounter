package com.leebaeng.lbpushupcounter.presenter

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.leebaeng.lbpushupcounter.MainActivity
import com.leebaeng.lbpushupcounter.R
import com.leebaeng.lbpushupcounter.databinding.DialogCreateProfileBinding
import com.leebaeng.lbpushupcounter.databinding.FragmentMainBinding
import com.leebaeng.lbpushupcounter.presenter.dialog.CreateProfileDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.fragment = this

        view.findViewById<AdView>(R.id.adView).loadAd(AdRequest.Builder().build())
        CoroutineScope(Dispatchers.IO).launch {
            (requireActivity() as MainActivity).db.let{
                if(it.userDao().getUser() == null)
                    launch(Dispatchers.Main) { CreateProfileDialog(requireContext(), it).show() }
            }
        }
    }

    fun onClick(view: View) {
        when (view) {
            binding.btnStartExercise -> navController.navigate(R.id.action_mainFragment_to_pushUpFragment)
            binding.btnShowHistory -> navController.navigate(R.id.action_mainFragment_to_historyFragment)
            binding.btnAppInfo -> navController.navigate(R.id.action_mainFragment_to_adFragment)
        }
    }
}