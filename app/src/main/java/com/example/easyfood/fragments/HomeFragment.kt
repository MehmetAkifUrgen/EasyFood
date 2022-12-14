package com.example.easyfood.fragments

import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.adapters.MostPopularAdapter

import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.pojo.CategoryMeals
import com.example.easyfood.pojo.Meal

import com.example.easyfood.viewModel.HomeViewModel



class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularMealsAdapter:MostPopularAdapter

    companion object{
        const val MEAL_ID="com.example.easyfood.fragments.idMeal"
        const val MEAL_NAME="com.example.easyfood.fragments.nameMeal"
        const val MEAL_THUMB="com.example.easyfood.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularMealsAdapter= MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        homeMvvm.getPopularMeals()
        observerPopularMealsLiveData()
        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        popularMealsAdapter.onItemClick = { meal->
                val intent=Intent(activity,MealActivity::class.java)
                intent.putExtra(MEAL_ID,meal.idMeal)
                intent.putExtra(MEAL_NAME,meal.strMeal)
                intent.putExtra(MEAL_THUMB,meal.strMealThumb)
                startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager =LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,true)
            adapter=popularMealsAdapter
        }
    }

    private fun observerPopularMealsLiveData() {
        homeMvvm.observePopularMealsLiveData().observe(viewLifecycleOwner) { mealList ->
            popularMealsAdapter.setMeals(mealsList = mealList as ArrayList<CategoryMeals>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imageRandomMeal)

                this@HomeFragment.randomMeal=t
            }

        })
    }


}