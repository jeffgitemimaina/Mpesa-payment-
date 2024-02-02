package roo.root.stk_push1.model

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class STKPushAsyncTask : AsyncTask<STKPushRequest, Void, String>() {
    override fun doInBackground(vararg params: STKPushRequest): String {
        val request = params[0]
        val url = URL(request.url)

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Authorization", "Bearer ${request.accessToken}")

        try {
            val outputStream = connection.outputStream
            outputStream.write(request.body.toByteArray())
            outputStream.flush()

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                reader.close()
                return response.toString()
            } else {
                return "Error: ${connection.responseMessage}"
            }
        } finally {
            connection.disconnect()
        }
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        // Handle the response
    }
}