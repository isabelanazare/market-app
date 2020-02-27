package com.example.market

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.clerk_product_list.*
import com.example.market.domain.Product
import com.example.market.model.MainModel
import com.example.market.utils.logd
import android.widget.Toast
import com.example.market.adapter.ClientAdapter

class ClientActivity : AppCompatActivity() {

    private var adapter: ClientAdapter? = null
    private lateinit var manager: Manager
    private lateinit var model: MainModel

    private fun checkIfOnline(): Boolean{
        if(!manager.networkConnectivity(applicationContext)){
            Toast.makeText(this, "You don't have internet connection", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_product_list)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        manager = Manager()
        if(checkIfOnline()) {
            setSupportActionBar(toolbar)
            toolbar.title = title

            refresh.setOnClickListener {
                model.fetchProductsFromNetwork(application as ProductsApp)
            }

            assert(recyclerView != null)
            setupRecyclerView(recyclerView)
            observeModel()
            loadProducts()
        }
    }

    private fun observeModel() {
        model.loading.observe { displayLoading(it) }
        model.products.observe { displayProducts(it ?: emptyList()) }
        model.message.observe { showError(it) }
    }

    private fun displayProducts(products: List<Product>) {
        adapter?.setData(products)
    }

    private fun displayLoading(loading: Boolean?) {
        logd("displayLoading: $loading")
        progress.visibility = if (loading!!) View.VISIBLE else View.GONE
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit) =
        observe(this@ClientActivity, Observer { observe(it) })

    private fun loadProducts() {
        model.fetchProducts(application as ProductsApp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logd("Back in main activity")
    }

    private fun showError(message: String?) {
        progress.visibility = View.GONE
        var errorMessage = "Unknown error"
        if (message != null) {
            errorMessage = message
        }
        Snackbar.make(recyclerView!!, errorMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction("RETRY") { loadProducts() }.show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = ClientAdapter()
        (application as ProductsApp).db.productDao.products
            .observe(this, Observer { products ->
                if (products != null) {
                    adapter!!.setData(products)
                }
            })
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.buttons, menu)
        return true
    }

}
