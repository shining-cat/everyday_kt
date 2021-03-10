package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Constants
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.repo.SessionPresetRepository

class DeleteSessionPresetUseCase(
    private val sessionPresetRepository: SessionPresetRepository,
    private val logger: Logger
) {

    suspend fun execute(sessionPreset: SessionPreset): Result<Int> {
        val output = sessionPresetRepository.delete(sessionPreset)
        return if (output is Output.Success) {
            //this usecase only handle single item deletion
            if (output.result == 1) {
                Result.Success(output.result)
            }
            else {
                Result.Error(
                    Constants.ERROR_CODE_DATABASE_OPERATION_FAILED,
                    Constants.ERROR_MESSAGE_DELETE_FAILED,
                    Exception(Constants.ERROR_MESSAGE_DELETE_FAILED)
                )
            }
        }
        else {
            output as Output.Error
            Result.Error(
                output.errorCode,
                output.errorResponse,
                output.exception
            )
        }
    }
}