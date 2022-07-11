package com.kocoukot.holdgame.data.network.model.response.leaderboard

import com.google.gson.annotations.SerializedName

class GlobalResultsResponse(
    @SerializedName("leaderboard") val leaderboard: List<LeaderboardResponse>,
) {

    class LeaderboardResponse(
        @SerializedName("userInfo") val userInfo: UserInfoResponse,
        @SerializedName("result") val result: GameResultResponse,
    )
}