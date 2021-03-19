package com.opah.desafio.felipe.home.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.opah.desafio.felipe.R
import com.opah.desafio.felipe.models.CharacterResults

class MarvelRecyclerAdapter(private val getCharacter: (CharacterResults) -> Unit) :
        RecyclerView.Adapter<MarvelRecyclerAdapter.ViewHolder>() {

    var marvelList = listOf<CharacterResults>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_marvel_hero, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = marvelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val marvel = marvelList[position]
        holder.bind(marvel, getCharacter)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(marvelList: CharacterResults, getCharacter: (CharacterResults) -> Unit) {

            val name = itemView.findViewById<TextView>(R.id.textView)
            val image = itemView.findViewById<ImageView>(R.id.imageView)
            val click = itemView.findViewById<ConstraintLayout>(R.id.id_marvel)

            click.setOnClickListener { getCharacter(marvelList) }
            name.text = marvelList.name

            Glide.with(image.context)
                    .load(marvelList.thumbnail.getCompletePath())
                    .into(image)
        }
    }
}