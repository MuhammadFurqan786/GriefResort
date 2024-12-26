package com.sokoldev.griefresort.data.repository;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log
import com.google.gson.Gson;
import com.google.gson.GsonBuilder
import com.sokoldev.griefresort.data.models.BookResponse;
import org.json.JSONObject

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


class BookRepository(val context: Context) {

    fun getBooksFromAssets(): BookResponse? {
        val json = readJsonFromAssets("books.json")
        Log.d("Raw JSON", json ?: "Error reading JSON")
        val jsonPrettyPrint = json?.let { JSONObject(it).toString(2) }
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return gson.fromJson(jsonPrettyPrint, BookResponse::class.java)
    }

    private fun readJsonFromAssets(filename: String): String? {
        var json: String? = null
        try {
            val assetManager: AssetManager = context.assets
            val inputStream: InputStream = assetManager.open(filename)
            val reader: Reader = InputStreamReader(inputStream)
            val size = inputStream.available()
            val buffer = CharArray(size)
            reader.read(buffer)
            json = String(buffer)
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BOOOOOK", "Error reading JSON from assets", e)
        }
        return json
    }
}
