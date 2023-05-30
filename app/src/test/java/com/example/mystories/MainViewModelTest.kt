package com.example.mystories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.mystories.local.entity.Story
import com.example.mystories.remote.LoginRepository
import com.example.mystories.remote.StoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mainViewModel: MainViewModel

    private val dummyToken = "token"

    @Test
    fun `when Get All Stories Not Null and Success`() = runTest {
        val dummyListStories = DataDummy.generateDummyListStory()
        val data = PagedTestSource.snapshot(dummyListStories)

        val stories = MutableLiveData<PagingData<Story>>()
        stories.value = data

        Mockito.`when`(storyRepository.getStories(dummyToken)).thenReturn(stories)

        mainViewModel = MainViewModel(null,storyRepository)

        val actualStories : PagingData<Story> = mainViewModel.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainDispatcherRule.testDispatcher,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )

        differ.submitData(actualStories)

        advanceUntilIdle()
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyListStories.size, differ.snapshot().size)
        Assert.assertEquals(dummyListStories[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get All Stories Return No Data`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expectedList = MutableLiveData<PagingData<Story>>()
        expectedList.value = data

        Mockito.`when`(storyRepository.getStories(dummyToken)).thenReturn(expectedList)

        mainViewModel = MainViewModel(null,storyRepository)

        val actualStories : PagingData<Story> = mainViewModel.getStories(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )

        differ.submitData(actualStories)

        Assert.assertEquals(0, differ.snapshot().size)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}