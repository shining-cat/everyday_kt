package fr.shining_cat.everyday.domain.sessionspresets

import fr.shining_cat.everyday.commons.Logger
import fr.shining_cat.everyday.domain.Result
import fr.shining_cat.everyday.models.SessionPreset
import fr.shining_cat.everyday.repository.Output
import fr.shining_cat.everyday.repository.repo.SessionPresetRepository

class LoadSessionPresetsUseCase(
    private val sessionPresetRepository: SessionPresetRepository,
    private val logger: Logger
) {

    suspend fun execute(): Result<List<SessionPreset>> {
        val output = sessionPresetRepository.getAllSessionPresetsLastEditTimeDesc()
        return if (output is Output.Success) {
            Result.Success(output.result)
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