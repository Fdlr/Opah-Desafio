package com.opah.desafio.felipe.ui.hq

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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
internal class HQViewModelTest {
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var hqViewModel: HQViewModel

    @Mock
    private lateinit var repository: CharacterRepository

    @Before
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        hqViewModel = HQViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCharacters() = TestCoroutineDispatcher().runBlockingTest {
        val expected = listOf(
            HQViewModel.ScreenState.GetHQ(
                MarvelComicsResponse(
                    MarvelComicsData(
                        offset = 1,
                        count = 1,
                        limit = 1,
                        results = arrayListOf(),
                        total = 1
                    )
                )
            )
        )
        val actual = mutableListOf<HQViewModel.ScreenState>()

        repository.saveHQ(
            MarvelComicsResponse(
                MarvelComicsData(
                    offset = 1,
                    count = 1,
                    limit = 1,
                    results = arrayListOf(),
                    total = 1
                )
            )
        )

        hqViewModel.state.observeForever {
            actual.add(it)
        }

        hqViewModel.initViewModel()
        Assert.assertEquals(expected, actual)
    }


}