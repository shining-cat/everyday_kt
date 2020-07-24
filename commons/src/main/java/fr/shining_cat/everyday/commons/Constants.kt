package fr.shining_cat.everyday.commons

class Constants {

    companion object {
        // Error codes
        const val ERROR_CODE_EXCEPTION = -100
        const val ERROR_CODE_NO_RESULT = -101
        const val ERROR_CODE_DATABASE_OPERATION_FAILED = -102

        //error messages
        const val ERROR_MESSAGE_NO_RESULT = "fetching from database did not return any result"
        const val ERROR_MESSAGE_UPDATE_FAILED = "Error while updating in database"
        const val ERROR_MESSAGE_READ_FAILED = "Error while reading in database"
        const val ERROR_MESSAGE_COUNT_FAILED = "Error while counting in database"
        const val ERROR_MESSAGE_INSERT_FAILED = "Error while inserting in database"
        const val ERROR_MESSAGE_DELETE_FAILED = "Error while deleting in database"

        //Constants
        const val SPLASH_MIN_DURATION_MILLIS = 1000L
    }
}