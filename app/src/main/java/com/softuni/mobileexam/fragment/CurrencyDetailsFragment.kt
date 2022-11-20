package com.softuni.mobileexam.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.softuni.mobileexam.R
import com.softuni.mobileexam.activity.MainActivity
import com.softuni.mobileexam.databinding.FragmentCurrencyDetailsBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedCurrencyName = arguments?.getString("currency_name", null)
        GlobalScope.launch {
            (activity as? MainActivity)?.currencyViewModel?.getCurrencyByName(
                selectedCurrencyName ?: return@launch
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun observeData() {
        GlobalScope.launch {
            (activity as? MainActivity)?.currencyViewModel?.selectedCurrency?.collect {
                binding.currency = it ?: return@collect

                (activity as? MainActivity)?.runOnUiThread {
                    setDataBinding()
                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it.image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivImage)
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setDataBinding() {
        binding.currency ?: return
        if (binding.currency?.favourite == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        binding.btnLike.setOnClickListener {
            val currency = binding.currency
            currency?.favourite = currency?.favourite != true

            if (currency?.favourite == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            GlobalScope.launch {
                (activity as? MainActivity)?.currencyViewModel?.updateFavourites(
                    currency ?: return@launch
                )
            }
        }
    }
}