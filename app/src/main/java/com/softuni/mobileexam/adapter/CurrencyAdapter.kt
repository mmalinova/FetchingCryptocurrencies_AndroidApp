package com.softuni.mobileexam.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softuni.mobileexam.R
import com.softuni.mobileexam.activity.MainActivity
import com.softuni.mobileexam.data.entity.CurrencyDetails
import com.softuni.mobileexam.databinding.CurrencyListItemBinding
import com.softuni.mobileexam.fragment.CurrencyDetailsFragment

class CurrencyAdapter(private val currencies: List<CurrencyDetails>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

        class CurrencyViewHolder(val binding: CurrencyListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CurrencyListItemBinding.inflate(layoutInflater, parent, false)

            return CurrencyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
            val currentCurrency = currencies[position]
            holder.binding.apply {
                currency = currentCurrency.name.substring(0, 1).uppercase() + currentCurrency.name.substring(1).lowercase()
                symbol = currentCurrency.symbol
                price = String.format("${currentCurrency.price}" + " (USD)")
                ivLiked.visibility = if (currentCurrency.favourite) View.VISIBLE else View.GONE

                Glide
                    // context to use for requesting the image
                    .with(root.context)
                    // URL to load the asset from
                    .load(currentCurrency.image)
                    .centerCrop()
                    // image to be shown until online asset is downloaded
                    .placeholder(R.drawable.ic_launcher_foreground)
                    // ImageView to load the online asset into when ready
                    .into(ivImage)

                holder.binding.root.setOnClickListener {
                    (it.context as MainActivity).supportFragmentManager.commit {
                        val bundle = Bundle()
                        bundle.putString("currency_name", currentCurrency.name)
                        replace(R.id.container_view, CurrencyDetailsFragment::class.java, bundle)
                        addToBackStack("currencies_list_to_details")
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return currencies.size
        }
    }