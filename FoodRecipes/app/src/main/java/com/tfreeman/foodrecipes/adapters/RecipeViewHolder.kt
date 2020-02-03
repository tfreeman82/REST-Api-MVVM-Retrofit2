package com.tfreeman.foodrecipes.adapters

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.tfreeman.foodrecipes.R

class RecipeViewHolder(@NonNull itemView: View, var onRecipeListener: OnRecipeListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var title: TextView
    var publisher: TextView
    var socialScore: TextView
    var image: AppCompatImageView
    override fun onClick(v: View) {
        onRecipeListener.onRecipeClick(getAdapterPosition())
    }

    init {
        title = itemView.findViewById(R.id.recipe_title)
        publisher = itemView.findViewById(R.id.recipe_publisher)
        socialScore = itemView.findViewById(R.id.recipe_social_score)
        image = itemView.findViewById(R.id.recipe_image)
        itemView.setOnClickListener(this)
    }
}