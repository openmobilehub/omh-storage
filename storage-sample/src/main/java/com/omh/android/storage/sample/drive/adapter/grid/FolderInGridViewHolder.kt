package com.omh.android.storage.sample.drive.adapter.grid

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.RvItemFilefolderGridBinding
import com.omh.android.storage.sample.model.UiFolderItemData

class FolderInGridViewHolder(
    binding: RvItemFilefolderGridBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val folderIcon: ImageView = binding.ivFolderGridItem
    private val fileIconPanel: FrameLayout = binding.flIconPanelGridItem
    private val fileName: TextView = binding.tvFileFolderGridItemName

    private fun loadFolderImage() {
        Glide.with(itemView)
            .asBitmap()
            .load("https://cdn-icons-png.flaticon.com/512/3767/3767084.png")
            .apply(
                RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
            ).into(folderIcon)
    }

    fun bind(uiData: UiFolderItemData) {
        fileName.text = uiData.name
        fileIconPanel.visibility = View.GONE
        loadFolderImage()
    }

}
