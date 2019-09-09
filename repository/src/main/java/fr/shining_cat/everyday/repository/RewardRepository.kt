package fr.shining_cat.everyday.repository

import androidx.lifecycle.LiveData
import fr.shining_cat.everyday.localdata.EveryDayRoomDatabase
import fr.shining_cat.everyday.model.RewardModel
import fr.shining_cat.everyday.repository.converter.RewardConverter.Companion.convertDTOsToModels

class RewardRepository(database: EveryDayRoomDatabase) {

    val rewardDao = database.rewardDao()
    val rewardsActiveAcquisitionDateAsc: LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsAcquisitionDateAsc())
    val rewardsActiveAcquisitionDateDesc: LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsActiveAcquisitionDateDesc())
    val rewardsActiveLevelAsc: LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsActiveLevelAsc())
    val rewardsActiveLevelDesc: LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsActiveLevelDesc())
    val rewardsNotEscapedAcquisitionDateDesc: LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsNotEscapedAcquisitionDatDesc())
    val rewardsEscapedAcquisitionDateDesc: LiveData<List<RewardModel>> = convertDTOsToModels(rewardDao.getAllRewardsEscapedAcquisitionDateDesc())

}