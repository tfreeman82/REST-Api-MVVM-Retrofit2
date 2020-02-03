package com.tfreeman.foodrecipes.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tfreeman.foodrecipes.AppExecutors
import com.tfreeman.foodrecipes.models.Recipe
import com.tfreeman.foodrecipes.network.responses.RecipeResponse
import com.tfreeman.foodrecipes.network.responses.RecipeSearchResponse
import com.tfreeman.foodrecipes.util.Constants
import com.tfreeman.foodrecipes.util.Constants.Companion.NETWORK_TIMEOUT
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.*
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class RecipeApiClient private constructor() {
    private val mRecipes: MutableLiveData<List<Recipe>>
    private var mRetrieveRecipesRunnable: RetrieveRecipesRunnable? = null
    private val mRecipe: MutableLiveData<Recipe>
    private var mRetrieveRecipeRunnable: RetrieveRecipeRunnable? = null
    private val mRecipeRequestTimeout: MutableLiveData<Boolean> = MutableLiveData()
    val recipes: LiveData<List<Recipe>>
        get() = mRecipes

    val recipe: LiveData<Recipe>
        get() = mRecipe

    val isRecipeRequestTimedOut: LiveData<Boolean>
        get() = mRecipeRequestTimeout

    fun searchRecipesApi(query: String?, pageNumber: Int) {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable = null
        }
        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(query, pageNumber)
        val handler: Future<*> =
            AppExecutors.instance?.networkIO()!!.submit(mRetrieveRecipesRunnable)
        AppExecutors.instance?.networkIO()?.schedule(Runnable {
            // let the user know its timed out
            handler.cancel(true)
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    fun searchRecipeById(recipeId: String?) {
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable = null
        }
        mRetrieveRecipeRunnable = RetrieveRecipeRunnable(recipeId)
        val handler: Future<*> =
            AppExecutors.instance?.networkIO()?.submit(mRetrieveRecipeRunnable)!!
        mRecipeRequestTimeout.setValue(false)
        AppExecutors.instance?.networkIO()?.schedule(Runnable {
            // let the user know it's timed out
            mRecipeRequestTimeout.postValue(true)
            handler.cancel(true)
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    private inner class RetrieveRecipesRunnable(
        private val query: String?,
        private val pageNumber: Int
    ) :
        Runnable {
        var cancelRequest = false
        override fun run() {
            try {
                val response: Response<*> = getRecipes(query, pageNumber).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {
                    val list: ArrayList<Any?> =
                        ArrayList((response.body() as RecipeSearchResponse?)?.getRecipes()!!)
                    if (pageNumber == 1) {
                        mRecipes.postValue(list)
                    } else {
                        val currentRecipes: MutableList<Recipe> =
                            mRecipes.getValue()
                        currentRecipes.addAll(list)
                        mRecipes.postValue(currentRecipes)
                    }
                } else {
                    val error = response.errorBody()!!.string()
                    Log.e(TAG, "run: $error")
                    mRecipes.postValue(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                mRecipes.postValue(null)
            }
        }

        private fun getRecipes(
            query: String?,
            pageNumber: Int
        ): Call<RecipeSearchResponse> {
            return ServiceProvider.getRecipeApi().searchRecipe(
                Constants.API_KEY,
                query, pageNumber.toString()
            )
        }

        private fun cancelRequest() {
            Log.d(
                TAG,
                "cancelRequest: canceling the search request."
            )
            cancelRequest = true
        }

    }

    private inner class RetrieveRecipeRunnable(private val recipeId: String?) :
        Runnable {
        var cancelRequest = false
        override fun run() {
            try {
                val response: Response<*> = getRecipe(recipeId).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {
                    val recipe: Recipe = (response.body() as RecipeResponse?).getRecipe()
                    mRecipe.postValue(recipe)
                } else {
                    val error = response.errorBody()!!.string()
                    Log.e(TAG, "run: $error")
                    mRecipe.postValue(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                mRecipe.postValue(null)
            }
        }

        private fun getRecipe(recipeId: String?): Call<RecipeResponse> {
            return ServiceProvider.getRecipeApi().getRecipe(
                Constants.API_KEY,
                recipeId
            )
        }

        private fun cancelRequest() {
            Log.d(
                TAG,
                "cancelRequest: canceling the search request."
            )
            cancelRequest = true
        }

    }

    fun cancelRequest() {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable!!.cancelRequest()
        }
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable!!.cancelRequest()
        }
    }

    companion object {
        private const val TAG = "RecipeApiClient"
        var instance: RecipeApiClient? = null
            get() {
                if (field == null) {
                    field = RecipeApiClient()
                }
                return field
            }
            private set
    }

    init {
        mRecipes = MutableLiveData()
        mRecipe = MutableLiveData()
    }
}

private fun <T> MutableLiveData<T>.postValue(list: Collection<T?>) {

}

private fun ScheduledExecutorService.schedule(runnable: Runnable, networkTimeout: Int, milliseconds: TimeUnit) {

}
