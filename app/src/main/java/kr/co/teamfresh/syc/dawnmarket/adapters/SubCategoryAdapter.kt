package kr.co.teamfresh.syc.dawnmarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.RecyclerView
import kr.co.teamfresh.syc.dawnmarket.data.models.AppSubDispClasInfoDTO
import kr.co.teamfresh.syc.dawnmarket.databinding.SubCategoryRecyclerViewBinding
import kr.co.teamfresh.syc.dawnmarket.viewmodels.SubCategoryViewModel

class SubCategoryAdapter(
    private var subCategories: List<AppSubDispClasInfoDTO>,
    private val subCategoryViewModel: SubCategoryViewModel
) : RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    class SubCategoryViewHolder(val binding: SubCategoryRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SubCategoryRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return SubCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val subCategory = subCategories[position]
        holder.binding.subCategory = subCategory
        holder.binding.detailSubCategoryNameRecyclerView.text = subCategory.subDispClasNm

        holder.binding.detailSubCategoryNameRecyclerView.setOnClickListener {
            subCategoryViewModel.onSubCategoryNameClicked(subCategory.dispClasSeq)
        }

        holder.binding.executePendingBindings()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSubCategories(newSubCategories: List<AppSubDispClasInfoDTO>) {
        subCategories = listOf(AppSubDispClasInfoDTO("", "", -1, -1, "상품 전체")) + newSubCategories
        notifyDataSetChanged()
    }

    override fun getItemCount() = subCategories.size
}