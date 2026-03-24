package com.nbk.stipendionettocalcolatore.calculator

import com.nbk.stipendionettocalcolatore.model.CalculationResult
import com.nbk.stipendionettocalcolatore.model.RegionData

object SalaryCalculator {
    fun calculateSalary(
        ral: Double,
        regionName: String,
        municipalPercentage: Double,
        months: Int,
        contractType: String,
        inpsPercentage: Double,
        hasSpouse: Boolean,
        childrenCount: Int,
        bonus100Option: String, // "Automatico", "Forza Sì", "Forza No"
        companyWelfare: Double 
    ): CalculationResult {
        // 1. INPS Calculation
        val inps = ral * (inpsPercentage / 100.0)
        
        // 2. Taxable Income
        val taxableIncome = ral - inps
        
        // 3. IRPEF Calculation
        var irpef = 0.0
        if (taxableIncome <= 28000) {
            irpef = taxableIncome * 0.23
        } else if (taxableIncome <= 50000) {
            irpef = (28000 * 0.23) + ((taxableIncome - 28000) * 0.33)
        } else {
            irpef = (28000 * 0.23) + (22000 * 0.33) + ((taxableIncome - 50000) * 0.43)
        }
        
        // 4. Regional and Municipal Tax
        val regionRate = RegionData.regions.find { it.name == regionName }?.taxRate ?: 0.0123
        val regionalTax = taxableIncome * regionRate
        val municipalTax = taxableIncome * (municipalPercentage / 100.0)
        
        // 5. Bonus 100 (Trattamento Integrativo / Ex-Bonus Renzi)
        // Usually 1200 per year if taxableIncome <= 15000, 
        // or up to 28000 if certain conditions are met (not fully specified, so we'll use a simplified version)
        var bonus100Amount = 0.0
        when (bonus100Option) {
            "Forza Sì" -> bonus100Amount = 1200.0
            "Forza No" -> bonus100Amount = 0.0
            "Automatico" -> {
                if (taxableIncome <= 15000) {
                    bonus100Amount = 1200.0
                } else if (taxableIncome <= 28000) {
                    // Simplified: partial bonus or specific conditions. 
                    // Let's assume full for simplicity if it matches website's basic behavior for now
                    bonus100Amount = 1200.0 
                }
            }
        }
        
        // 6. Net Salary Calculation
        // User spec: net_salary = RAL - INPS - IRPEF - regional_tax - municipal_tax - bonuses
        // We interpret "bonuses" as the subtracted value (maybe for welfare cost) 
        // but we ADD the "Bonus 100" as it's an income supplement.
        val netAnnual = ral - inps - irpef - regionalTax - municipalTax - companyWelfare + bonus100Amount
        val netMonthly = netAnnual / months
        
        return CalculationResult(
            grossAnnual = Math.round(ral * 100.0) / 100.0,
            netAnnual = Math.round(netAnnual * 100.0) / 100.0,
            netMonthly = Math.round(netMonthly * 100.0) / 100.0,
            inps = Math.round(inps * 100.0) / 100.0,
            irpef = Math.round(irpef * 100.0) / 100.0,
            regionalTax = Math.round(regionalTax * 100.0) / 100.0,
            municipalTax = Math.round(municipalTax * 100.0) / 100.0,
            months = months
        )
    }
}
