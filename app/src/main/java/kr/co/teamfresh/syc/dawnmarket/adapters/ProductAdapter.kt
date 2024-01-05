package kr.co.teamfresh.syc.dawnmarket.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.teamfresh.syc.dawnmarket.data.models.AppGoodsInfoDTO
import kr.co.teamfresh.syc.dawnmarket.databinding.ProductRecyclerViewBinding
import kr.co.teamfresh.syc.dawnmarket.viewmodels.ProductViewModel
import java.text.DecimalFormat

class ProductAdapter(
    private var products: List<AppGoodsInfoDTO>,
    private val productviewModel: ProductViewModel
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ProductRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.binding.product = product
        val decimal = DecimalFormat("#,###")

        Glide.with(holder.binding.productImage.context)
            .load("https://d1afu5va4iy6dc.cloudfront.net/${product.imgPath}")
            .into(holder.binding.productImage)

        holder.binding.productNameText.text = product.goodsNm

        if(product.dcPrice != product.slePrice) {
            holder.binding.productOriginalPriceText.text = "${decimal.format(product.slePrice)}원"
            holder.binding.productOriginalPriceText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.productOriginalPriceText.visibility = View.VISIBLE

            val discountRate: Float = product.slePrice.toFloat() / product.dcPrice.toFloat()
            val formattedRate = String.format("%.1f", discountRate)
            holder.binding.productDiscountRateText.visibility = View.VISIBLE
            holder.binding.productDiscountRateText.text = "${formattedRate}%"

            val finalPriceLayoutParams = holder.binding.productFinalPriceText.layoutParams as ViewGroup.MarginLayoutParams
            finalPriceLayoutParams.marginStart = dpToPx(5, holder.itemView.context)
            finalPriceLayoutParams.bottomMargin = dpToPx(5, holder.itemView.context)
            holder.binding.productFinalPriceText.layoutParams = finalPriceLayoutParams
            holder.binding.productFinalPriceText.text = "${decimal.format(product.dcPrice)}원"
        } else holder.binding.productFinalPriceText.text ="${decimal.format(product.slePrice)}원"
        holder.binding.productOptionText.text = product.goodsNrm

        holder.binding.executePendingBindings()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<AppGoodsInfoDTO>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun getItemCount() = products.size

    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}