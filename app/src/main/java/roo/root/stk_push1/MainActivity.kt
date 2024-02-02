package roo.root.stk_push1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import roo.root.stk_push1.databinding.ActivityMainBinding
import roo.root.stk_push1.model.STKPushRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.util.Base64;
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.btnStkpush.setOnClickListener{
            val businessShortCode = "174379"
            val passkey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"
            val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
            val timestamp = dateFormat.format(Date())
            val password = Base64.encodeToString("$businessShortCode$passkey$timestamp".toByteArray(), Base64.NO_WRAP)
            val transactionType = "CustomerPayBillOnline"
            val amount = binding.etAmount.text.toString()
            val partyA = "600990"
            val partyB = "600000"
            val phoneNumber = binding.etNumber.text.toString()
            val callBackURL = "https://eolg9b8syvjbt9z.m.pipedream.net"
            val accountReference = "Tests"
            val transactionDesc = "Tests"

            val stkPushRequest = STKPushRequest(
                businessShortCode,
                password,
                timestamp,
                transactionType,
                amount,
                partyA,
                partyB,
                phoneNumber,
                callBackURL,
                accountReference,
                transactionDesc
            )

            GlobalScope.launch(Dispatchers.IO) {
                val mediaType = "application/json".toMediaTypeOrNull()
                val requestBody = RequestBody.create(
                    mediaType, """
            {
                "BusinessShortCode": "${stkPushRequest.businessShortCode}",
                "Password": "${stkPushRequest.password}",
                "Timestamp": "${stkPushRequest.timestamp}",
                "TransactionType": "${stkPushRequest.transactionType}",
                "Amount": "${stkPushRequest.amount}",
                "PartyA": "${stkPushRequest.partyA}",
                "PartyB": "${stkPushRequest.partyB}",
                "PhoneNumber": "${stkPushRequest.phoneNumber}",
                "CallBackURL": "${stkPushRequest.callBackURL}",
                "AccountReference": "${stkPushRequest.accountReference}",
                "TransactionDesc": "${stkPushRequest.transactionDesc}"
            }
        """.trimIndent()
                )

                val requestUrl = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest"
                val requestBuilder = Request.Builder()
                    .url(requestUrl)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer ${stkPushRequest.accessToken}")
                val client = OkHttpClient()
                try {
                    val response = client.newCall(requestBuilder.build()).execute()
                    if (response.isSuccessful) {
                        val responseData = response.body?.string() ?: "Empty response body"
                        Log.d("Response", responseData)
                    } else {
                        Log.e("Error", "Error: ${response.code} ${response.message}")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("Error", "Error: ${e.message}")
                }
            }
        }

    }
}