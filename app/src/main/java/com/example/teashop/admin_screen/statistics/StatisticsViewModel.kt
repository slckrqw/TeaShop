package com.example.teashop.admin_screen.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.enums.StatisticsSortType
import com.example.teashop.data.model.statistics.Statistics
import com.example.teashop.data.repository.AccountingRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class StatisticsViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _stats = MutableLiveData<Statistics>()

    val stats: LiveData<Statistics>
        get() = _stats

    fun getStatisticsByPeriod(
        token: String,
        sortType: StatisticsSortType,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = AccountingRepository().getStatisticsByPeriod("Bearer $token", sortType)
            if (response.isSuccessful) {
                response.body()?.let {
                    _stats.value = it
                }
            } else {
                onError()
            }
        }
    }
}