package com.example.market

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.clerk_product_list.*
import com.example.market.adapter.ClerkAdapter
import com.example.market.domain.Product
import com.example.market.model.MainModel
import com.example.market.utils.logd

class ClerkActivity : AppCompatActivity() {

    private var adapter: ClerkAdapter? = null

    private lateinit var manager: Manager
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clerk_product_list)
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        manager = Manager()

        setSupportActionBar(toolbar)
        toolbar.title = title


        fab.setOnClickListener {
            val intent = Intent(application, NewProduct::class.java)
            startActivityForResult(intent, 10000)
        }

        refresh.setOnClickListener {
            model.fetchProductsFromNetwork(application as ProductsApp)
        }

        assert(recyclerView != null)
        setupRecyclerView(recyclerView)
        observeModel()
        loadProducts()
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
        observe(this@ClerkActivity, Observer { observe(it) })

    private fun loadProducts() {
        model.fetchProducts(application as ProductsApp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logd("Back in main activity")
    }


    fun showError(message: String?) {
        progress.visibility = View.GONE
        var errorMessage = "Unknown error"
        if (message != null) {
            errorMessage = message
        }
        Snackbar.make(recyclerView!!, errorMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction("RETRY") { loadProducts() }.show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = ClerkAdapter()
        (application as ProductsApp).db.productDao.products
            .observe(this, Observer { products ->
                if (products != null) {
                    adapter!!.setData(products)
                }
            })
        recyclerView.adapter = adapter
    }
}
