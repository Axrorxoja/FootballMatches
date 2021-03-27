package uz.axrorxoja.footballmatches.presentation.vm.team

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import uz.axrorxoja.data.model.Area
import uz.axrorxoja.data.model.Team
import uz.axrorxoja.domain.global.DomainState
import uz.axrorxoja.domain.usecase.ILoadMostWinningTeamUseCase
import uz.axrorxoja.footballmatches.TestResProvider

class TeamViewModelTest {
    private val useCase = Mockito.mock(ILoadMostWinningTeamUseCase::class.java)
    private val resProvider = TestResProvider()
    private val testCoroutine = TestCoroutineScope()
    private val vm = TeamViewModel(useCase, resProvider)

    init {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun `loadData with success`() = testCoroutine.runBlockingTest {
        val list = mutableListOf<TeamScreenState>()
        vm.screenState
            .onEach(list::add)
            .launchIn(testCoroutine)
        val area = Area(12L, "", "", "")
        val team = Team(1L, area, "", "", "", "", "", "", "", "", 0, "", "", "")
        Mockito.`when`(useCase.loadMostWinningTeamMatches())
            .thenReturn(DomainState.SuccessTeam(team))
        vm.loadData()
        val default = list[0]
        val progress = list[1]
        val success = list[2]
        Assert.assertTrue(list.size == 3)

        Assert.assertTrue(default.default)
        Assert.assertNull(default.success)
        Assert.assertNull(default.progress)
        Assert.assertNull(default.error)

        Assert.assertFalse(progress.default)
        Assert.assertNotNull(progress.progress)
        Assert.assertNull(progress.success)
        Assert.assertNull(progress.error)

        Assert.assertFalse(success.default)
        Assert.assertNotNull(success.success)
        Assert.assertNull(success.progress)
        Assert.assertNull(success.error)
    }

    @Test
    fun `loadData with NoNetwork`() = testCoroutine.runBlockingTest {
        val list = mutableListOf<TeamScreenState>()
        vm.screenState
            .onEach(list::add)
            .launchIn(testCoroutine)
        Mockito.`when`(useCase.loadMostWinningTeamMatches())
            .thenReturn(DomainState.NoNetwork)
        vm.loadData()
        val default = list[0]
        val progress = list[1]
        val fail = list[2]
        Assert.assertTrue(list.size == 3)

        Assert.assertTrue(default.default)
        Assert.assertNull(default.success)
        Assert.assertNull(default.progress)
        Assert.assertNull(default.error)

        Assert.assertFalse(progress.default)
        Assert.assertNotNull(progress.progress)
        Assert.assertNull(progress.success)
        Assert.assertNull(progress.error)

        Assert.assertFalse(fail.default)
        Assert.assertNotNull(fail.error)
        Assert.assertNull(fail.success)
        Assert.assertNull(fail.progress)
    }

    @Test
    fun `loadData with UnKnownError`() = testCoroutine.runBlockingTest {
        val list = mutableListOf<TeamScreenState>()
        vm.screenState
            .onEach(list::add)
            .launchIn(testCoroutine)
        Mockito.`when`(useCase.loadMostWinningTeamMatches())
            .thenReturn(DomainState.UnKnownError)
        vm.loadData()
        val default = list[0]
        val progress = list[1]
        val fail = list[2]
        Assert.assertTrue(list.size == 3)

        Assert.assertTrue(default.default)
        Assert.assertNull(default.success)
        Assert.assertNull(default.progress)
        Assert.assertNull(default.error)

        Assert.assertFalse(progress.default)
        Assert.assertNotNull(progress.progress)
        Assert.assertNull(progress.success)
        Assert.assertNull(progress.error)

        Assert.assertFalse(fail.default)
        Assert.assertNotNull(fail.error)
        Assert.assertNull(fail.success)
        Assert.assertNull(fail.progress)
    }

}