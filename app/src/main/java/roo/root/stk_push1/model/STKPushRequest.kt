package roo.root.stk_push1.model

data class STKPushRequest(
    val businessShortCode: String,
    val password: String,
    val timestamp: String,
    val transactionType: String,
    val amount: String,
    val partyA: String,
    val partyB: String,
    val phoneNumber: String,
    val callBackURL: String,
    val accountReference: String,
    val transactionDesc: String
)

