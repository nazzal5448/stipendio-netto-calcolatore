package com.nbk.stipendionettocalcolatore.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nbk.stipendionettocalcolatore.R
import com.nbk.stipendionettocalcolatore.ui.theme.PrimaryNavy

@Composable
fun SettingsScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryNavy,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                val currentLocale = AppCompatDelegate.getApplicationLocales().get(0)?.language
                    ?: java.util.Locale.getDefault().language
                ListItem(
                    headlineContent = { Text(stringResource(R.string.language), fontWeight = FontWeight.Medium) },
                    trailingContent = { 
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("IT", color = if (currentLocale == "it") PrimaryNavy else Color.Gray, fontWeight = if (currentLocale == "it") FontWeight.Bold else FontWeight.Normal)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("/", color = Color.Gray)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("EN", color = if (currentLocale == "en") PrimaryNavy else Color.Gray, fontWeight = if (currentLocale == "en") FontWeight.Bold else FontWeight.Normal)
                        }
                    },
                    modifier = Modifier.clickable { 
                        val nextLocale = if (currentLocale == "it") "en" else "it"
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(nextLocale))
                    }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray.copy(alpha = 0.5f))
                ListItem(
                    headlineContent = { Text(stringResource(R.string.privacy_policy), fontWeight = FontWeight.Medium) },
                    modifier = Modifier.clickable {
                        val url = "https://stipendionettocalcolatore.it/privacy-policy/"
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(context, Uri.parse(url))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().shadow(2.dp, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.legal_disclaimer).uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(R.string.disclaimer_text),
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
