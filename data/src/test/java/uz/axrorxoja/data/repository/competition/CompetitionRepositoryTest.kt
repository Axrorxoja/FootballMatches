package uz.axrorxoja.data.repository.competition

import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import uz.axrorxoja.data.model.Area
import uz.axrorxoja.data.model.Competition
import uz.axrorxoja.data.model.Season
import uz.axrorxoja.data.network.Api
import java.io.IOException

class CompetitionRepositoryTest {
    private val mockApi = Mockito.mock(Api::class.java)
    private val repository:ICompetitionRepository = CompetitionRepository(mockApi)
    private val testCoroutine = TestCoroutineScope()

    @Test
    fun `competitionById with success`() = testCoroutine.runBlockingTest {
        val id = 123L
        val competition =
            Competition(12L, Area(12L, "", "", ""), "", "", "", Season(12L, "", "", ""), "")
        Mockito.`when`(mockApi.competitionById(id)).thenReturn(competition)
        val result = repository.competitionById(id)
        assertTrue(result.data == competition)
        assertNull(result.error)
    }

    @Test
    fun `competitionById with fail`() = testCoroutine.runBlockingTest {
        val id = 123L
        val error = IOException()
        Mockito.`when`(mockApi.competitionById(id)).thenThrow(error)
        val result = repository.competitionById(id)
        assertTrue(result.error == error)
        assertNull(result.data)
    }
}