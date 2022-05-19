package com.ksolves.newsapporg.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hbb20.CountryCodePicker
import com.ksolves.newsapporg.R


 class LoginActivity : AppCompatActivity(),CountryCodePicker.OnCountryChangeListener {
    private var ccp: CountryCodePicker?=null
    private var countryCode:String?=null
    lateinit var button: Button
    lateinit var mobileNumber : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v1)
        button = findViewById(R.id.button)
        mobileNumber = findViewById(R.id.mobileNumber)
//        spinner.adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_spinner_dropdown_item,
//            CountryData.countryNames
//        )

        ccp = findViewById(R.id.spinner)

        ccp!!.setOnCountryChangeListener(this)
        button.setOnClickListener {
//            val code =
//                CountryData.countryAreaCodes[spinner.selectedItemPosition]
            if (mobileNumber.text.count() == 10) {
                val i = Intent(this, OTPValidation::class.java).apply {
                    putExtra("mobileNumber", countryCode+mobileNumber.text.toString())
                }
                startActivity(i)
            } else {
                Toast.makeText(this, "Enter 10 digits mobile number",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCountrySelected() {
        countryCode=ccp!!.selectedCountryCode
        Toast.makeText(this, "Country Code $countryCode",Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}