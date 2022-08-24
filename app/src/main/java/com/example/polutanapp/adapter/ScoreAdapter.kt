package com.example.polutanapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.polutanapp.databinding.ItemListCityBinding
import com.example.polutanapp.model.UserModel

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    private val listScore = ArrayList<UserModel>()

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }


    private lateinit var onItemClickCallBack: OnItemClickCallBack

    //    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val binding =
//            ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//        return ViewHolder(binding)
//    }
    override fun onCreateViewHolder(
        viewGroup: ViewGroup, viewType: Int
    ): ScoreViewHolder {
        val binding =
            ItemListCityBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ScoreViewHolder(binding)
    }

    inner class ScoreViewHolder(var storyBinding: ItemListCityBinding) :
        RecyclerView.ViewHolder(storyBinding.root) {
        fun bind(user: UserModel) {
            storyBinding.tvAqiNumber.text = user.score.toString()

            if (user.score in 0..50) {
                storyBinding.tvCategoryAqi.text = "Good"
            } else if (user.score in 51..100) {
                storyBinding.tvCategoryAqi.text = "Moderate"
            } else if (user.score in 101..150) {
                storyBinding.tvCategoryAqi.text = "Unhealthy for sensitive group"
            } else if (user.score in 151..200) {
                storyBinding.tvCategoryAqi.text = "Unhealthy"
            } else if (user.score in 201..300) {
                storyBinding.tvCategoryAqi.text = "Very Unhealthy"
            }

            storyBinding.root.setOnClickListener {
                onItemClickCallBack.onItemClicked(user)
            }

        }
    }


    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(user = UserModel("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MmExZDNhNWFiYWUzYTczM2M3MDQwNzkiLCJpYXQiOjE2NTUwMDUzMjl9.Xi8Po3JDcjfzo2NTaS7HI6-CMeIYyPNmaoGrAW4iEis",200,"Prediction Success!",87))
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: UserModel)
    }


}