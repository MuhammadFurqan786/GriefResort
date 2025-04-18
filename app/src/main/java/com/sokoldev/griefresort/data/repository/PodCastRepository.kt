package com.sokoldev.griefresort.data.repository;

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.google.gson.GsonBuilder
import com.sokoldev.griefresort.data.models.BookResponse
import com.sokoldev.griefresort.data.models.Podcast
import com.sokoldev.griefresort.data.models.Song
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader


class PodCastRepository(val context: Context) {

    fun getPodcastsFromAssets(): List<Podcast> {
        val json = readJsonFromAssets("podcast.json")
        Log.d("Raw JSON", json ?: "Error reading JSON")

        val gson = GsonBuilder()
            .setLenient()
            .create()

        // Change this to deserialize into a list of podcast  objects
        return gson.fromJson(json, Array<Podcast>::class.java).toList()
    }


    private fun readJsonFromAssets(filename: String): String? {
        var json: String? = null
        try {
            val assetManager: AssetManager = context.assets
            val inputStream: InputStream = assetManager.open(filename)
            val reader: Reader = InputStreamReader(inputStream, Charsets.UTF_8)
            val size = inputStream.available()
            val buffer = CharArray(size)
            reader.read(buffer)
            json = String(buffer)
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Podcast REPOSITORY", "Error reading JSON from assets", e)
        }
        return json
    }
}
