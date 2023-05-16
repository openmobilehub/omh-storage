package com.omh.android.storage.sample.drive.adapter.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omh.android.storage.sample.databinding.RvItemFilefolderGridBinding
import com.omh.android.storage.sample.model.UiFileFolderItemData
import com.omh.android.storage.sample.model.UiFileItemData
import com.omh.android.storage.sample.model.UiFolderItemData

class FilesFoldersGridAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemsInGrid = mutableListOf<UiFileFolderItemData>()

    override fun getItemCount() = itemsInGrid.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder = when (viewType) {
        UiFileItemData.RV_TYPE -> {
            FileInGridViewHolder(
                RvItemFilefolderGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
        UiFolderItemData.RV_TYPE -> {
            FolderInGridViewHolder(
                RvItemFilefolderGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
        else -> throw IllegalStateException("Unknown view type: $viewType")
    }

    override fun getItemViewType(position: Int) = when (itemsInGrid[position]) {
        is UiFileItemData -> UiFileItemData.RV_TYPE
        is UiFolderItemData -> UiFolderItemData.RV_TYPE
        else -> -1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FileInGridViewHolder -> holder.bind(itemsInGrid[position] as UiFileItemData)
            is FolderInGridViewHolder -> holder.bind(itemsInGrid[position] as UiFolderItemData)
        }
    }

    fun addAll(items: List<UiFileFolderItemData>) {
        itemsInGrid.clear()
        itemsInGrid.addAll(items)
        notifyDataSetChanged()
    }

}
