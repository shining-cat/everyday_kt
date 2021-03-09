package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.repo.SessionPresetRepository

class CreateSessionPresetUseCase(
    private val sessionPresetRepository: SessionPresetRepository,
    private val logger: Logger
) {

    suspend fun execute(sessionPreset: SessionPreset): Result<Array<Long>> {
        val output = sessionPresetRepository.insert(listOf(sessionPreset))
        return if (output is Output.Success) {
            Result.Success(output.result)
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