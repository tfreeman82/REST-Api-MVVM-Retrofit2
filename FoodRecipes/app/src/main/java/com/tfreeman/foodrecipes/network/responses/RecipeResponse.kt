package com.tfreeman.foodrecipes.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tfreeman.foodrecipes.models.Recipe

class RecipeResponse {
    @SerializedName("recipe")
    @Expose
    private val recipe: Recipe? = null

    fun getRecipe(): Recipe? {
        return recipe
    }

    override fun toString(): String {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}'
    }
}