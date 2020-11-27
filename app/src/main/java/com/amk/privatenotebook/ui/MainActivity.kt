package com.amk.privatenotebook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amk.privatenotebook.R
import com.amk.privatenotebook.ui.headerFragment.HeaderFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HeaderFragment())
            .commit()
    }
}