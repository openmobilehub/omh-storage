package com.omh.android.storage.api.domain.model

enum class OmhFileType(
    val mimeType: String,
    val extension: String? = null
) {
    /**
     * Can find documentation about supported MIME types here:
     *
     * https://developers.google.com/drive/api/guides/mime-types
     * https://developers.google.com/drive/api/guides/ref-export-formats
     */

    // Specific MIME from Google Workspace and Drive
    AUDIO("application/vnd.google-apps.audio"),
    DOCUMENT("application/vnd.google-apps.document"),
    THIRD_PARTY_SHORTCUT("application/vnd.google-apps.drive-sdk"),
    DRAWING("application/vnd.google-apps.drawing"),
    FILE("application/vnd.google-apps.file"),
    FOLDER("application/vnd.google-apps.folder"),
    FORM("application/vnd.google-apps.form"),
    FUSIONTABLE("application/vnd.google-apps.fusiontable"),
    JAMBOARD("application/vnd.google-apps.jam"),
    MAP("application/vnd.google-apps.map"),
    PHOTO("application/vnd.google-apps.photo"),
    PRESENTATION("application/vnd.google-apps.presentation"),
    SCRIPT("application/vnd.google-apps.script"),
    SHORTCUT("application/vnd.google-apps.shortcut"),
    SITE("application/vnd.google-apps.site"),
    SPREADSHEET("application/vnd.google-apps.spreadsheet"),
    UNKNOWN("application/vnd.google-apps.unknown"),
    VIDEO("application/vnd.google-apps.video"),

    // Documents
    MICROSOFT_WORD(
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        ".docx"
    ), // .docx
    OPEN_DOCUMENT_TEXT(
        "application/vnd.oasis.opendocument.text",
        ".odt"
    ), // .odt
    RICH_TEXT(
        "application/rtf",
        ".rtf"
    ), // RTF
    PDF(
        "application/pdf",
        ".pdf"
    ), // .pdf
    TEXT(
        "text/plain",
        ".txt"
    ), // .txt
    ZIP(
        "application/zip",
        ".zip"
    ), // ZIP
    EPUB(
        "application/epub+zip",
        ".epub"
    ), // .epub

    // Spreadsheets
    MICROSOFT_EXCEL(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        ".xlsx"
    ), // .xlsx
    OPEN_DOCUMENT_SPREADSHEET(
        "application/x-vnd.oasis.opendocument.spreadsheet",
        ".ods"
    ), // .ods
    CSV(
        "text/csv",
        ".csv"
    ), // .csv
    TSV(
        "text/tab-separated-values",
        ".tsv"
    ), // .tsv

    // Presentations
    MICROSOFT_POWERPOINT(
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        ".pptx"
    ), // .pptx
    OPEN_DOCUMENT_PRESENTATION(
        "application/vnd.oasis.opendocument.presentation",
        ".odp"
    ), // .odp

    // Drawings
    JPEG(
        "image/jpeg",
        "jpg"
    ), // .jpg
    PNG(
        "image/png",
        ".png"
    ), // .png
    SVG(
        "image/svg+xml",
        ".svg"
    ), // .svg

    // Apps Scripts
    JSON(
        "application/vnd.google-apps.script+json",
        ".json"
    ), // .json

    // Video
    MP4(
        "video/mp4",
        ".mp4"
    ),

    // Other MYME type
    OTHER("")
}
