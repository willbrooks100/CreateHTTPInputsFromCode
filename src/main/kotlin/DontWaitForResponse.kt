package org.example

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONArray
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
        put("id", uniqueId)
        put("sender", "O=Org-A, L=London, C=GB")
        put("receiver", "O=Org-B, L=London, C=GB")
        put("amount", "111")
        put("tokenType", "GBP")
        put("extraAttributes", JSONObject().apply {
            put("abc", "123")
        })
        put("signatures", JSONArray().apply {
            put(JSONObject().apply {
                put("keyIdentity", JSONObject().apply {
                    put("networkIdentity", "networkIdentity")
                    put("issuer", "issuer")
                    put("serial", "serial")
                })
                put("signature", "signature")
                put("timestamp", "2024-12-04T11:06:20.588656200Z")
                put("signatureSpec", JSONObject().apply {
                    put("signingAlgorithm", "signingAlgorithm")
                    put("hashAlgorithm", "hashAlgorithm")
                    put("maskAlgorithm", "maskAlgorithm")
                    put("maskSpec", "maskSpec")
                    put("saltLen", "1")
                    put("trailerField", "1")
                })
            })
        })
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

    // 1) Execute the request, with response processing and error handling:
//    try {
//        client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) {
//                println("Request failed: ${response.code}")
//            } else {
//                println("Response: ${response.body?.string()}")
//            }
//        }
//    } catch (e: Exception) {
//        println("Error occurred: ${e.message}")
//    }

    // 2) Execute the request - WITHOUT response processing and error handling:
//    client.newCall(request).execute()


    // 3) If you want the program to remain asynchronous or non-blocking (recommended for network calls), you could use OkHttp's enqueue method instead, which doesn't block the main thread:
    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            // Do nothing
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            // Do nothing
        }
    })


}
