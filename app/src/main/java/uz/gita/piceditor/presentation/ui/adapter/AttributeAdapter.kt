package uz.gita.piceditor.presentation.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.piceditor.R
import uz.gita.piceditor.data.model.model.common.ImageData
import uz.gita.piceditor.databinding.ItemSelectedAttributeBinding

class AttributeAdapter : ListAdapter<ImageData, AttributeAdapter.AttributeViewHolder>(AttributeDiffUtil) {
    private var selectImageListener: ((Int) -> Unit)? = null

    object AttributeDiffUtil : DiffUtil.ItemCallback<ImageData>() {
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean = oldItem == newItem
    }

    inner class AttributeViewHolder(private val binding: ItemSelectedAttributeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.main.setOnClickListener { selectImageListener?.invoke(absoluteAdapterPosition) }
        }

        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                if (isSelected) binding.main.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.colorSelected))
                else binding.main.setBackgroundColor(Color.WHITE)

                binding.imageOfAttribute.setImageResource(imgRes)
                binding.textOfAttribute.text = name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributeViewHolder =
        AttributeViewHolder(ItemSelectedAttributeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) = holder.bind()

    fun setSelectImageListener(block: ((Int) -> Unit)) {
        selectImageListener = block
    }
}