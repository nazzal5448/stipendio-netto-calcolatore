package com.nbk.stipendionettocalcolatore.model

data class CalculationResult(
    val grossAnnual: Double,
    val netAnnual: Double,
    val netMonthly: Double,
    val inps: Double,
    val irpef: Double,
    val regionalTax: Double,
    val municipalTax: Double,
    val months: Int
)
