package com.tfreeman.foodrecipes.util

class Constants {

    companion object {
        val BASE_URL = "https://recipesapi.herokuapp.com"

        // API_KEY is no longer necessary since food2fork has shutdown. This can be empty it doesn't matter.
//    public static final String API_KEY = "453556cb475252e7e42d65aa11912447";
        val API_KEY = ""

        val NETWORK_TIMEOUT = 3000

        val DEFAULT_SEARCH_CATEGORIES = arrayOf(
            "Barbeque",
            "Breakfast",
            "Chicken",
            "Beef",
            "Brunch",
            "Dinner",
            "Wine",
            "Italian"
        )

        val DEFAULT_SEARCH_CATEGORY_IMAGES = arrayOf(
            "barbeque",
            "breakfast",
            "chicken",
            "beef",
            "brunch",
            "dinner",
            "wine",
            "italian"
        )
    }

}