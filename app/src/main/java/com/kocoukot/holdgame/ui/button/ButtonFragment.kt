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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
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


        RewardedAd.load(
            requireContext(),
            BuildConfig.GOOGLE_WATCH_KEY,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.d("ad was not loaded")
                    Log.d(TAG, adError.toString())
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Timber.d("ad was loaded")
                    mRewardedAd = rewardedAd
                }
            })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mRewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mRewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }

    private fun showAd() {
        if (mRewardedAd != null) {
            mRewardedAd?.show(requireActivity()) {
                fun onUserEarnedReward(rewardItem: RewardItem) {
                    var rewardAmount = rewardItem.amount
                    var rewardType = rewardItem.type
                    Log.d(TAG, "User earned the reward.")
                }
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
    }

}