package com.alexlyxy.alexretrofitlessontwo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alexretrofitlessontwo.data.ProductApi
import com.alexlyxy.alexretrofitlessontwo.data.RepositoryImpl
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        //val productApi = retrofit.create(ProductApi::class.java)
       // val repositoryImpl = retrofit.create(RepositoryImpl::class.java)
        val repositoryImpl = retrofit.create(RepositoryImpl::class.java)



        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //val product = productApi.getProductById(1)
                val product =repositoryImpl.getProductById(1)

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
//                        Picasso.get().load(product.images[1]).into(ivImageOne)
//                        Picasso.get().load(product.images[2]).into(ivImageTwo)
//                        Picasso.get().load(product.images[3]).into(ivImageThree)
                    }
                }
            }
        }
    }
}

//class MainViewModel : ViewModel() {
//
//    private val repository = ShopListRepositoryImpl
//
//    private val getShopListUseCase = GetShopListUseCase(repository)
//    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
//    private val editShopItemUseCase = EditShopItemUseCase(repository)
//
//    val shopList = getShopListUseCase.getShopList()
//
//    fun deleteShopItem(shopItem: ShopItem) {
//        deleteShopItemUseCase.deleteShopItem(shopItem)
//    }
//
//    fun changeEnableState(shopItem: ShopItem) {
//        val newItem = shopItem.copy(enabled = !shopItem.enabled)
//        editShopItemUseCase.editShopItem(newItem)
//    }
