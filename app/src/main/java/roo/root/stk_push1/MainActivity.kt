package roo.root.stk_push1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import roo.root.stk_push1.databinding.ActivityMainBinding
import roo.root.stk_push1.model.STKPushRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.util.Base64;
import roo.root.stk_push1.model.STKPushAsyncTask

class MainActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.btnStkpush.setOnClickListener{

            val businessShortCode = "YourBusinessShortCode"
            val passkey = "YourPasskey" // Provided by Safaricom
            val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
            val timestamp = dateFormat.format(Date())
            val password = Base64.encodeToString("$businessShortCode$passkey$timestamp".toByteArray(), Base64.NO_WRAP)
            val transactionType = "CustomerPayBillOnline"
            val amount = binding.etAmount.text.toString()// Example amount
            val partyA = "0706100265"
            val partyB = "YourBusinessShortCode"
            val phoneNumber = binding.etNumber.text.toString() // Enter customer's phone number
            val callBackURL = "YourCallBackURL"
            val accountReference = "YourAccountReference"
            val transactionDesc = "YourTransactionDescription"

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

            // Execute AsyncTask to initiate STK push
            val task = STKPushAsyncTask()
            task.execute(stkPushRequest)
        }

    }
}