package com.opah.desafio.felipe.ui.details

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.opah.desafio.felipe.R
import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.ui.home.HomeActivity
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity: AppCompatActivity() {

    private val viewModel: DetailsViewModel by viewModel()

    private var imageView: ImageView? = null
    private var imageViewBack: ImageView? = null
    private var textView: TextView? = null

    lateinit var intention: DetailsViewModel.DetailsIntention

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initViews()
        initViewModel()
        viewScreenState()
    }

    private fun initViews() {
        imageView = findViewById(R.id.imageView)
        imageViewBack = findViewById(R.id.imageViewBack)
        textView = findViewById(R.id.textView)

    }

    private fun initViewModel() {
        intention = DetailsViewModel.DetailsIntention(viewModel::takeIntention)
        intention.loadInitialData()

        imageViewBack?.setOnClickListener {
            intention.navigateToHome()
        }
    }

    private fun viewScreenState() {
        viewModel.state.observeForever { state ->
            when (state) {
                is DetailsViewModel.ScreenState.GetPosition -> {
                    textView?.text = state.value.name

                    Glide.with(imageView?.context!!)
                            .load(state.value.thumbnail.getCompletePath())
                            .into(imageView!!)
                }

                is DetailsViewModel.ScreenState.NavigateToHome -> {
                    navigateToHome()
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}