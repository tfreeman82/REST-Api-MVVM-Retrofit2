package com.tfreeman.foodrecipes.util

import android.util.Log
import com.tfreeman.foodrecipes.models.Recipe

object Testing {
    fun printRecipes(list: List<Recipe>, tag: String?) {
        for (recipe in list) {
            Log.d(tag, "onChanged: " + recipe.getTitle())
        }
    }
}