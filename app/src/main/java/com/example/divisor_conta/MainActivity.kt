package com.example.divisor_conta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.divisor_conta.ui.theme.Divisor_ContaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Divisor_ContaTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp, bottom = 48.dp)) { innerPadding -> DivisorLayout(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun DivisorLayout(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun DivisorPreview() {
    Divisor_ContaTheme {
        DivisorLayout()
    }
}