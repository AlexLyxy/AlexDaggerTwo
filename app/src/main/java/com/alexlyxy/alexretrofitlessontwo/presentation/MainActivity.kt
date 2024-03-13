package com.alexlyxy.alexretrofitlessontwo.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexlyxy.alexretrofitlessontwo.databinding.ActivityMainBinding
import com.alexlyxy.alexretrofitlessontwo.domain.GetProductUseCase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var  getProductUseCase:  GetProductUseCase

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProductUseCase = GetProductUseCase ()

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val product = getProductUseCase.getLatestProduct()

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
                    }
                }
            }
        }
    }
}


class QuestionsListActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var stackoverflowApi: StackoverflowApi

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_questions_list)

        // init pull-down-to-refresh
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            fetchQuestions()
        }

        // init recycler view
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        questionsAdapter = QuestionsAdapter{ clickedQuestion ->
            QuestionDetailsActivity.start(this, clickedQuestion.id)
        }
        recyclerView.adapter = questionsAdapter

        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        stackoverflowApi = retrofit.create(StackoverflowApi::class.java)
    }

    override fun onStart() {
        super.onStart()
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            showProgressIndication()
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null) {
                    questionsAdapter.bindData(response.body()!!.questions)
                    isDataLoaded = true
                } else {
                    onFetchFailed()
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed()
                }
            } finally {
                hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

    private fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    class QuestionsAdapter(
        private val onQuestionClickListener: (Question) -> Unit
    ) : RecyclerView.Adapter<QuestionViewHolder>() {

        private var questionsList: List<Question> = java.util.ArrayList(0)

        inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.txt_title)
        }

        fun bindData(questions: List<Question>) {
            questionsList = ArrayList(questions)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_question_list_item, parent, false)
            return QuestionViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.title.text = questionsList[position].title
            holder.itemView.setOnClickListener {
                onQuestionClickListener.invoke(questionsList[position])
            }
        }

        override fun getItemCount(): Int {
            return questionsList.size
        }

    }
}