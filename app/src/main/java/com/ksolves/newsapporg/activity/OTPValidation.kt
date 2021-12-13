package com.ksolves.newsapporg.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.ksolves.newsapporg.R
import com.ksolves.newsapporg.utils.SmsBroadcastReceiver
import kotlinx.android.synthetic.main.activity_o_t_p_validation.*
import java.util.regex.Pattern

class OTPValidation : AppCompatActivity() {
    private var mVerificationId: String? = null
    private var mAuth: FirebaseAuth? = null
    private val REQ_USER_CONSENT = 200
    var smsBroadcastReceiver : SmsBroadcastReceiver? = null
    lateinit var otpTextField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p_validation)
        otpTextField = findViewById(R.id.otpTextField)

        val mobileNumber = intent.getStringExtra("mobileNumber")
        Log.d("TAGccp", mobileNumber.toString())

        mAuth = FirebaseAuth.getInstance()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+$mobileNumber", // Phone number to verify
            60, // Timeout duration
            java.util.concurrent.TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks

        startSmartUserConsent()

        verifyButton.setOnClickListener {
            if (!otpTextField.text.isNullOrEmpty()) {
                verifyVerificationCode(otpTextField.text.toString())
            }
        }


    }

    private val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    verifyVerificationCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this@OTPValidation, e.message, Toast.LENGTH_LONG).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(this@OTPValidation, e.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                mVerificationId = s
                //mResendToken = forceResendingToken
            }
        }

    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        //verification successful we will start the profile activity
                        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
                        val i = Intent(this, MainActivityy::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this,"Failed", Toast.LENGTH_LONG).show()
                    }
                })
    }

    private fun startSmartUserConsent() {

        val client = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_USER_CONSENT){

            if(resultCode == RESULT_OK && data != null){

                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                getOtpFromMessage(message)


            }

        }

    }

    private fun getOtpFromMessage(message: String?) {

        val otpPatter = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPatter.matcher(message)
        if (matcher.find()){

            otpTextField!!.setText(matcher.group(0))

        }


    }

    private fun registerBroadcastReceiver(){

        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver!!.smsBroadcastReceiverListener = object  : SmsBroadcastReceiver.SmsBroadcastReceiverListener{
            override fun onSuccess(intent: Intent?) {

                startActivityForResult(intent,REQ_USER_CONSENT)

            }

            override fun onFailure() {

            }


        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver,intentFilter)

    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReceiver)
    }

}