package com.example.foodiefrontend.service

import android.content.Context
import com.example.foodiefrontend.R
import java.util.Properties

object Config {
    private lateinit var properties: Properties
    private const val API_PATH = "/api/"

    fun load(context: Context) {
        properties = Properties().apply {
            load(context.resources.openRawResource(R.raw.config))
        }
    }

    fun getUrl(): String {
        return properties.getProperty("URL")
    }

    fun getBaseUrl(): String {
        val url = getUrl()
        return "$url$API_PATH"
    }
}
