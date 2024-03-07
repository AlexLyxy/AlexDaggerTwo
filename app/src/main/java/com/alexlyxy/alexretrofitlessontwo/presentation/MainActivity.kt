package com.alexlyxy.alexretrofitlessontwo.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.data.RepositoryImpl
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityMainBinding
import com.alexlyxy.alexretrofitlessontwo.domain.GetProductUseCase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //private lateinit var  getProductUseCase:  GetProductUseCase

    private val  repository = RepositoryImpl()
    private val getProductUseCase = GetProductUseCase(repository)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getProductUseCase = GetProductUseCase ()

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
               // val product = getProductUseCase.getLatestProduct()
                val product = getProductUseCase.getLocalProduct()

                Log.d("MyLog", "Product : $product")

                runOnUiThread {

                    binding.apply {

                        tvTitle.text = buildString {
                            append("Title:  ")
                            append(product.title)
                        }
                        tvDescr.text = buildString {
                            append("Description:  ")
                            append(product.description)
                        }
                        tvPrice.text = buildString {
                            append("Price:  ")
                            append(product.price)
                        }
                        tvDiscount.text = buildString {
                            append("DiscountPercentage:  ")
                            append(product.discountPercentage)
                        }
                        tvRating.text = buildString {
                            append("Rating:  ")
                            append(product.rating)
                        }
                        tvStock.text = buildString {
                            append("Stock:  ")
                            append(product.stock)
                        }
                        tvBrand.text = buildString {
                            append("Brand:  ")
                            append(product.brand)
                        }
                        tvCategory.text = buildString {
                            append("Category:  ")
                            append(product.category)
                        }
                        tvThumbnail.text = buildString {
                            append("Thumbnail:  ")
                            append(product.thumbnail)
                        }
//                        Picasso.get().load(product.images[1]).into(ivImageOne)
//                        Picasso.get().load(product.images[2]).into(ivImageTwo)
//                        Picasso.get().load(product.images[3]).into(ivImageThree)

                    }
                }
            }
        }
    }
}
