package com.tfreeman.foodrecipes.adapters

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.tfreeman.foodrecipes.R
import de.hdodenhof.circleimageview.CircleImageView

class CategoryViewHolder(@NonNull itemView: View, listener: OnRecipeListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var categoryImage: CircleImageView
    var categoryTitle: TextView
    var listener: OnRecipeListener

    override fun onClick(v: View) {
        listener.onCategoryClick(categoryTitle.text.toString())
    }

    init {
        this.listener = listener
        categoryImage = itemView.findViewById(R.id.category_image)
        categoryTitle = itemView.findViewById(R.id.category_title)
        itemView.setOnClickListener(this)
    }
}