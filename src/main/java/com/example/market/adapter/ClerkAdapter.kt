package com.example.market.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.market.R
import com.example.market.domain.Product
import kotlinx.android.synthetic.main.item_clerk_view.view.*
import kotlinx.android.synthetic.main.item_client_view.view.name
import kotlinx.android.synthetic.main.item_client_view.view.price
import kotlinx.android.synthetic.main.item_client_view.view.quantity


class ClerkAdapter : RecyclerView.Adapter<ClerkAdapter.ElementViewAdapter>() {
    private var mValues = mutableListOf<Product>()

    fun setData(products: List<Product>) {
        mValues.clear()
        mValues.addAll(products)
        notifyDataSetChanged()
    }

    class ElementViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clerk_view, parent, false)
        return ElementViewAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementViewAdapter, position: Int) {
        holder.view.name.text = mValues[position].name
        holder.view.quantity.text = mValues[position].quantity.toString()
        holder.view.price.text = mValues[position].price.toString()
        holder.view.status.text = mValues[position].status
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

}
