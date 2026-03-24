package com.nbk.stipendionettocalcolatore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nbk.stipendionettocalcolatore.R
import com.nbk.stipendionettocalcolatore.ui.theme.AccentOrange
import com.nbk.stipendionettocalcolatore.ui.theme.PrimaryNavy
import com.nbk.stipendionettocalcolatore.viewmodel.calculator.CalculatorViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(viewModel: CalculatorViewModel, onBack: () -> Unit) {
    val result by viewModel.result.collectAsState()
    val formatter = NumberFormat.getCurrencyInstance(Locale.ITALY)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.results_title), fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryNavy)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        if (result == null) return@Scaffold

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Annual Net
            Card(
                modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = PrimaryNavy),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(stringResource(R.string.annual_net_salary).uppercase(), color = Color.LightGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text("€ ", color = AccentOrange, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(String.format("%,.2f", result!!.netAnnual), color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Monthly Net
            Card(
                modifier = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(stringResource(R.string.monthly_net).uppercase(), color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(formatter.format(result!!.netMonthly), color = PrimaryNavy, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(R.string.calculated_on_months, result!!.months), color = Color.Gray, fontSize = 12.sp)
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // INPS
                Card(
                    modifier = Modifier.weight(1f).shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.inps_contributions).uppercase(), color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(formatter.format(result!!.inps), color = PrimaryNavy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // IRPEF
                Card(
                    modifier = Modifier.weight(1f).shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.gross_irpef).uppercase(), color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(formatter.format(result!!.irpef), color = PrimaryNavy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Regional
                Card(
                    modifier = Modifier.weight(1f).shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.regional_add).uppercase(), color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(formatter.format(result!!.regionalTax), color = PrimaryNavy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Municipal
                Card(
                    modifier = Modifier.weight(1f).shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.municipal_add).uppercase(), color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(formatter.format(result!!.municipalTax), color = PrimaryNavy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Bonus 100 Row (only if it was applied)
            // Note: We need to check if bonus was applied. For now let's just show it if it exists in a logic that results in net increase
            // In a real app we'd pass this value more explicitly.
        }
    }
}
