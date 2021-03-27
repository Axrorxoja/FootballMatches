package uz.axrorxoja.domain.usecase

import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import uz.axrorxoja.data.model.*
import uz.axrorxoja.data.repository.competition.CompetitionResult
import uz.axrorxoja.data.repository.competition.ICompetitionRepository
import uz.axrorxoja.data.repository.match.IMatchRepository
import uz.axrorxoja.data.repository.match.MatchResult
import uz.axrorxoja.data.repository.team.ITeamRepository
import uz.axrorxoja.data.repository.team.TeamResult
import uz.axrorxoja.domain.global.Const
import uz.axrorxoja.domain.global.DomainState
import java.io.IOException
import java.lang.Exception

class LoadMostWinningTeamUseCaseTest {
    private val matchRepository = Mockito.mock(IMatchRepository::class.java)
    private val competitionRepository = Mockito.mock(ICompetitionRepository::class.java)
    private val teamRepository = Mockito.mock(ITeamRepository::class.java)
    private val testCoroutine = TestCoroutineScope()
    private val dispatcherProvider = TestDispatcherProvider()
    private val repository: ILoadMostWinningTeamUseCase = LoadMostWinningTeamUseCase(
        matchRepository,
        competitionRepository,
        teamRepository,
        dispatcherProvider
    )

