package com.hold.data.repository


import com.hold.domain.model.user.GameResult
import com.hold.domain.model.user.GameUser

class GameRepositoryImpl : GameRepository {


    override suspend fun getGlobalResults(): List<GameUser> = listOf(
        GameUser(
            id = "sdfasdfs",
            avatar = "",
            userName = "Panda",
            records = mutableListOf(
                GameResult(
                    date = 1654871408000L,
                    result = 52234L
                ),
                GameResult(
                    date = 1654871408000L,
                    result = 5623432L
                ),
                GameResult(
                    date = 1654871408000L,
                    result = 58363432L
                ),
            )
        ),
        GameUser(
            id = "ewuybsdkjh",
            avatar = "",
            userName = "Giraffe",
            records = mutableListOf(
                GameResult(
                    date = 1654957808000L,
                    result = 123432L
                ),
                GameResult(
                    date = 1654957808000L,
                    result = 634832L
                ),
                GameResult(
                    date = 1654871408000L,
                    result = 4364332L
                ),
            )
        ),

        GameUser(
            id = "jksnrevj",
            avatar = "",
            userName = "Wolf",
            records = mutableListOf(
                GameResult(
                    date = 1654957808000L,
                    result = 5852332L
                ),
                GameResult(
                    date = 1654957808000L,
                    result = 528332452L
                ),
                GameResult(
                    date = 1654871408000L,
                    result = 582346332L
                ),
            )
        ),
    )


}
