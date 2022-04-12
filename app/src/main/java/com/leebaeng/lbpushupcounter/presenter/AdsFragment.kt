package com.leebaeng.lbpushupcounter.presenter

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.leebaeng.lbpushupcounter.R
import com.leebaeng.lbpushupcounter.databinding.FragmentAdsBinding
import com.leebaeng.util.log.logE
import com.leebaeng.util.log.logEX
import com.leebaeng.util.log.logI
import com.leebaeng.util.log.logW
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AdsFragment : BaseFragment(R.layout.fragment_ads) {
    private lateinit var binding: FragmentAdsBinding
    private var mRewardedAd: RewardedAd? = null
    private var retryCntCurrent = 0
    private var worker: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdsBinding.bind(view)

        startAd()
    }

    private fun startAd() {
        worker = CoroutineScope(Dispatchers.Main).launch {
            if (initAds()) {
                addAdListener()
                showAds()
            } else {
                if (retryCntCurrent >= RETRY_COUNT) {
                    Toast.makeText(requireContext(), resources.getString(R.string.loading_failed), Toast.LENGTH_SHORT).show()
                    return@launch navigationNext()
                }
                retryCntCurrent++

                Toast.makeText(requireContext(), resources.getString(R.string.loading), Toast.LENGTH_SHORT).show()
                delay(3000)
                startAd()
            }
        }
    }

    private fun addAdListener() {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() = "Ad was shown.".logI()
            override fun onAdFailedToShowFullScreenContent(adError: AdError?) = "onAdFailedToShowFullScreenContent : ${adError?.message}".logE()
            override fun onAdDismissedFullScreenContent() {
                "Ad was dismissed.".logW()
                mRewardedAd = null
                showRetryAlert()
            }
        }
    }

    private fun showRetryAlert() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.ad_alert_title))
            .setMessage(resources.getString(R.string.ad_alert_message))
            .setPositiveButton(resources.getString(R.string.ad_alert_btn_continue)) { _, _ -> navigationNext() }
            .setNegativeButton(resources.getString(R.string.ad_alert_btn_retry)) { _, _ -> startAd() }
            .create()
            .show()
    }

    private suspend fun initAds(): Boolean = suspendCoroutine {
        RewardedAd.load(requireContext(), "ca-app-pub-3940256099942544/5224354917", AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                "initAds.onAdFailedToLoad : ${adError.message}".logE()
                mRewardedAd = null
                it.resume(false)
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                "Ad was loaded.".logI()
                mRewardedAd = rewardedAd
                if (mRewardedAd != null) {
                    it.resume(true)
                } else {
                    "The rewarded ad wasn't ready yet.".logI()
                    it.resume(false)
                }
            }
        })
    }

    private fun showAds() {
        try {
            mRewardedAd?.show(requireActivity()) {
                "User earned the reward. = $it.type".logW()
                navigationNext()
            }
        } catch (e: Exception) {
            e.logEX("showAds Failed")
            navigationNext()
        }
    }

    private fun navigationNext() {
        navController.navigate(R.id.action_adFragment_to_historyFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        worker?.cancel("destroy fragment")
        mRewardedAd = null
        worker = null
        retryCntCurrent = 0
    }

    companion object {
        private const val RETRY_COUNT = 5
    }
}