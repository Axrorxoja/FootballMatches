package uz.axrorxoja.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.axrorxoja.data.model.*
import uz.axrorxoja.data.model.CompetitionResponse
import uz.axrorxoja.data.model.MatchResponse

internal interface Api {
    @GET("v2/competitions/{competitionId}/matches?matchday=1")
    suspend fun matches(
        @Path("competitionId") id: Long,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
    ): MatchResponse

    @GET("v2/competitions/")
    suspend fun competitions(): CompetitionResponse

    @GET("v2/competitions/{competitionId}")
    suspend fun competitionById(
        @Path("competitionId") id: Long
    ): Competition

    @GET("v2/teams/{teamId}")
    suspend fun teamById(
        @Path("teamId") id: Long
    ): Team
}