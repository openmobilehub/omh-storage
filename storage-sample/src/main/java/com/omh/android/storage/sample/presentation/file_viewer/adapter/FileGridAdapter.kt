package com.omh.android.storage.sample.presentation.file_viewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.model.OmhFileType
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.RvItemFilefolderGridBinding

class FileGridAdapter : RecyclerView.Adapter<FileGridAdapter.FileGridViewHolder>() {

    private val files = mutableListOf<OmhFile>()

    class FileGridViewHolder(
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

        fun bind(file: OmhFile) {
            fileName.text = file.name

            val iconLink = when (file.fileType) {
                OmhFileType.PDF -> "https://drive-thirdparty.googleusercontent.com/32/type/application/pdf"

                OmhFileType.DOCUMENT,
                OmhFileType.MICROSOFT_WORD,
                OmhFileType.OPEN_DOCUMENT_TEXT -> "https://drive-thirdparty.googleusercontent.com/32/type/application/vnd.google-apps.document"

                OmhFileType.SPREADSHEET,
                OmhFileType.MICROSOFT_EXCEL,
                OmhFileType.OPEN_DOCUMENT_SPREADSHEET -> "https://drive-thirdparty.googleusercontent.com/32/type/application/vnd.google-apps.spreadsheet"

                OmhFileType.PNG -> "https://drive-thirdparty.googleusercontent.com/32/type/image/png"

                OmhFileType.ZIP -> "https://drive-thirdparty.googleusercontent.com/32/type/application/zip"

                else -> "https://static.thenounproject.com/png/3482632-200.png"
            }

            loadFileImage(iconLink)
        }

    }

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