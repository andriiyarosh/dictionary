package com.work.dictionarry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.work.dictionarry.dagger.Component
import com.work.dictionarry.dagger.ComponentHolder
import com.work.dictionarry.dagger.DaggerComponent

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ComponentHolder.getApplicationComponent()
    }
}
