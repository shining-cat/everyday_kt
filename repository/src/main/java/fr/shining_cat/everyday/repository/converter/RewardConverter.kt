package fr.shining_cat.everyday.repository.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fr.shining_cat.everyday.localdata.dto.RewardDTO
import fr.shining_cat.everyday.localdata.dto.RewardDTOConstants
import fr.shining_cat.everyday.model.Critter
import fr.shining_cat.everyday.model.RewardModel
import fr.shining_cat.everyday.model.RewardModelConstants

class RewardConverter {
    companion object{

        fun convertModelsToDTOs(rewardModels: List<RewardModel>): List<RewardDTO> {
            return rewardModels.map {rewardModel ->  convertModelToDTO(rewardModel)}
        }

        fun convertModelToDTO(rewardModel: RewardModel): RewardDTO {
            val level  = when (rewardModel.level) {
                Critter.Level.LEVEL_5 -> 5
                Critter.Level.LEVEL_4 -> 4
                Critter.Level.LEVEL_3 -> 3
                Critter.Level.LEVEL_2 -> 2
                Critter.Level.LEVEL_1 -> 1
            }
            val rewardDTO = RewardDTO(
                code = rewardModel.code,
                level = level
            )
            if(rewardModel.id != -1L){
                rewardDTO.id = rewardModel.id
            }
            rewardDTO.acquisitionDate = if (rewardModel.acquisitionDate == RewardModelConstants.NO_ACQUISITION_DATE) RewardDTOConstants.NO_ACQUISITION_DATE else rewardModel.acquisitionDate
            rewardDTO.escapingDate = if (rewardModel.escapingDate == RewardModelConstants.NO_ESCAPING_DATE) RewardDTOConstants.NO_ESCAPING_DATE else rewardModel.escapingDate
            rewardDTO.name =  if (rewardModel.name == RewardModelConstants.NO_NAME) RewardDTOConstants.NO_NAME else rewardModel.name
            rewardDTO.isActive = rewardModel.isActive
            rewardDTO.isEscaped = rewardModel.isEscaped
            rewardDTO.legsColor = rewardModel.legsColor
            rewardDTO.bodyColor = rewardModel.bodyColor
            rewardDTO.armsColor = rewardModel.armsColor
            return rewardDTO
        }

        fun convertDTOsToModels(rewardDTOs: LiveData<List<RewardDTO>>): LiveData<List<RewardModel>> {
            return Transformations.map(rewardDTOs){it.map { rewardDTO ->  convertDTOtoModel(rewardDTO)}}
        }

        fun convertDTOtoModel(rewardDTO: LiveData<RewardDTO>): LiveData<RewardModel> {
            return Transformations.map(rewardDTO){it ->  convertDTOtoModel(it)}
        }

        fun convertDTOtoModel(rewardDTO: RewardDTO): RewardModel{
            val rewardCode = rewardDTO.code
            val level  = when (rewardDTO.level){
                5 -> Critter.Level.LEVEL_5
                4 -> Critter.Level.LEVEL_4
                3 -> Critter.Level.LEVEL_3
                2 -> Critter.Level.LEVEL_2
                1 -> Critter.Level.LEVEL_1
                else -> Critter.getLevel(rewardCode)
            }
            val rewardModel = RewardModel(id= rewardDTO.id, code = rewardDTO.code, level = level)
            rewardModel.acquisitionDate = if (rewardDTO.acquisitionDate == RewardDTOConstants.NO_ACQUISITION_DATE) RewardModelConstants.NO_ACQUISITION_DATE else rewardDTO.acquisitionDate
            rewardModel.escapingDate = if (rewardDTO.escapingDate == RewardDTOConstants.NO_ESCAPING_DATE) RewardModelConstants.NO_ESCAPING_DATE else rewardDTO.escapingDate
            rewardModel.name = if (rewardDTO.name == RewardDTOConstants.NO_NAME) RewardModelConstants.NO_NAME else rewardDTO.name
            rewardModel.isActive = rewardDTO.isActive
            rewardModel.isEscaped = rewardDTO.isEscaped
            rewardModel.legsColor = rewardDTO.legsColor
            rewardModel.bodyColor = rewardDTO.bodyColor
            rewardModel.armsColor = rewardDTO.armsColor
            return rewardModel

        }
    }
}