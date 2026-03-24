package com.nbk.stipendionettocalcolatore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun HistoryScreen(viewModel: CalculatorViewModel, onNavigateToDetails: () -> Unit) {
    val history by viewModel.history.collectAsState()
    val formatter = NumberFormat.getCurrencyInstance(Locale.ITALY)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.history), fontWeight = FontWeight.Bold, color = PrimaryNavy) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        if (history.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.no_history), color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(history) { result ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("RAL", fontSize = 12.sp, color = Color.Gray)
                                    Text(formatter.format(result.grossAnnual), fontWeight = FontWeight.Bold, color = PrimaryNavy)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(stringResource(R.string.net_monthly_label), fontSize = 12.sp, color = Color.Gray)
                                    Text(formatter.format(result.netMonthly), fontWeight = FontWeight.Bold, color = AccentOrange)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(stringResource(R.string.months) + ": ${result.months}", fontSize = 12.sp, color = Color.Gray)
                                Text(stringResource(R.string.net_annual_label) + ": ${formatter.format(result.netAnnual)}", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
