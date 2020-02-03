package com.tfreeman.foodrecipes.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tfreeman.foodrecipes.models.Recipe

class RecipeSearchResponse {

    @SerializedName("count")
    @Expose
    private val count = 0

    @SerializedName("recipes")
    @Expose
    private var recipes: MutableList<Recipe?>? = null

    fun getCount(): Int {
        return count
    }

    fun getRecipes(): List<Recipe?>? {
        return recipes
    }

    override fun toString(): String {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}'
    }
}