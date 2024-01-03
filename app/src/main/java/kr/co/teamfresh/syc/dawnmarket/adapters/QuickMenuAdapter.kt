package kr.co.teamfresh.syc.dawnmarket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.teamfresh.syc.dawnmarket.data.models.AppMainQuickMenuDTO
import kr.co.teamfresh.syc.dawnmarket.databinding.QuickmenuRecyclerViewBinding
import kr.co.teamfresh.syc.dawnmarket.utils.DevelopmentToast

class QuickMenuAdapter(private var quickMenus: List<AppMainQuickMenuDTO>) :
    RecyclerView.Adapter<QuickMenuAdapter.QuickMenuViewHolder>() {

    class QuickMenuViewHolder(val binding: QuickmenuRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickMenuViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = QuickmenuRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return QuickMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuickMenuViewHolder, position: Int) {
        val quickMenu = quickMenus[position]
        holder.binding.quickMenu = quickMenu
        Glide.with(holder.binding.quickMenuImageRecyclerView.context)
            .load("https://d1afu5va4iy6dc.cloudfront.net/${quickMenu.quickMenuImgPath}")
            .into(holder.binding.quickMenuImageRecyclerView)
        holder.binding.quickMenuNameRecyclerView.text = quickMenu.quickMenuNm

        val showToast = View.OnClickListener {
            DevelopmentToast.showToast(holder.itemView.context)
        }
        holder.binding.quickMenuImageRecyclerView.setOnClickListener(showToast)
        holder.binding.quickMenuNameRecyclerView.setOnClickListener(showToast)

        holder.binding.executePendingBindings()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateQuickMenus(newQuickMenus: List<AppMainQuickMenuDTO>) {
        quickMenus = newQuickMenus
        notifyDataSetChanged()
    }

    override fun getItemCount() = quickMenus.size
}