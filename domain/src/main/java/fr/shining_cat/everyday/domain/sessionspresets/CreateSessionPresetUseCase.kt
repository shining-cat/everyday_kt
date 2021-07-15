package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_CODE_DATABASE_OPERATION_FAILED
import fr.shining_cat.everyday.commons.Constants.Companion.ERROR_MESSAGE_INSERT_FAILED
import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.repo.SessionPresetRepository

class CreateSessionPresetUseCase(
    private val sessionPresetRepository: SessionPresetRepository,
    private val logger: Logger
) {

    private val LOG_TAG = CreateSessionPresetUseCase::class.java.name

    suspend fun execute(
        sessionPreset: SessionPreset
    ): Result<Long> {

        val sessionPresetToInsert = when (sessionPreset) {
            is SessionPreset.AudioSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())
            is SessionPreset.AudioFreeSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())
            is SessionPreset.TimedSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())
            is SessionPreset.TimedFreeSessionPreset -> sessionPreset.copy(lastEditTime = System.currentTimeMillis())
            else -> SessionPreset.UnknownSessionPreset()
        }
        val output = sessionPresetRepository.insert(listOf(sessionPresetToInsert))
        return if (output is Output.Success) {
            // this usecase only handles single item insertion
            if (output.result.size == 1) {
                Result.Success(output.result[0])
            } else {
                Result.Error(
                    ERROR_CODE_DATABASE_OPERATION_FAILED,
                    ERROR_MESSAGE_INSERT_FAILED,
                    Exception(ERROR_MESSAGE_INSERT_FAILED)
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