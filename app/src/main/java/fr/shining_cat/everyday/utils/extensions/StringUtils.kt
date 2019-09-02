package fr.shining_cat.everyday.utils.extensions

fun ellipsizeStringFallbackIfNullOrEmpty(stringInput: String?, length: Int, fallbackIfNull: String = "", fallbackIfEmpty: String = ""): String {
    return when {
        stringInput == null -> fallbackIfNull
        stringInput.isEmpty() -> fallbackIfEmpty
        stringInput.length <= length -> stringInput
        else -> "${stringInput.substring(0, length-1)}…"
    }
}