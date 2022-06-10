package com.hold.ui.button

import com.hold.arch.mvvm.state.ScreenState

class ButtonScreenState : ScreenState() {

    var timer by message<Long>()
}