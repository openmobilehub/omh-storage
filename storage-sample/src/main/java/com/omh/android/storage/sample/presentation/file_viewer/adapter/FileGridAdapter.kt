package com.omh.android.storage.sample.presentation.file_viewer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.model.OmhFileType
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.FileGridAdapterBinding

class FileGridAdapter(
    private val listener: GridItemListener
) : ListAdapter<OmhFile, FileGridAdapter.FileGridViewHolder>(DiffCallBack()) {

    private class DiffCallBack : ItemCallback<OmhFile>() {

        override fun areItemsTheSame(oldItem: OmhFile, newItem: OmhFile) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OmhFile, newItem: OmhFile) = oldItem == newItem
    }

    interface GridItemListener {

        fun onFileClicked(file: OmhFile)
    }

    class FileGridViewHolder(
        private val binding: FileGridAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            private const val URL_DOCUMENT =
                "https://drive-thirdparty.googleusercontent.com/32/type/application/vnd.google-apps.document"
            private const val URL_SHEET =
                "https://drive-thirdparty.googleusercontent.com/32/type/application/vnd.google-apps.spreadsheet"
            private const val URL_PDF =
                "https://drive-thirdparty.googleusercontent.com/32/type/application/pdf"
            private const val URL_PNG =
                "https://drive-thirdparty.googleusercontent.com/32/type/image/png"
            private const val URL_ZIP =
                "https://drive-thirdparty.googleusercontent.com/32/type/application/zip"
            private const val URL_OTHER = "https://static.thenounproject.com/png/3482632-200.png"
        }

        fun bind(file: OmhFile, listener: GridItemListener) {
            val context = binding.root.context
            val iconLink = getFileIconUrl(file.fileType)

            with(binding) {
                fileName.text = file.name
                loadFileIcon(context, iconLink, fileIcon)
                root.setOnClickListener { listener.onFileClicked(file) }
            }
        }

        private fun getFileIconUrl(fileType: OmhFileType) = when (fileType) {
            OmhFileType.PDF -> URL_PDF

            OmhFileType.DOCUMENT,
            OmhFileType.MICROSOFT_WORD,
            OmhFileType.OPEN_DOCUMENT_TEXT -> URL_DOCUMENT

            OmhFileType.SPREADSHEET,
            OmhFileType.MICROSOFT_EXCEL,
            OmhFileType.OPEN_DOCUMENT_SPREADSHEET -> URL_SHEET

            OmhFileType.PNG -> URL_PNG

            OmhFileType.ZIP -> URL_ZIP

            else -> URL_OTHER
        }

        private fun loadFileIcon(
            context: Context,
            iconUrl: String,
            imageView: ImageView
        ) {
            Glide.with(context)
                .asBitmap()
                .load(iconUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = FileGridViewHolder(
        FileGridAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: FileGridViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}
