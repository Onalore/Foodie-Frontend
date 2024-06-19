package com.example.foodiefrontend.service
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object BackendApi {

    //En emulador 10.0.2.2 = localhost
    private val BASE_URL = "http://10.0.2.2:8080/api/"
    //Para levantarlo en el celu usar ngrok desde la terminal "ngrok http 8080" y usar url
    //private val BASE_URL = "https://ee8f-186-152-218-200.ngrok-free.app/api/"
    //para utilizar serveo
    //private val BASE_URL = "https://b6f8d3facef479c59e400bcde633f011.serveo.net/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .build()

    fun createRecipesService(): RecipesService {
        return retrofit.create(RecipesService::class.java)
    }

    fun createStockService(): StockService {
        return retrofit.create(StockService::class.java)
    }

    fun createUserService(): UserService {
        return retrofit.create(UserService::class.java)
    }

}