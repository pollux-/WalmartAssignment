package com.test.walmartcountrylist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.walmartcountrylist.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, CountryFragment())
            .commit()
    }
}