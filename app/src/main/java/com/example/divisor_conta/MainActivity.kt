package com.example.divisor_conta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
fun FieldText(labelText : String, value: String,
              onValueChange: (String) -> Unit,
              action: ImeAction,
              modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = action),
        modifier = modifier
    )
}

@Composable
fun DivisorLayout(modifier: Modifier = Modifier) {
    var quant by remember { mutableStateOf("") }
    var contaInput by remember { mutableStateOf("") }
    Column {
        FieldText(
            labelText = "Quantidade de pessoas",
            value = quant,
            onValueChange = { newValue -> quant = newValue },
            action = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(16.dp))
        FieldText(
            labelText = "Conta",
            value = contaInput,
            onValueChange = { newValue -> contaInput = newValue },
            action = ImeAction.Done
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DivisorPreview() {
    Divisor_ContaTheme {
        DivisorLayout()
    }
}