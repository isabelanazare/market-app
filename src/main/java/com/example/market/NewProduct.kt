package com.example.market

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_new_product.*
import com.example.market.domain.Product
import com.example.market.model.MainModel

class NewProduct : AppCompatActivity() {
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        save.setOnClickListener {
            val app: ProductsApp = application as ProductsApp
            model.addProduct(app, Product(0, productName.text.toString(),productDescription.text.toString()
            ,productQuantity.text.toString().toInt(), productPrice.text.toString().toInt(),productStatus.text.toString()))
            finish()
        }
    }
}
