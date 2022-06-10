package com.hold.data.network

object Endpoint {

    object Email {
        const val CHECK = "check"
    }

    // AuthService
    object Auth {
        const val SIGN_UP = "signup"
        const val LOGIN = "login"
        const val FB_LOGIN = "fblogin"
        const val RESTORE_PASSWORD = "restore-password"
        const val RESET_PASSWORD = "reset-password"
        const val LOGOUT = "logout"
        const val DELETE_ACCOUNT = "delete-account"
        const val VERIFY_CODE = "verify-code"
        const val RESEND_OTP_CODE = "resend-verification-code"
    }

    object Profile {
        const val MY_PROFILE = "my-profile"
        const val UPDATE = "update"
        const val WEATHER_UNIT = "weather-unit"
        const val TRANSPORT_MODE = "transport-mode"
        const val DISTANCE_UNIT = "distance-unit"
        const val INTERESTS = "interests"
        const val UPDATE_NAME = "update-name"
        const val UPDATE_DEVICE_INFO = "update-device-info"
        const val UPDATE_LOCATION = "update-location"
        const val UPDATE_NOTIFICATIONS_SETTING = "update-notification-settings"
        const val SET_UPCOMING_CALENDAR_EVENTS = "set-upcoming-calendar-events"
        const val PLACES_LIST = "places-list"
        const val ADD_PLACE = "add-place"
        const val UPDATE_PLACE = "update-place"
        const val SET_PLACE_STATUS = "set-place-status"
    }


}
