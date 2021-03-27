package uz.axrorxoja.data.repository.team

import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import uz.axrorxoja.data.model.*
import uz.axrorxoja.data.network.Api
import java.io.IOException

class TeamRepositoryTest {
    private val mockApi = Mockito.mock(Api::class.java)
    private val repository: ITeamRepository = TeamRepository(mockApi)
    private val testCoroutine = TestCoroutineScope()

    @Test
    fun `teamById with success`() = testCoroutine.runBlockingTest {
        val id = 123L
        val team = Team(1L,Area(12L, "", "", ""),"","","","","","","","",12,"","","")
        Mockito.`when`(mockApi.teamById(id)).thenReturn(team)
        val result = repository.teamById(id)
        assertTrue(result.data == team)
        assertNull(result.error)
    }

    @Test
    fun `teamById with fail`() = testCoroutine.runBlockingTest {
        val id = 123L
        val error = IOException()
        Mockito.`when`(mockApi.teamById(id)).thenThrow(error)
        val result = repository.teamById(id)
        assertTrue(result.error == error)
        assertNull(result.data)
    }
}