package com.example.foodiefrontend.service

import android.content.Context
import com.example.foodiefrontend.R
import java.util.Properties

object Config {
    private lateinit var properties: Properties

    fun load(context: Context) {
        properties = Properties().apply {
            load(context.resources.openRawResource(R.raw.config))
        }
    }

    fun getBaseUrl(): String {
        return properties.getProperty("BASE_URL")
    }
}
