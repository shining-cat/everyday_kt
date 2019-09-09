package fr.shining_cat.everyday.repository

import fr.shining_cat.everyday.localdata.RewardDTO
import fr.shining_cat.everyday.localdata.RewardDTOConstants
import fr.shining_cat.everyday.model.Critter
import fr.shining_cat.everyday.model.RewardModel
import fr.shining_cat.everyday.model.RewardModelConstants

fun convertModelToDTO(rewardModel: RewardModel): RewardDTO{
    val level  = when (rewardModel.getLevel()) {
        Critter.Level.LEVEL_5 -> 5
        Critter.Level.LEVEL_4 -> 4
        Critter.Level.LEVEL_3 -> 3
        Critter.Level.LEVEL_2 -> 2
        Critter.Level.LEVEL_1 -> 1
    }
    val rewardDTO = RewardDTO(rewardCode = rewardModel.getCode(), rewardLevel = level)
    rewardDTO.acquisitionDate = if (rewardModel.getAcquisitionDate() != RewardModelConstants.NO_ACQUISITION_DATE) rewardModel.getAcquisitionDate() else RewardDTOConstants.NO_ACQUISITION_DATE
    rewardDTO.escapingDate = if (rewardModel.getEscapingDate() != RewardModelConstants.NO_ESCAPING_DATE) rewardModel.getEscapingDate() else RewardDTOConstants.NO_ESCAPING_DATE
    rewardDTO.isActive = rewardModel.getIsActive()
    rewardDTO.isEscaped = rewardModel.getIsEscaped()
    rewardDTO.rewardName =  if (rewardModel.getName() != RewardModelConstants.NO_NAME) rewardModel.getName() else RewardDTOConstants.NO_NAME
    rewardDTO.rewardLegsColor = rewardModel.getLegsColor()
    rewardDTO.rewardBodyColor = rewardModel.getBodyColor()
    rewardDTO.rewardArmsColor = rewardModel.getArmsColor()
    return rewardDTO
}

fun convertDTOtoModel(rewardDTO: RewardDTO): RewardModel{
    val rewardCode = rewardDTO.rewardCode
    val level  = when (rewardDTO.rewardLevel){
         5 -> Critter.Level.LEVEL_5
         4 -> Critter.Level.LEVEL_4
         3 -> Critter.Level.LEVEL_3
         2 -> Critter.Level.LEVEL_2
         1 -> Critter.Level.LEVEL_1
        else -> Critter.getLevel(rewardCode)
    }
    val rewardModel = RewardModel(code = rewardDTO.rewardCode, level = level)
    rewardModel.
    return rewardModel
}