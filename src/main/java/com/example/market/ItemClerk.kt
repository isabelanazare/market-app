package com.example.market

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.market.model.MainModel

class ItemClerk : AppCompatActivity() {
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_clerk_view)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
    }
}
