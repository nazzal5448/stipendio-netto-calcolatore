// Salary Calculator Logic for Italy (Stipendio Netto Calcolatore)
// Ported from Android App to JavaScript for Browser Extensions (2026 Manifest V3 compliant)

const RegionData = {
    regions: [
        { name: "Abruzzo", taxRate: 0.0173 },
        { name: "Basilicata", taxRate: 0.0123 },
        { name: "Calabria", taxRate: 0.0173 },
        { name: "Campania", taxRate: 0.0203 },
        { name: "Emilia-Romagna", taxRate: 0.0133 },
        { name: "Friuli-Venezia Giulia", taxRate: 0.0123 },
        { name: "Lazio", taxRate: 0.0173 },
        { name: "Liguria", taxRate: 0.0123 },
        { name: "Lombardia", taxRate: 0.0123 },
        { name: "Marche", taxRate: 0.0123 },
        { name: "Molise", taxRate: 0.0203 },
        { name: "Piemonte", taxRate: 0.0123 },
        { name: "Puglia", taxRate: 0.0133 },
        { name: "Sardegna", taxRate: 0.0123 },
        { name: "Sicilia", taxRate: 0.0123 },
        { name: "Toscana", taxRate: 0.0142 },
        { name: "Trentino-Alto Adige", taxRate: 0.0123 },
        { name: "Umbria", taxRate: 0.0123 },
        { name: "Valle d'Aosta", taxRate: 0.0123 },
        { name: "Veneto", taxRate: 0.0123 }
    ]
};

function calculateSalary(params) {
    const {
        ral,
        regionName,
        municipalPercentage,
        months,
        contractType,
        inpsPercentage,
        hasSpouse,
        childrenCount,
        bonus100Option, // "Automatico", "Forza Sì", "Forza No"
        companyWelfare
    } = params;

    // 1. INPS Calculation
    const inps = ral * (inpsPercentage / 100.0);
    
    // 2. Taxable Income
    const taxableIncome = ral - inps;
    
    // 3. IRPEF Calculation
    let irpef = 0.0;
    if (taxableIncome <= 28000) {
        irpef = taxableIncome * 0.23;
    } else if (taxableIncome <= 50000) {
        irpef = (28000 * 0.23) + ((taxableIncome - 28000) * 0.33);
    } else {
        irpef = (28000 * 0.23) + (22000 * 0.33) + ((taxableIncome - 50000) * 0.43);
    }
    
    // 4. Regional and Municipal Tax
    const region = RegionData.regions.find(r => r.name === regionName) || { taxRate: 0.0123 };
    const regionalTax = taxableIncome * region.taxRate;
    const municipalTax = taxableIncome * (municipalPercentage / 100.0);
    
    // 5. Bonus 100 (Simplified supplement)
    let bonus100Amount = 0.0;
    if (bonus100Option === "Forza Sì") {
        bonus100Amount = 1200.0;
    } else if (bonus100Option === "Forza No") {
        bonus100Amount = 0.0;
    } else if (bonus100Option === "Automatico") {
        if (taxableIncome <= 15000) {
            bonus100Amount = 1200.0;
        } else if (taxableIncome <= 28000) {
            bonus100Amount = 1200.0;
        }
    }
    
    // 6. Net Salary Calculation
    const netAnnual = ral - inps - irpef - regionalTax - municipalTax - companyWelfare + bonus100Amount;
    const netMonthly = netAnnual / months;
    
    return {
        grossAnnual: Math.round(ral * 100.0) / 100.0,
        netAnnual: Math.round(netAnnual * 100.0) / 100.0,
        netMonthly: Math.round(netMonthly * 100.0) / 100.0,
        inps: Math.round(inps * 100.0) / 100.0,
        irpef: Math.round(irpef * 100.0) / 100.0,
        regionalTax: Math.round(regionalTax * 100.0) / 100.0,
        municipalTax: Math.round(municipalTax * 100.0) / 100.0,
        months: months
    };
}
