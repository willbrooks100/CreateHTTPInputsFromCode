package org.example

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    // Generate a unique ID based on the current datetime stamp
    val uniqueId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
    println("### uniqueId = "+uniqueId)

    // Define the URL with the unique ID
    val url = "http://localhost:8091/upstream-transfer-workflow/v1/start-upstream/$uniqueId"

    // Create JSON data
    val jsonObject = JSONObject().apply {
        put("amount", "505")
        put("tokenType", "ABABAB")
        put("receiver", "O=Org-B, L=London, C=GB")
        put("receiverSignatures", emptyList<String>())
        put("metadata", JSONObject().apply { put("a", "a") })
    }

    // Convert JSON to RequestBody
    val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val requestBody = jsonObject.toString().toRequestBody(jsonMediaType)

    // Create HTTP client
    val client = OkHttpClient()

    // Build the request
    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    // Execute the request
    try {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("Request failed: ${response.code}")
            } else {
                println("Response: ${response.body?.string()}")
            }
        }
    } catch (e: Exception) {
        println("Error occurred: ${e.message}")
    }
}
