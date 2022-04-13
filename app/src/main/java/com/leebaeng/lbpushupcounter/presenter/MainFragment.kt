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

        requireActivity().let{
            CoroutineScope(Dispatchers.IO).launch {
                val user = (it as MainActivity).db.userDao().getUser()
                if(user == null){
                    // TODO : open Create Profile Dialog
                    withContext(Dispatchers.Main){

                        val bindingDialog = DialogCreateProfileBinding.inflate(layoutInflater)
                        val dialog = AlertDialog.Builder(requireContext())
                            .setView(bindingDialog.root)
                            .create()

                        bindingDialog.btnOK.setOnClickListener {
                            dialog.dismiss()
                        }

                        dialog.show()
                    }
                }
            }
        }

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