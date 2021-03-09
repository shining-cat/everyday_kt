package fr.shining_cat.everyday.domain

sealed class Result<out T> {
    data class Success<out T>(
        val result: T
    ) : Result<T>()

    data class Error(
        val errorCode: Int,
        val errorResponse: String,
        val exception: Exception?
    ) : Result<Nothing>() {

        override fun toString() = "code: $errorCode | response: $errorResponse | exception: $exception"
    }
}