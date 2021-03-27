package uz.axrorxoja.data.repository.match

import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import uz.axrorxoja.data.model.Area
import uz.axrorxoja.data.model.Competition
import uz.axrorxoja.data.model.MatchResponse
import uz.axrorxoja.data.model.Season
import uz.axrorxoja.data.network.Api
import java.io.IOException

class MatchRepositoryTest{
    private val mockApi = Mockito.mock(Api::class.java)
    private val repository:IMatchRepository = MatchRepository(mockApi)
    private val testCoroutine = TestCoroutineScope()

    @Test
    fun `matchesByCompetition with success`() = testCoroutine.runBlockingTest {
        val id = 123L
        val dateFrom = "test dateFrom"
        val dateTo = "test dateTo"
        val competition =
            Competition(12L, Area(12L, "", "", ""), "", "", "", Season(12L, "", "", ""), "")
        val matchResponse = MatchResponse(0,competition, listOf())
        Mockito.`when`(mockApi.matches(id,dateFrom,dateTo)).thenReturn(matchResponse)
        val result = repository.matchesByCompetition(id,dateFrom,dateTo)
        assertTrue(result.data == matchResponse.matches)
        assertNull(result.error)
    }

    @Test
    fun `matchesByCompetition with fail`() = testCoroutine.runBlockingTest {
        val id = 123L
        val dateFrom = "test dateFrom"
        val dateTo = "test dateTo"
        val error = IOException()
        Mockito.`when`(mockApi.matches(id,dateFrom,dateTo)).thenThrow(error)
        val result = repository.matchesByCompetition(id,dateFrom,dateTo)
        assertNull(result.data)
        assertTrue(result.error == error)
    }
}