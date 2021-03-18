package com.opah.desafio.felipe.home

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opah.desafio.felipe.R
import com.opah.desafio.felipe.home.adapter.MarvelRecyclerAdapter
import com.opah.desafio.felipe.utils.gone
import com.opah.desafio.felipe.utils.visible
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null



    private val adapterMarvel = MarvelRecyclerAdapter(MarvelRecyclerAdapter.OnClickListener {
//          intention.navigateToDetail(it.characterId)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        initViewModel()
        viewScreenState()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerViewID)
        setAdapter()
    }

    private fun initViewModel() {
        val intention = HomeViewModel.MainIntention(viewModel::takeIntention)
        intention.loadInitialData()
    }

    private fun viewScreenState() {
        viewModel.state.observeForever { state ->
            when (state) {
                is HomeViewModel.ScreenState.Loading -> {
                    progressBar?.visible()
                }

                is HomeViewModel.ScreenState.ApiSuccess -> {
                    progressBar?.gone()
                    adapterMarvel.marvelList = state.value.allResponse.results
                    adapterMarvel.notifyDataSetChanged()
                }

                is HomeViewModel.ScreenState.ApiError -> {
                    progressBar?.gone()
                    Toast.makeText(this, state.error, Toast.LENGTH_LONG)
                }

                else -> {
                    progressBar?.gone()
                }
            }
        }
    }

    private fun setAdapter() {
        val manager = LinearLayoutManager(this)
        with(recyclerView!!) {
            layoutManager = manager
            adapter = adapterMarvel
        }
    }
}