    @Test
    fun `loadMostWinningTeamMatches with success`() = testCoroutine.runBlockingTest {
        val competitionId = Const.DEFAULT_COMPETITION_ID
        val season = Season(12L, "2020-09-21", "2021-03-11", "")
        val area = Area(12L, "", "", "")
        val competition = Competition(
            12L,
            area,
            "", "", "",
            season,
            ""
        )
        val competitionResult = CompetitionResult(data = competition)
        Mockito.`when`(competitionRepository.competitionById(competitionId))
            .thenReturn(competitionResult)

        val coach = Coach(1L, "", "", "")
        val player = Player(1L, "", 0)
        val matches = listOf(
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(2L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(3L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(4L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(5L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(6L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(7L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            )
        )
        val matchResult = MatchResult(data = matches)
        Mockito.`when`(
            matchRepository.matchesByCompetition(
                competitionId,
                "2021-02-27",
                "2021-03-27"
            )
        ).thenReturn(matchResult)
        val winningTeam = Team(1L, area, "", "", "", "", "", "", "", "", 0, "", "", "")
        val teamResult = TeamResult(data = winningTeam)
        Mockito.`when`(teamRepository.teamById(1L)).thenReturn(teamResult)
        val state = repository.loadMostWinningTeamMatches()
        Mockito.verify(competitionRepository).competitionById(Mockito.anyLong())
        Mockito.verify(matchRepository).matchesByCompetition(
            Mockito.anyLong(),
            Mockito.anyString(),
            Mockito.anyString()
        )
        Mockito.verify(teamRepository).teamById(Mockito.anyLong())
        Assert.assertTrue(state is DomainState.SuccessTeam)
        Assert.assertTrue((state as DomainState.SuccessTeam).data == winningTeam)
    }

    @Test
    fun `competitionById with fail NoNetwork`() = testCoroutine.runBlockingTest {
        val competitionId = Const.DEFAULT_COMPETITION_ID

        Mockito.`when`(competitionRepository.competitionById(competitionId))
            .thenReturn(CompetitionResult(error = IOException()))

        val state = repository.loadMostWinningTeamMatches()
        Mockito.verify(competitionRepository).competitionById(Mockito.anyLong())
        Assert.assertTrue(state == DomainState.NoNetwork)
    }

    @Test
    fun `competitionById with fail UnKnown`() = testCoroutine.runBlockingTest {
        val competitionId = Const.DEFAULT_COMPETITION_ID

        Mockito.`when`(competitionRepository.competitionById(competitionId))
            .thenReturn(CompetitionResult(error = IndexOutOfBoundsException()))

        val state = repository.loadMostWinningTeamMatches()
        Mockito.verify(competitionRepository).competitionById(Mockito.anyLong())
        Assert.assertTrue(state == DomainState.UnKnownError)
    }

    @Test
    fun `matchesByCompetition fail NoNetwork`() = testCoroutine.runBlockingTest {
        val competitionId = Const.DEFAULT_COMPETITION_ID
        val season = Season(12L, "2020-09-21", "2021-03-11", "")
        val area = Area(12L, "", "", "")
        val competition = Competition(
            12L,
            area,
            "", "", "",
            season,
            ""
        )
        val competitionResult = CompetitionResult(data = competition)
        Mockito.`when`(competitionRepository.competitionById(competitionId))
            .thenReturn(competitionResult)

        val matchResult = MatchResult(error = IOException())
        Mockito.`when`(
            matchRepository.matchesByCompetition(
                competitionId,
                "2021-02-27",
                "2021-03-27"
            )
        ).thenReturn(matchResult)
        val state = repository.loadMostWinningTeamMatches()
        Mockito.verify(competitionRepository).competitionById(Mockito.anyLong())
        Mockito.verify(matchRepository).matchesByCompetition(
            Mockito.anyLong(),
            Mockito.anyString(),
            Mockito.anyString()
        )
        Assert.assertTrue(state is DomainState.NoNetwork)
    }

    @Test
    fun `matchesByCompetition fail UnKnown`() = testCoroutine.runBlockingTest {
        val competitionId = Const.DEFAULT_COMPETITION_ID
        val season = Season(12L, "2020-09-21", "2021-03-11", "")
        val area = Area(12L, "", "", "")
        val competition = Competition(
            12L,
            area,
            "", "", "",
            season,
            ""
        )
        val competitionResult = CompetitionResult(data = competition)
        Mockito.`when`(competitionRepository.competitionById(competitionId))
            .thenReturn(competitionResult)

        val matchResult = MatchResult(error = Exception())
        Mockito.`when`(
            matchRepository.matchesByCompetition(
                competitionId,
                "2021-02-27",
                "2021-03-27"
            )
        ).thenReturn(matchResult)
        val state = repository.loadMostWinningTeamMatches()
        Mockito.verify(competitionRepository).competitionById(Mockito.anyLong())
        Mockito.verify(matchRepository).matchesByCompetition(
            Mockito.anyLong(),
            Mockito.anyString(),
            Mockito.anyString()
        )
        Assert.assertTrue(state is DomainState.UnKnownError)
    }

    @Test
    fun `teamRepository fail NoNetWork`() = testCoroutine.runBlockingTest {
        val competitionId = Const.DEFAULT_COMPETITION_ID
        val season = Season(12L, "2020-09-21", "2021-03-11", "")
        val area = Area(12L, "", "", "")
        val competition = Competition(
            12L,
            area,
            "", "", "",
            season,
            ""
        )
        val competitionResult = CompetitionResult(data = competition)
        Mockito.`when`(competitionRepository.competitionById(competitionId))
            .thenReturn(competitionResult)

        val coach = Coach(1L, "", "", "")
        val player = Player(1L, "", 0)
        val matches = listOf(
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(2L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(3L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(4L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(5L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(6L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(7L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            )
        )
        val matchResult = MatchResult(data = matches)
        Mockito.`when`(
            matchRepository.matchesByCompetition(
                competitionId,
                "2021-02-27",
                "2021-03-27"
            )
        ).thenReturn(matchResult)
        val teamResult = TeamResult(error = IOException())
        Mockito.`when`(teamRepository.teamById(1L)).thenReturn(teamResult)
        val state = repository.loadMostWinningTeamMatches()
        Mockito.verify(competitionRepository).competitionById(Mockito.anyLong())
        Mockito.verify(matchRepository).matchesByCompetition(
            Mockito.anyLong(),
            Mockito.anyString(),
            Mockito.anyString()
        )
        Mockito.verify(teamRepository).teamById(Mockito.anyLong())
        Assert.assertTrue(state is DomainState.NoNetwork)
    }

    @Test
    fun `teamRepository fail UnKnown`() = testCoroutine.runBlockingTest {
        val competitionId = Const.DEFAULT_COMPETITION_ID
        val season = Season(12L, "2020-09-21", "2021-03-11", "")
        val area = Area(12L, "", "", "")
        val competition = Competition(
            12L,
            area,
            "", "", "",
            season,
            ""
        )
        val competitionResult = CompetitionResult(data = competition)
        Mockito.`when`(competitionRepository.competitionById(competitionId))
            .thenReturn(competitionResult)

        val coach = Coach(1L, "", "", "")
        val player = Player(1L, "", 0)
        val matches = listOf(
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(2L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(3L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(4L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(5L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(6L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            ),
            Match(
                1L, season, "", "", 0,
                0, "", "", "",
                TeamInfo(1L, "", coach, player, listOf(), listOf()),
                TeamInfo(7L, "", coach, player, listOf(), listOf()),
                Score(ScoreState.HOME_TEAM)
            )
        )
        val matchResult = MatchResult(data = matches)
        Mockito.`when`(
            matchRepository.matchesByCompetition(
                competitionId,
                "2021-02-27",
                "2021-03-27"
            )
        ).thenReturn(matchResult)
        val teamResult = TeamResult(error = Exception())
        Mockito.`when`(teamRepository.teamById(1L)).thenReturn(teamResult)
        val state = repository.loadMostWinningTeamMatches()
        Mockito.verify(competitionRepository).competitionById(Mockito.anyLong())
        Mockito.verify(matchRepository).matchesByCompetition(
            Mockito.anyLong(),
            Mockito.anyString(),
            Mockito.anyString()
        )
        Mockito.verify(teamRepository).teamById(Mockito.anyLong())
        Assert.assertTrue(state is DomainState.UnKnownError)
    }
}