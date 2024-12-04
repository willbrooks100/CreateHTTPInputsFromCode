package org.example

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun main() {

    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
//    val sleepMillis: Long = 25

// Optional count-in , if required e.g. whist manually killing services.
//    for (j in 0..4){
//        println("### sleep number ......  "+j)
//        sleep(1000)
//    }


    for (i in 0..15 ) {

// ### Send From A to B
        // Generate a unique ID based on the current datetime stamp
        val uniqueIdA = "A_"+timestamp+"_"+i
//        val uniqueId = UUID.randomUUID()

        println("### uniqueId = " + uniqueIdA)

        // Set the sleep time between requests, if any:
//        sleep(sleepMillis)

        // Define the URL with the unique ID
        val url = "http://localhost:8091/upstream-transfer-workflow/v1/start-upstream/$uniqueIdA"

        // Create JSON data
        val jsonObject = JSONObject().apply {
            put("id", uniqueIdA)
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

        // 3) To remain asynchronous and non-blocking (recommended for network calls), use OkHttp's enqueue method instead, which doesn't block the main thread:
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Do nothing
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // Do nothing
            }
        })

// ### Send From B to A
        // Generate a unique ID based on the current datetime stamp
        val uniqueIdB = "B_"+timestamp+"_"+i
//        val uniqueIdB = UUID.randomUUID()

        println("### uniqueIdB = " + uniqueIdB)

        // Set the sleep time between requests, if any:
//        sleep(sleepMillis)

        // Define the URL with the unique ID
        val urlB = "http://localhost:8092/upstream-transfer-workflow/v1/start-upstream/$uniqueIdB"

        // Create JSON data
        val jsonObjectB = JSONObject().apply {
            put("id", uniqueIdB)
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
        val jsonMediaTypeB = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBodyB = jsonObjectB.toString().toRequestBody(jsonMediaTypeB)

//        // Create HTTP client
//        val clientB = OkHttpClient()

        // Build the request
        val requestB = Request.Builder()
            .url(urlB)
            .post(requestBodyB)
            .build()

        // 3) To remain asynchronous and non-blocking (recommended for network calls), use OkHttp's enqueue method instead, which doesn't block the main thread:
        client.newCall(requestB).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Do nothing
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // Do nothing
            }
        })






    }
}
