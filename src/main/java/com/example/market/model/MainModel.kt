package com.example.market.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.market.ProductsApp
import com.example.market.domain.Product
import com.example.market.service.ProductService
import com.example.market.service.ServiceFactory

class MainModel : ViewModel() {

    private val service: ProductService = ServiceFactory
        .createRetrofitService(ProductService::class.java, ProductService.SERVICE_ENDPOINT)

    private val mutableProducts = MutableLiveData<List<Product>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableMessage = MutableLiveData<String>()

    val products: LiveData<List<Product>> = mutableProducts
    val loading: LiveData<Boolean> = mutableLoading
    val message: LiveData<String> = mutableMessage

    fun fetchProductsFromNetwork(app: ProductsApp) {
        mutableLoading.postValue(true)
        viewModelScope.launch {
            try {
                mutableProducts.value = service.getProducts()
                launch(Dispatchers.IO) {
                    app.db.productDao.deleteProducts()
                    app.db.productDao.addProducts(products.value!!)
                }
            } catch (e: Exception) {
                mutableMessage.value = "Received an error while retrieving the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

    fun fetchProducts(app: ProductsApp) {
        mutableLoading.value = true
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val numberOfProducts = app.db.productDao.numberOfProducts
                if (numberOfProducts <= 0) {
                    fetchProductsFromNetwork(app)
                }
            }
        } catch (e: Exception) {
            mutableMessage.value = "Received an error while retrieving local data: ${e.message}"
        } finally {
            mutableLoading.value = false
        }
    }

    fun addProduct(app: ProductsApp, product: Product) {
        mutableLoading.value = true
        viewModelScope.launch {
            try {
                service.addProduct(product)
                launch(Dispatchers.IO) {
                    app.db.productDao.addProduct(product)
                }
            } catch (e: Exception) {
                mutableMessage.value = "Received an error while adding the data: ${e.message}"
            } finally {
                mutableLoading.value = false
            }
        }
    }

}