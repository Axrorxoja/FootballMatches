package uz.axrorxoja.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import uz.axrorxoja.data.model.Competition
import uz.axrorxoja.data.model.MatchResponse
import uz.axrorxoja.data.model.Team
import uz.axrorxoja.data.util.Config

interface Api {
    @GET("v2/competitions/{competitionId}/matches?matchday=1")
    suspend fun matches(
        @Path("competitionId") id: Long,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): MatchResponse

    @GET("v2/competitions/{competitionId}")
    suspend fun competitionById(
        @Path("competitionId") id: Long
    ): Competition

    @GET("v2/teams/{teamId}")
    suspend fun teamById(
        @Path("teamId") id: Long
    ): Team
}