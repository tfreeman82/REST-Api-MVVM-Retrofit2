package com.tfreeman.foodrecipes.network

import com.tfreeman.foodrecipes.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider {
    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = retrofitBuilder.build()

    private val recipeApi = retrofit.create(RecipeApi::class.java)

    fun getRecipeApi(): RecipeApi? {
        return recipeApi
    }
}