package com.ksolves.newsapporg.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hbb20.CountryCodePicker
import com.ksolves.newsapporg.R
import com.ksolves.newsapporg.utils.CountryData
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),CountryCodePicker.OnCountryChangeListener {
    private var ccp: CountryCodePicker?=null
    private var countryCode:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        Toast.makeText(this,"Country Code "+countryCode,Toast.LENGTH_SHORT).show()
        Log.d("TAGccp", "Country Code "+countryCode)
    }

}