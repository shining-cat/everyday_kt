package fr.shining_cat.everyday.commons

class Constants {


    companion object {
        // Error codes
        const val ERROR_CODE_EXCEPTION = -100
        const val ERROR_CODE_DTO_NULL = -101
        const val ERROR_CODE_ENTITY_NULL = -102
        const val ERROR_CODE_DATABASE_OPERATION_FAILED = -102
        const val ERROR_CODE_DATABASE = -103

        //error messages
        const val ERROR_MESSAGE_DTO_NULL = "fetching from database did not return any result"
        const val ERROR_MESSAGE_UPDATE_FAILED = "Error while updating in database"
        const val ERROR_MESSAGE_INSERT_FAILED = "Error while inserting in database"

    }
}