package com.tfreeman.foodrecipes.adapters

interface OnRecipeListener {
    fun onRecipeClick(position: Int)

    fun onCategoryClick(category: String)
}