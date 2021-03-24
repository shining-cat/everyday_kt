package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_DATABASE_OPERATION_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_UPDATE_FAILED
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.repo.SessionPresetRepository

class UpdateSessionPresetUseCase(
    private val sessionPresetRepository: SessionPresetRepository,
    private val logger: Logger
) {

    suspend fun execute(
        sessionPreset: SessionPreset
    ): Result<Int> {
        val output = sessionPresetRepository.update(sessionPreset.copy(lastEditTime = System.currentTimeMillis()))
        return if (output is Output.Success) {
            // this usecase only handle single item deletion
            if (output.result == 1) {
                Result.Success(output.result)
            } else {
                Result.Error(
                    ERROR_CODE_DATABASE_OPERATION_FAILED,
                    ERROR_MESSAGE_UPDATE_FAILED,
                    Exception(ERROR_MESSAGE_UPDATE_FAILED)
                )
            }
        } else {
            output as Output.Error
            Result.Error(
                output.errorCode,
                output.errorResponse,
                output.exception
            )
        }
    }
}