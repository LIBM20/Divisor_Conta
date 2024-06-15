package com.example.divisor_conta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
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
fun GorjetaSwitch(texto: String, gorjeta: Boolean,
                  onGorjetaChanged: (Boolean) -> Unit,
                  modifier: Modifier = Modifier) {
    Row (modifier = modifier
        .fillMaxWidth()
        .padding(end = 26.dp, start = 26.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(text = texto)
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = gorjeta,
            onCheckedChange = onGorjetaChanged,
        )
    }
}

@Composable
fun DivisorLayout(modifier: Modifier = Modifier) {
    var quant by remember { mutableStateOf("") }
    var contaInput by remember { mutableStateOf("") }
    var gorjeta by remember { mutableStateOf(false) }


    Column {
        FieldText(
            labelText = stringResource(R.string.quantidade_de_pessoas),
            value = quant,
            onValueChange = { newValue -> quant = newValue },
            action = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(16.dp))
        FieldText(
            labelText = stringResource(R.string.conta),
            value = contaInput,
            onValueChange = { newValue -> contaInput = newValue },
            action = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(20.dp))
        GorjetaSwitch(
            texto = stringResource(R.string.pretende_dar_gorjeta),
            gorjeta = gorjeta,
            onGorjetaChanged = { newValue -> gorjeta = newValue },
            modifier = Modifier
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