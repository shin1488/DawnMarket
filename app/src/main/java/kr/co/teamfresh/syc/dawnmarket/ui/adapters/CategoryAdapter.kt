package kr.co.teamfresh.syc.dawnmarket.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.teamfresh.syc.dawnmarket.data.models.AppDispClasInfoDTO
import kr.co.teamfresh.syc.dawnmarket.databinding.CategoryRecyclerViewBinding

class CategoryAdapter(private var categories: List<AppDispClasInfoDTO>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: CategoryRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.category = category
        Glide.with(holder.binding.categoryImageRecyclerView.context)
            .load("https://d1afu5va4iy6dc.cloudfront.net/${category.dispClasImgPath}")
            .into(holder.binding.categoryImageRecyclerView)
        holder.binding.categoryNameRecyclerView.text = category.dispClasNm
        holder.binding.executePendingBindings()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newCategories: List<AppDispClasInfoDTO>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    override fun getItemCount() = categories.size
}