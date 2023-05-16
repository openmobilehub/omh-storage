package com.omh.android.storage.sample.drive.adapter.grid

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.RvItemFilefolderGridBinding
import com.omh.android.storage.sample.model.UiFileItemData

class FileInGridViewHolder(
    binding: RvItemFilefolderGridBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val fileIcon: ImageView = binding.ivFileGridItem
    private val fileName: TextView = binding.tvFileFolderGridItemName

    private fun loadFileImage(url: String) {
        Glide.with(itemView)
            .asBitmap()
            .load(url)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(fileIcon)
    }

    fun bind(uiData: UiFileItemData) {
        fileName.text = uiData.name
        val iconLink = when (uiData.ext) {
            "txt/plain" -> "https://www.iconarchive.com/download/i103408/paomedia/small-n-flat/file-text.256.png"
            "application/pdf" -> "https://cdn-icons-png.flaticon.com/512/136/136522.png"
            "image/png", "image/jpeg" -> "https://freeiconshop.com/wp-content/uploads/edd/image-outline-filled.png"
            else -> "https://static.thenounproject.com/png/3482632-200.png"
        }
        loadFileImage(iconLink)
    }

}
