package com.nbk.stipendionettocalcolatore.viewmodel.calculator

import androidx.lifecycle.ViewModel
import com.nbk.stipendionettocalcolatore.calculator.SalaryCalculator
import com.nbk.stipendionettocalcolatore.model.CalculationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {
    private val _ral = MutableStateFlow("")
    val ral: StateFlow<String> = _ral.asStateFlow()

    private val _region = MutableStateFlow("Lombardia")
    val region: StateFlow<String> = _region.asStateFlow()

    private val _municipalTax = MutableStateFlow("0.8")
    val municipalTax: StateFlow<String> = _municipalTax.asStateFlow()

    private val _months = MutableStateFlow(12)
    val months: StateFlow<Int> = _months.asStateFlow()

    private val _contractType = MutableStateFlow("Indeterminato")
    val contractType: StateFlow<String> = _contractType.asStateFlow()

    private val _inpsPercentage = MutableStateFlow("9.19")
    val inpsPercentage: StateFlow<String> = _inpsPercentage.asStateFlow()

    private val _hasSpouse = MutableStateFlow(false)
    val hasSpouse: StateFlow<Boolean> = _hasSpouse.asStateFlow()

    private val _childrenCount = MutableStateFlow(0)
    val childrenCount: StateFlow<Int> = _childrenCount.asStateFlow()

    private val _bonus100Option = MutableStateFlow("Automatico")
    val bonus100Option: StateFlow<String> = _bonus100Option.asStateFlow()

    private val _companyWelfare = MutableStateFlow("")
    val companyWelfare: StateFlow<String> = _companyWelfare.asStateFlow()

    private val _result = MutableStateFlow<CalculationResult?>(null)
    val result: StateFlow<CalculationResult?> = _result.asStateFlow()

    // History state
    private val _history = MutableStateFlow<List<CalculationResult>>(emptyList())
    val history: StateFlow<List<CalculationResult>> = _history.asStateFlow()

    fun updateRal(value: String) { _ral.value = value }
    fun updateRegion(value: String) { _region.value = value }
    fun updateMunicipalTax(value: String) { _municipalTax.value = value }
    fun updateMonths(value: Int) { _months.value = value }
    fun updateContractType(value: String) { _contractType.value = value }
    fun updateInpsPercentage(value: String) { _inpsPercentage.value = value }
    fun updateHasSpouse(value: Boolean) { _hasSpouse.value = value }
    fun updateChildrenCount(value: Int) { _childrenCount.value = value }
    fun updateBonus100Option(value: String) { _bonus100Option.value = value }
    fun updateCompanyWelfare(value: String) { _companyWelfare.value = value }

    fun calculate() {
        val ralValue = ral.value.toDoubleOrNull() ?: 0.0
        val municipalTaxValue = municipalTax.value.toDoubleOrNull() ?: 0.0
        val inpsValue = inpsPercentage.value.toDoubleOrNull() ?: 9.19
        val welfareValue = companyWelfare.value.toDoubleOrNull() ?: 0.0

        val calcResult = SalaryCalculator.calculateSalary(
            ral = ralValue,
            regionName = region.value,
            municipalPercentage = municipalTaxValue,
            months = months.value,
            contractType = contractType.value,
            inpsPercentage = inpsValue,
            hasSpouse = hasSpouse.value,
            childrenCount = childrenCount.value,
            bonus100Option = bonus100Option.value,
            companyWelfare = welfareValue
        )
        _result.value = calcResult
        
        // Save to temporary history (for now, will implement persistence later)
        _history.value = listOf(calcResult) + _history.value
    }
}
