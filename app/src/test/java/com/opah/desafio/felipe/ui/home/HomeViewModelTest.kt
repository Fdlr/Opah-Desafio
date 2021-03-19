package com.opah.desafio.felipe.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.opah.desafio.felipe.models.CharacterAllResponse
import com.opah.desafio.felipe.models.CharacterResponse
import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.repository.CharacterRepository
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
internal class HomeViewModelTest {
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var repository: CharacterRepository

    @Before
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        homeViewModel = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCharacters() = TestCoroutineDispatcher().runBlockingTest {
        val expected = listOf(HomeViewModel.ScreenState.Loading)
        val actual = mutableListOf<HomeViewModel.ScreenState>()

        Mockito.`when`(repository.getCharacters())
            .thenReturn(
                Response.success(
                    CharacterResponse(
                        CharacterAllResponse(
                            offset = 0,
                            count = 0,
                            limit = 0,
                            results = arrayListOf(),
                            total = 0
                        )
                    )
                )
            )

        homeViewModel.state.observeForever {
            actual.add(it)
        }

        homeViewModel.getCharacters()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun navigateDetails() = TestCoroutineDispatcher().runBlockingTest {
        val expected = listOf(
            HomeViewModel.ScreenState.NavigateDetails(
                CharacterResults(
                    characterId = 1222,
                    name = "Hero",
                    description = "TestUnit",
                    thumbnail = null
                )
            )
        )
        val actual = mutableListOf<HomeViewModel.ScreenState>()

        Mockito.`when`(repository.getCharacters())
            .thenReturn(
                Response.success(
                    CharacterResponse(
                        CharacterAllResponse(
                            offset = 0,
                            count = 0,
                            limit = 0,
                            results = arrayListOf(),
                            total = 0
                        )
                    )
                )
            )

        homeViewModel.state.observeForever {
            actual.add(it)
        }

        homeViewModel.navigateDetails(
            CharacterResults(
                characterId = 1222,
                name = "Hero",
                description = "TestUnit",
                thumbnail = null
            )
        )
        Assert.assertEquals(expected, actual)
    }


}