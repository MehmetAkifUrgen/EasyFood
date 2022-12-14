package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.databinding.PopularItemsBinding
import com.example.easyfood.pojo.CategoryMeals

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick:((CategoryMeals) -> Unit)
    private var mealsList=ArrayList<CategoryMeals>()

    fun setMeals(mealsList: ArrayList<CategoryMeals>){
        this.mealsList=mealsList
        notifyDataSetChanged()
    }
    class PopularMealViewHolder( val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealsItem)
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}