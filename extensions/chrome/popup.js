// UI Logic for Stipendio Netto Extension

document.addEventListener('DOMContentLoaded', () => {
    const regionSelect = document.getElementById('region');
    const ralInput = document.getElementById('ral');
    const monthsSelect = document.getElementById('months');
    const calculateBtn = document.getElementById('calculateBtn');
    const resultsSection = document.getElementById('results');

    // Advanced inputs
    const municipalTaxInput = document.getElementById('municipalTax');
    const inpsRateInput = document.getElementById('inpsRate');
    const hasSpouseCheck = document.getElementById('hasSpouse');
    const childrenCountInput = document.getElementById('childrenCount');
    const bonusOptionSelect = document.getElementById('bonusOption');
    const welfareInput = document.getElementById('welfare');

    // Result display elements
    const monthlyNetText = document.getElementById('monthlyNet');
    const annualNetText = document.getElementById('annualNet');
    const inpsTotalText = document.getElementById('inpsTotal');
    const irpefTotalText = document.getElementById('irpefTotal');
    const taxesTotalText = document.getElementById('taxesTotal');

    // Populate regions from calculator.js (RegionData is global there)
    RegionData.regions.forEach(region => {
        const option = document.createElement('option');
        option.value = region.name;
        option.textContent = region.name;
        if (region.name === 'Lombardia') option.selected = true;
        regionSelect.appendChild(option);
    });

    calculateBtn.addEventListener('click', () => {
        const ral = parseFloat(ralInput.value);
        if (isNaN(ral) || ral <= 0) {
            alert('Inserisci un RAL valido');
            return;
        }

        const params = {
            ral: ral,
            regionName: regionSelect.value,
            municipalPercentage: parseFloat(municipalTaxInput.value) || 0,
            months: parseInt(monthsSelect.value),
            contractType: 'Indeterminato', // Default
            inpsPercentage: parseFloat(inpsRateInput.value) || 9.19,
            hasSpouse: hasSpouseCheck.checked,
            childrenCount: parseInt(childrenCountInput.value) || 0,
            bonus100Option: bonusOptionSelect.value,
            companyWelfare: parseFloat(welfareInput.value) || 0
        };

        const result = calculateSalary(params);
        displayResults(result);
    });

    function displayResults(result) {
        resultsSection.classList.remove('hidden');
        
        monthlyNetText.textContent = formatCurrency(result.netMonthly);
        annualNetText.textContent = formatCurrency(result.netAnnual);
        inpsTotalText.textContent = formatCurrency(result.inps);
        irpefTotalText.textContent = formatCurrency(result.irpef);
        
        const otherTaxes = result.regionalTax + result.municipalTax;
        taxesTotalText.textContent = formatCurrency(otherTaxes);

        // Smooth scroll to results
        resultsSection.scrollIntoView({ behavior: 'smooth' });
    }

    function formatCurrency(value) {
        return new Intl.NumberFormat('it-IT', {
            style: 'currency',
            currency: 'EUR'
        }).format(value);
    }
});
