package com.hold.ui.button

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.hold.R
import com.hold.arch.presentation.StatefulFragment
import com.hold.databinding.FragmentButtonBinding
import com.hold.ui.common.ext.navController
import com.hold.ui.common.ext.viewBinding
import com.hold.utils.DateUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class ButtonFragment : StatefulFragment<ButtonViewModel>(R.layout.fragment_button) {
    override val viewModel: ButtonViewModel by viewModel()
    private val binding by viewBinding(FragmentButtonBinding::bind)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            toLeaderboard.apply {
                setOnClickListener {
                    navController.navigate(R.id.action_buttonFragment_to_leaderboardFragment)
                }
            }

            holdButton.apply {
//                AskContinueFragment.create(parentFragmentManager)

                setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        hintImage.animate().alpha(0.0f).duration = 200
                        viewModel.onButtonStartHold()
                    }

                    if (event.action == MotionEvent.ACTION_UP) {
                        viewModel.onButtonStopHold()
//                        AskContinueFragment.create(parentFragmentManager)
                    }
                    true
                }
            }

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        observeScreenState(ButtonScreenState::timer) {
            binding.timerView.text = DateUtil.timeTimerFormat(it)
        }
    }
}