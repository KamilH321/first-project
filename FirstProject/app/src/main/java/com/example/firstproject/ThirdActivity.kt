package com.example.firstproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstproject.ui.theme.FirstProjectTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultText = getString(R.string.title_third_screen)
        val originalText: String? = intent.getStringExtra("text")
        val displayText = originalText ?: defaultText

        enableEdgeToEdge()
        setContent {
            FirstProjectTheme {
                ThirdScreen(displayText)
            }
        }
    }
}

@Composable
fun ThirdScreen(displayText: String){

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.height(5.dp))

        Text(displayText)

        Spacer(modifier = Modifier.height(5.dp))

        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                context.startActivity(intent)
            }
        ){
            Text(stringResource(R.string.btn_open_screen1))
        }
    }
}

@Preview(showBackground = true,
    name = "Pixel 7 • Dark",
    showSystemUi = true,
    device = Devices.PIXEL_7)

@Composable
fun GreetingPreview3() {
    FirstProjectTheme {
        ThirdScreen("Экран 3")
    }
}