import com.example.foodiefrontend.data.Recipe

data class FavoriteRecipesResponse(
    val success: Boolean,
    val recetas: List<Recipe>
)
