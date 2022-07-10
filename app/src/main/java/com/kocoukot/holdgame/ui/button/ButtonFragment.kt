package com.kocoukot.holdgame.ui.button

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.kocoukot.holdgame.BuildConfig
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.ui.button.model.ButtonRoute
import com.kocoukot.holdgame.ui.common.ext.navController
import com.kocoukot.holdgame.ui.common.ext.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ButtonFragment : Fragment() {
    private val viewModel: ButtonViewModel by viewModel()
    private var mRewardedAd: RewardedAd? = null
    private var TAG = "MainActivity"
    private val adRequest = AdRequest.Builder().build()

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }

    private var billingClient = BillingClient.newBuilder(requireContext())
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.steps.observeNonNull(viewLifecycleOwner) { route ->
            when (route) {
                ButtonRoute.ToLeaderboard -> navController.navigate(R.id.action_buttonFragment_to_leaderboardFragment)
                ButtonRoute.ToProfile -> navController.navigate(R.id.action_buttonFragment_to_profileFragment)
                ButtonRoute.CloseApp -> requireActivity().finish()
                ButtonRoute.ShowAd -> showAd()
            }
        }
        activity?.window?.apply {
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.main_background)
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.main_background)
        }
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                MainButtonScreenContent(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adInit()
    }

    private fun adInit() {
        RewardedAd.load(
            requireContext(),
            BuildConfig.GOOGLE_WATCH_KEY,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.d("ad was not loaded")
                    Log.d(TAG, adError.toString())
                    mRewardedAd = null
                    viewModel.onAddLoaded(false)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Timber.d("ad was loaded")
                    mRewardedAd = rewardedAd
                    viewModel.onAddLoaded(true)
                }
            })

    }

    private fun showAd() {
        if (mRewardedAd != null) {
            mRewardedAd?.show(requireActivity()) {
                mRewardedAd?.fullScreenContentCallback = adCallBack

                Timber.d("ad was User earned the reward. rewardAmount ")
                viewModel.onUserWatchedAd()
            }
        } else {
            Timber.d("ad was rewarded ad wasn't ready yet.")
        }
    }

    private val adCallBack = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Timber.d("ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            Timber.d("ad was dismissed fullscreen content..")
            adInit()
            mRewardedAd = null
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            // Called when ad fails to show.
            Timber.d("ad was failed to show fullscreen content.")
            mRewardedAd = null
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Timber.d("ad was recorded an impression.")
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Timber.d("ad was showed fullscreen content.")
        }
    }


    private fun billStartConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }

            override fun onBillingServiceDisconnected() {
                billStartConnection()
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

}