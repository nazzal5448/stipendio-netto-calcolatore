package com.nbk.stipendionettocalcolatore.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nbk.stipendionettocalcolatore.R
import com.nbk.stipendionettocalcolatore.ui.theme.AccentOrange
import com.nbk.stipendionettocalcolatore.ui.theme.PrimaryNavy
import com.nbk.stipendionettocalcolatore.viewmodel.calculator.CalculatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel, onNavigateToResults: () -> Unit) {
    val ral by viewModel.ral.collectAsState()
    val region by viewModel.region.collectAsState()
    val municipalTax by viewModel.municipalTax.collectAsState()
    val months by viewModel.months.collectAsState()
    val contractType by viewModel.contractType.collectAsState()
    val inpsPercentage by viewModel.inpsPercentage.collectAsState()
    val hasSpouse by viewModel.hasSpouse.collectAsState()
    val childrenCount by viewModel.childrenCount.collectAsState()
    val bonus100Option by viewModel.bonus100Option.collectAsState()
    val companyWelfare by viewModel.companyWelfare.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(horizontal = 16.dp)
    ) {
        // App Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryNavy
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Section 1: Salary Core
            CompactCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(stringResource(R.string.gross_annual_salary).uppercase(), fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = ral,
                        onValueChange = { if (it.length <= 10) viewModel.updateRal(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        prefix = { Text("€", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AccentOrange)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.region).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            RegionDropdown(selectedRegion = region, onRegionSelected = { viewModel.updateRegion(it) })
                        }
                        Column(modifier = Modifier.weight(0.7f)) {
                            Text(stringResource(R.string.municipal_add).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            CompactTextField(
                                value = municipalTax,
                                onValueChange = { viewModel.updateMunicipalTax(it) },
                                suffix = "%"
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.months).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                listOf(12, 13, 14).forEach { month ->
                                    CompactChip(
                                        selected = months == month,
                                        text = month.toString(),
                                        onClick = { viewModel.updateMonths(month) }
                                    )
                                }
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.contract_type).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            ContractDropdown(selected = contractType, onSelected = { viewModel.updateContractType(it) })
                        }
                    }
                }
            }

            // Section 2: Family & Bonus
            CompactCard {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.spouse).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(if (hasSpouse) stringResource(R.string.yes) else stringResource(R.string.no), fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Switch(
                                    checked = hasSpouse,
                                    onCheckedChange = { viewModel.updateHasSpouse(it) },
                                    modifier = Modifier.scale(0.8f),
                                    colors = SwitchDefaults.colors(checkedThumbColor = AccentOrange)
                                )
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.children).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            CompactTextField(
                                value = childrenCount.toString(),
                                onValueChange = { val count = it.toIntOrNull() ?: 0; viewModel.updateChildrenCount(count) }
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.bonus_subtitle).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            BonusDropdown(selected = bonus100Option, onSelected = { viewModel.updateBonus100Option(it) })
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.welfare_label).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            CompactTextField(
                                value = companyWelfare,
                                onValueChange = { viewModel.updateCompanyWelfare(it) },
                                suffix = "€"
                            )
                        }
                    }
                }
            }

            // Advanced Trigger
            var advancedExpanded by remember { mutableStateOf(false) }
            TextButton(
                onClick = { advancedExpanded = !advancedExpanded },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    if (advancedExpanded) stringResource(R.string.hide_advanced) else stringResource(R.string.show_advanced),
                    fontSize = 12.sp, color = Color.Gray
                )
            }

            if (advancedExpanded) {
                CompactCard {
                    Column {
                        Text(stringResource(R.string.inps_percentage_label).uppercase(), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        CompactTextField(value = inpsPercentage, onValueChange = { viewModel.updateInpsPercentage(it) }, suffix = "%")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Fixed Action Button Section (Bottom)
        Surface(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            color = Color.Transparent
        ) {
            Button(
                onClick = {
                    if (ral.isNotEmpty() && ral.toDoubleOrNull() != null) {
                        viewModel.calculate()
                        onNavigateToResults()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.calculate_salary), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CompactCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    suffix: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        suffix = suffix?.let { { Text(it, fontSize = 12.sp, color = Color.Gray) } },
        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AccentOrange,
            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.3f)
        ),
        singleLine = true
    )
}

@Composable
fun CompactChip(selected: Boolean, text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.size(40.dp).clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = if (selected) PrimaryNavy else Color.White,
        border = if (!selected) BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)) else null
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text, color = if (selected) Color.White else Color.Gray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionDropdown(selectedRegion: String, onRegionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val regions = com.nbk.stipendionettocalcolatore.model.RegionData.regions.map { it.name }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedRegion,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AccentOrange, unfocusedBorderColor = Color.Transparent),
            textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            regions.forEach { regionName ->
                DropdownMenuItem(
                    text = { Text(regionName) },
                    onClick = {
                        onRegionSelected(regionName)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractDropdown(selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val optionsMap = mapOf(
        stringResource(R.string.contract_indeterminato) to "Indeterminato",
        stringResource(R.string.contract_determinato) to "Determinato",
        stringResource(R.string.contract_apprendistato) to "Apprendistato"
    )
    val displaySelected = optionsMap.entries.find { it.value == selected }?.key ?: selected

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = displaySelected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AccentOrange, unfocusedBorderColor = Color.Transparent),
            textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
            maxLines = 1,
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            optionsMap.forEach { (label, value) ->
                DropdownMenuItem(
                    text = { Text(label, fontSize = 14.sp) },
                    onClick = {
                        onSelected(value)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BonusDropdown(selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val optionsMap = mapOf(
        stringResource(R.string.bonus_automatico) to "Automatico",
        stringResource(R.string.bonus_yes) to "Forza Sì",
        stringResource(R.string.bonus_no) to "Forza No"
    )
    val displaySelected = optionsMap.entries.find { it.value == selected }?.key ?: selected

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = displaySelected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AccentOrange, unfocusedBorderColor = Color.Transparent),
            textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
            maxLines = 1,
            shape = RoundedCornerShape(8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            optionsMap.forEach { (label, value) ->
                DropdownMenuItem(
                    text = { Text(label, fontSize = 14.sp) },
                    onClick = {
                        onSelected(value)
                        expanded = false
                    }
                )
            }
        }
    }
}
