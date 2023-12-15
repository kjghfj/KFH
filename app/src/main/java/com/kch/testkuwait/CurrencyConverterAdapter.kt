package com.kch.testkuwait

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class CurrencyConverterAdapter (private val mList: List<Rates>) :
    RecyclerView.Adapter<CurrencyConverterAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyTrip = mList[position]

        holder.txtCurrency.text = currencyTrip.aED
        holder.txtCurrencyAmount.text =currencyTrip.aRS

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var txtCurrency: AppCompatTextView = itemView.findViewById(R.id.txtcurrency)
        var txtCurrencyAmount: AppCompatTextView = itemView.findViewById(R.id.txtcurrencyAmount)
    }

    companion object {
        var context: Context? = null
    }
}