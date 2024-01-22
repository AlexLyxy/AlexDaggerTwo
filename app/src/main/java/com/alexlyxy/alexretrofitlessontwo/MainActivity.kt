package com.alexlyxy.alexretrofitlessontwo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val productApi = retrofit.create(ProductApi::class.java)

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = productApi.getProductById(1)
                val product1 = productApi.getProductById(2)

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
                            append(product.price.toString())
                        }
                        tvDiscount.text = buildString {
                            append("DiscountPercentage:  ")
                            append(product.discountPercentage.toString())
                        }
                        tvRating.text = buildString {
                            append("Rating:  ")
                            append(product.rating.toString())
                        }
                        tvStock.text = buildString {
                            append("Stock:  ")
                            append(product.stock.toString())
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
                        Picasso.get().load(product.images[1]).into(ivImageOne)
                        Picasso.get().load(product.images[2]).into(ivImageTwo)
                        Picasso.get().load(product.images[3]).into(ivImageThree)



                        binding.apply {
                            tvTitle.text = buildString {
                                append("Title:  ")
                                append(product1.title)
                            }
                            tvDescr.text = buildString {
                                append("Description:  ")
                                append(product1.description)
                            }
                            tvPrice.text = buildString {
                                append("Price:  ")
                                append(product1.price.toString())
                            }
                            tvDiscount.text = buildString {
                                append("DiscountPercentage:  ")
                                append(product1.discountPercentage.toString())
                            }
                            tvRating.text = buildString {
                                append("Rating:  ")
                                append(product1.rating.toString())
                            }
                            tvStock.text = buildString {
                                append("Stock:  ")
                                append(product1.stock.toString())
                            }
                            tvBrand.text = buildString {
                                append("Brand:  ")
                                append(product1.brand)
                            }
                            tvCategory.text = buildString {
                                append("Category:  ")
                                append(product1.category)
                            }
                            tvThumbnail.text = buildString {
                                append("Thumbnail:  ")
                                append(product1.thumbnail)
                            }
                            Picasso.get().load(product1.images[1]).into(ivImageOne)
                            Picasso.get().load(product1.images[2]).into(ivImageTwo)
                            Picasso.get().load(product1.images[3]).into(ivImageThree)
                        }
                    }
                }
            }
        }
    }
}