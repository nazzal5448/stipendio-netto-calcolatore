package com.nbk.stipendionettocalcolatore.model

data class Region(val name: String, val taxRate: Double)

object RegionData {
    val regions = listOf(
        Region("Abruzzo", 0.0173),
        Region("Basilicata", 0.0123),
        Region("Calabria", 0.0173),
        Region("Campania", 0.0203),
        Region("Emilia-Romagna", 0.0133),
        Region("Friuli-Venezia Giulia", 0.0123),
        Region("Lazio", 0.0173),
        Region("Liguria", 0.0123),
        Region("Lombardia", 0.0123),
        Region("Marche", 0.0123),
        Region("Molise", 0.0203),
        Region("Piemonte", 0.0123),
        Region("Puglia", 0.0133),
        Region("Sardegna", 0.0123),
        Region("Sicilia", 0.0123),
        Region("Toscana", 0.0142),
        Region("Trentino-Alto Adige", 0.0123),
        Region("Umbria", 0.0123),
        Region("Valle d'Aosta", 0.0123),
        Region("Veneto", 0.0123)
    )
}
