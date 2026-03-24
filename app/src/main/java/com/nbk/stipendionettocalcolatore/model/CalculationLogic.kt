package com.nbk.stipendionettocalcolatore.model


object CalculationLogic {
    fun calculateSalary(
        ral: Double,
        region: String,
        municipalPercentage: Double,
        months: Int,
        isApprenticeship: Boolean,
        inpsPercentage: Double,
        hasSpouse: Boolean,
        childrenCount: Int,
        bonus100: Boolean
    ): CalculationResult {
        val inps = ral * (inpsPercentage / 100.0)
        val taxableIncome = ral - inps
        
        var irpef = 0.0
        if (taxableIncome <= 28000) {
            irpef = taxableIncome * 0.23
        } else if (taxableIncome <= 50000) {
            irpef = (28000 * 0.23) + ((taxableIncome - 28000) * 0.33)
        } else {
            irpef = (28000 * 0.23) + (22000 * 0.33) + ((taxableIncome - 50000) * 0.43)
        }
        
        val regionalTax = taxableIncome * 0.012 // Simplified
        val municipalTax = taxableIncome * (municipalPercentage / 100.0)
        
        var deductions = 0.0
        // Simplified deductions
        
        var bonuses = 0.0
        if (bonus100 && taxableIncome <= 28000) {
            bonuses = 1200.0 // 100 * 12
        }
        
        val netAnnual = ral - inps - irpef - regionalTax - municipalTax + bonuses
        val netMonthly = netAnnual / months
        
        return CalculationResult(
            grossAnnual = ral,
            netAnnual = netAnnual,
            netMonthly = netMonthly,
            inps = inps,
            irpef = irpef,
            regionalTax = regionalTax,
            municipalTax = municipalTax,
            months = months
        )
    }
}
