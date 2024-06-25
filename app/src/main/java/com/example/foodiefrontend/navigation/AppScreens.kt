package com.example.foodiefrontend.navigation

sealed class AppScreens(val route: String) {

    object WelcomeScreen : AppScreens("welcome_screen")
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object HomeScreen : AppScreens("home_screen")

    object RateRecipeScreen : AppScreens("rate_recipe/{recipeJson}") {
        fun createRoute(recipeJson: String) = "rate_recipe/$recipeJson"
    }
    object RecipesScreen : AppScreens("recipes_screen")
    object RecipeScreen : AppScreens("recipe_screen/{recipeJson}") {
        fun createRoute(recipeJson: String) = "recipe_screen/$recipeJson"
    }
    object SuggestedRecipesScreen : AppScreens("suggested_recipes_screen")
    object RandomRecipesScreen : AppScreens("random_recipes_screen")

    object StockScreen : AppScreens("stock_screen") {
        fun createRoute(codeEan: String?) = if (codeEan != null) "stock_screen/$codeEan" else "stock_screen"
    }

    object ProfileScreen : AppScreens("profile_screen")
    object CameraScreen : AppScreens("camera_screen")
    object FamilyConfigScreen : AppScreens("family_config_screen")
    object AddFamilyScreen : AppScreens("add_family_screen")
    object ModifyFamilyScreen :
        AppScreens("modify_family_screen/{nombre}/{apellido}/{edad}/{restricciones}") {
        fun createRoute(nombre: String, apellido: String, edad: Int, restricciones: String?) =
            if (restricciones.isNullOrEmpty()) {
                "modify_family_screen/$nombre/$apellido/$edad"
            } else {
                "modify_family_screen/$nombre/$apellido/$edad/$restricciones"
            }
    }
}
