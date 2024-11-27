package org.example

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {

    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))

// Optional count-in , if required e.g. whist manually killing services.
//    for (j in 0..4){
//        println("### sleep number ......  "+j)
//        sleep(1000)
//    }

    for (i in 0..6) {

        // Generate a unique ID based on the current datetime stamp
        val uniqueId = timestamp+"_"+i
        println("### uniqueId = " + uniqueId)

//        sleep(175)

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


        // 3) If you want the program to remain asynchronous or non-blocking (recommended for network calls), use OkHttp's enqueue method instead, which doesn't block the main thread:
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Do nothing
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // Do nothing
            }
        })

    }
}
