package com.omh.android.storage.sample.presentation.drive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.databinding.RvItemFilefolderGridBinding

class FileGridAdapter : RecyclerView.Adapter<FileGridViewHolder>() {

    private val files = mutableListOf<OmhFile>()

    override fun getItemCount() = files.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FileGridViewHolder = FileGridViewHolder(
        RvItemFilefolderGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: FileGridViewHolder, position: Int) {
        holder.bind(files[position])
    }

    fun addAll(items: List<OmhFile>) {
        files.clear()
        files.addAll(items)
        notifyDataSetChanged()
    }

}
