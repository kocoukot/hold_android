package com.hold.data.network

object Endpoint {

    object User {
        const val INSERT_USER = "insert-user-data"
        const val UPDATE_USER = "update-user-data"
        const val ADD_USER_RESULT = "add-user-result"
    }


    object Leaderboard {
        const val GET_GLOBAL_RESULTS = "global-results"
        const val GET_USER_RESULTS = "user-results"
    }
}
