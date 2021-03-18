package com.opah.desafio.felipe.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.opah.desafio.felipe.R
import com.opah.desafio.felipe.models.CharacterResults

class MarvelRecyclerAdapter(private val onClickListener: OnClickListener) :
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
        val news = marvelList[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(news)
        }
        holder.bind(news)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(marvelList: CharacterResults) {

            val name = itemView.findViewById<TextView>(R.id.textView)

            name.text = marvelList.name

            Glide.with(itemView.findViewById<ImageView>(R.id.imageView))
                .load(marvelList.thumbnail.path + "/portrait_incredible." + marvelList.thumbnail.extension)
                .into(itemView.findViewById(R.id.imageView))
        }
    }


    class OnClickListener(val clickListener: (marvelList: CharacterResults) -> Unit) {
        fun onClick(marvelList: CharacterResults) = clickListener(marvelList)
    }
}