package com.opah.desafio.felipe.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.opah.desafio.felipe.R
import com.opah.desafio.felipe.ui.home.HomeActivity
import com.opah.desafio.felipe.ui.hq.HQActivity
import com.opah.desafio.felipe.utils.gone
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()

    private var imageView: ImageView? = null
    private var imageViewBack: ImageView? = null
    private var textView: TextView? = null
    private var button: Button? = null

    lateinit var intention: DetailsViewModel.DetailsIntention

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initViewModel()
        viewScreenState()
    }

    private fun initViews(view: View) {
        imageView = view.findViewById(R.id.imageView)
        imageViewBack = view.findViewById(R.id.imageViewBack)
        textView = view.findViewById(R.id.textView)
        button = view.findViewById(R.id.button)
    }

    private fun initViewModel() {
        intention = DetailsViewModel.DetailsIntention(viewModel::takeIntention)
        intention.loadInitialData()

        imageViewBack?.setOnClickListener {
            intention.navigateToHome()
        }

        button?.setOnClickListener {
            viewModel.getComicsByCharacterId()
        }
    }

    private fun viewScreenState() {
        viewModel.state.observeForever { state ->
            when (state) {
                is DetailsViewModel.ScreenState.GetPosition -> {
                    textView?.text = state.value.name

                    Glide.with(imageView?.context!!)
                        .load(state.value.thumbnail!!.getCompletePath())
                        .into(imageView!!)
                }

                is DetailsViewModel.ScreenState.NavigateToHome -> {
                    navigateToHome()
                }

                is DetailsViewModel.ScreenState.ApiSuccess -> {
                    Toast.makeText(requireContext(), "Api carregada com sucesso", Toast.LENGTH_LONG)
                        .show()
                    navigateToHQ()
                }

                is DetailsViewModel.ScreenState.ApiError -> {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }

                is DetailsViewModel.ScreenState.NavigateToHq -> {
                    navigateToHQ()
                }
            }
        }
    }

    private fun navigateToHome() {
        HomeActivity.frameLayout?.gone()
    }

    private fun navigateToHQ() {
        val intent = Intent(requireContext(), HQActivity::class.java)
        startActivity(intent)
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }

}