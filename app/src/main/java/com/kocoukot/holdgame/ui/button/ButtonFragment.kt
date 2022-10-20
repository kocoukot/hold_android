package com.kocoukot.holdgame.ui.button

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.*
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.kocoukot.holdgame.BuildConfig
import com.kocoukot.holdgame.Constant.ONE_DAY_PRODUCT_ID
import com.kocoukot.holdgame.Constant.ONE_TRY_PRODUCT_ID
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.core_mvi.BaseFragment
import com.kocoukot.holdgame.core_mvi.ComposeRoute
import com.kocoukot.holdgame.ui.button.model.ButtonRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ButtonFragment : BaseFragment<ButtonViewModel>() {
    override val viewModel: ButtonViewModel by viewModel()
    private var mRewardedAd: RewardedAd? = null
    private var TAG = "MainActivity"
    private val adRequest = AdRequest.Builder().build()

    override val screenContent: @Composable (ButtonViewModel) -> Unit =
        @Composable { MainButtonScreenContent(viewModel) }


    private val billingClient by lazy {
        BillingClient.newBuilder(requireContext())
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
    }

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                getProductsList()
                Timber.i("purchases ${purchases.first().products}")
                when {
                    purchases.first().products.first().equals(ONE_DAY_PRODUCT_ID) -> {
                        viewModel.onUserGotTryForDay()
                        handlePurchase(purchases.first())
                    }
                    purchases.first().products.first().equals(ONE_TRY_PRODUCT_ID) -> {
                        handlePurchase(purchases.first())
                        viewModel.onUserGotOneMoreTry()
                    }
                }
            } else {
                // Handle any other error codes.
            }
        }


    override fun onStart() {
        super.onStart()
        activity?.window?.apply {
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.main_background)
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.main_background)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        billStartConnection()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                viewModel.onUserGotOneMoreTry()
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
                    getProductsList()
                }
            }

            override fun onBillingServiceDisconnected() {
                billStartConnection()
            }
        })
    }

    private fun getProductsList() {
        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(ONE_TRY_PRODUCT_ID)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build(),

                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(ONE_DAY_PRODUCT_ID)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build()
                    )
                )
                .build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Timber.i("productDetailsList $productDetailsList")
                viewModel.onBillsGot(productDetailsList.toList())
            }
        }
    }

    private fun launchBillFlow(product: ProductDetails) {
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(product)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        billingClient.launchBillingFlow(requireActivity(), billingFlowParams)
    }

    private fun handlePurchase(purchase: Purchase) {
        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                billingClient.consumePurchase(consumeParams)
            }
        }
    }

    override fun observeData(cb: ((ComposeRoute) -> Unit)?) {
        super.observeData { route ->
            when (route) {
                ButtonRoute.ShowAd -> showAd()
                is ButtonRoute.LaunchBill -> launchBillFlow(route.product)
            }
        }
    }
}