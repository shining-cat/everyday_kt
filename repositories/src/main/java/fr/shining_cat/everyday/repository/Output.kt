package fr.shining_cat.everyday.repository



sealed class Output<out T> {
    data class Success<out T>(
        val result: T
    ) : Output<T>()

    data class Error(val errorCode: Int, val errorResponse: String, val exception: Exception?) :
        Output<Nothing>() {
        override fun toString() =
            "code: $errorCode | response: $errorResponse | exception: $exception"
    }
}