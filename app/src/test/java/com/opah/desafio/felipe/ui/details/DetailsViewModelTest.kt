package com.opah.desafio.felipe.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.opah.desafio.felipe.models.MarvelComicsData
import com.opah.desafio.felipe.models.MarvelComicsResponse
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
internal class DetailsViewModelTest {
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var detailsViewModel: DetailsViewModel

    @Mock
    private lateinit var repository: CharacterRepository

    @Before
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        detailsViewModel = DetailsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getComicsByCharacterId() = TestCoroutineDispatcher().runBlockingTest {
        val expected = listOf(DetailsViewModel.ScreenState.Loading)
        val actual = mutableListOf<DetailsViewModel.ScreenState>()

        Mockito.`when`(repository.findComics(112))
            .thenReturn(
                Response.success(
                    MarvelComicsResponse(
                        MarvelComicsData(
                            offset = 1,
                            total = 1,
                            results = arrayListOf(),
                            limit = 1,
                            count = 1
                        )
                    )
                )
            )

        detailsViewModel.state.observeForever {
            actual.add(it)
        }

        detailsViewModel.getComicsByCharacterId()
        Assert.assertEquals(expected, actual)
    }


}