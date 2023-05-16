package com.omh.android.storage.api.domain.model

enum class FileType(val mimeType: String) {

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

    // External MIME
    ZIP("application/zip"),
    IMAGE_PNG("image/png"),
    PDF("application/pdf"),
    OTHER("")
}
