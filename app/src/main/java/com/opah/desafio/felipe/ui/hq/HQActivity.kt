package com.opah.desafio.felipe.ui.hq

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.opah.desafio.felipe.R
import org.koin.android.viewmodel.ext.android.viewModel


class HQActivity : AppCompatActivity() {

    private val viewModel: HQViewModel by viewModel()

    private var textName: TextView? = null
    private var textPrice: TextView? = null
    private var textHQ: TextView? = null
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_hq)
        initViews()
        initViewModel()
        viewScreenState()
    }


    private fun initViews() {
        textName = findViewById(R.id.txt_nome_hq)
        textPrice = findViewById(R.id.txt_price)
        textHQ = findViewById(R.id.txt_descricao_hq)
        imageView = findViewById(R.id.iv_persona_hq)
    }

    private fun initViewModel() {
        viewModel.initViewModel()
    }

    private fun viewScreenState() {
        viewModel.state.observeForever { state ->
            when (state) {
                is HQViewModel.ScreenState.GetHQ -> {
//                    textView?.text = state.value.name
//
//                    Glide.with(imageView?.context!!)
//                            .load(state.value.thumbnail.getCompletePath())
//                            .into(imageView!!)
                }
            }
        }
    }
}