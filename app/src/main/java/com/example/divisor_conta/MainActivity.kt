package com.example.divisor_conta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.divisor_conta.ui.theme.Divisor_ContaTheme
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Divisor_ContaTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier
                    .fillMaxSize()) { innerPadding -> DivisorLayout(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize())
                    //.padding(top = 16.dp, bottom = 16.dp)
                }
            }
        }
    }
}

@Composable
fun ButtonImage(
    drawableResourceId: Int,
    contentDescription: String,
    onImageClick: () -> Unit,
) {
        Column(modifier = Modifier
                .fillMaxSize()
               , horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
                Button(
                onClick = onImageClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white))
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = contentDescription,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Divisor de Contas", style  = MaterialTheme.typography.displaySmall)
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
        colors = TextFieldDefaults.colors(unfocusedContainerColor = colorResource(id = R.color.teal_dark), unfocusedLabelColor = colorResource(
            id = R.color.black
        ), unfocusedIndicatorColor = colorResource(
            id = R.color.teal_dark), focusedContainerColor = colorResource(
            id = R.color.grey_dark), focusedLabelColor = colorResource(id = R.color.white), focusedIndicatorColor = colorResource(id = R.color.teal_light), focusedTextColor = colorResource(
            id = R.color.white
        )),
        modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
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
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorResource(R.color.teal_dark),
                checkedTrackColor = colorResource(R.color.grey_dark),
                uncheckedThumbColor = colorResource(R.color.grey_dark),
                uncheckedTrackColor = colorResource(R.color.teal_dark),
                uncheckedBorderColor = colorResource(id = R.color.teal_light),
                checkedBorderColor = colorResource(id = R.color.grey_dark)
            )
        )
    }
}

@Composable
fun calcConta(pessoas: Int, conta: Double, percentGorjeta: Double): String {
    val gorjeta = percentGorjeta / 100 * conta
    val total = (conta + gorjeta)/pessoas

    // Formato de moeda para Euro (€) usando o Locale para Português
    val euroFormat = NumberFormat.getCurrencyInstance(Locale("pt"))
    euroFormat.currency = Currency.getInstance("EUR")

    return euroFormat.format(total)
}

@Composable
fun DivisorLayout(modifier: Modifier = Modifier) {
    // TextFields iniciais
    var quantInput by remember { mutableStateOf("") }
    var contaInput by remember { mutableStateOf("") }

    // Conversão dos inputs nos textfields
    val quant = quantInput.toIntOrNull() ?: 1
    val conta = contaInput.toDoubleOrNull() ?: 0.0

    // Switches
    var gorjeta by remember { mutableStateOf(false) }
    var gIndividual by remember { mutableStateOf(false) }

    // Lista para guardar as gorjetas individuais
    val percentGorjetaInput = remember { mutableStateListOf<String>() }
    var percentGorjeta = 0.0

    var total: String

    var currentStep by remember { mutableIntStateOf(1) }

    // Caso não seja inserida um valor de conta o switch de gorjeta não funcionará
    // Como a quantidade de pessoas tem como valor default 1, não haverá problema se o utilizador não inserir nenhum valor
    if (conta == 0.0) {
        gorjeta = false
    }

    when (currentStep) {
        1 -> {
            ButtonImage(
                drawableResourceId = R.drawable._011863,
                contentDescription = "Calculadora",
                onImageClick = {
                    currentStep = 2
                }
            )
        }

        2 -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Divider(
                    color = colorResource(id = R.color.grey_dark),
                    thickness = 100.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = modifier
                        .weight(1f)
                        .statusBarsPadding()
                        .background(colorResource(id = R.color.teal_light)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    FieldText(
                        labelText = stringResource(id = R.string.quantidade_de_pessoas),
                        value = quantInput,
                        onValueChange = { newValue -> quantInput = newValue },
                        action = ImeAction.Next
                    )
                    FieldText(
                        labelText = stringResource(id = R.string.conta),
                        value = contaInput,
                        onValueChange = { newValue -> contaInput = newValue },
                        action = ImeAction.Done
                    )
                    GorjetaSwitch(
                        texto = stringResource(id = R.string.pretende_dar_gorjeta),
                        gorjeta = gorjeta,
                        onGorjetaChanged = { newValue -> gorjeta = newValue },
                        modifier = Modifier
                    )
                    if (gorjeta) {
                        // Caso a quantidade de pessoas seja 1, o switch de gorjeta individual não aparecerá
                        if (quant > 1) {
                            GorjetaSwitch(
                                texto = stringResource(R.string.gorjeta_individual),
                                gorjeta = gIndividual,
                                onGorjetaChanged = { gIndividual = it },
                                modifier = Modifier
                            )
                        }
                        if (!gIndividual) {
                            FieldText(
                                labelText = stringResource(R.string.percentagem_de_gorjeta),
                                value = percentGorjetaInput.getOrNull(0) ?: "",
                                onValueChange = { newValue ->
                                    if (percentGorjetaInput.isEmpty()) {
                                        percentGorjetaInput.add(newValue)
                                    } else {
                                        percentGorjetaInput[0] = newValue
                                    }
                                                },
                                    action = ImeAction.Done
                                )
                            if (percentGorjetaInput.isNotEmpty()) {
                                percentGorjeta = percentGorjetaInput[0].toDoubleOrNull() ?: 0.0
                            }
                        } else {
                            currentStep = 3
                            gIndividual = false
                        }
                    }
                    // Como os totais para cada pessoa vão aparecer, não vale a pena o total geral aparecer
                    if (!gIndividual) {
                        Spacer(modifier = Modifier.height(16.dp))
                        total = calcConta(
                            pessoas = quant,
                            conta = conta,
                            percentGorjeta = percentGorjeta
                        )
                        Text(text = stringResource(R.string.total_geral, total), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { contaInput = ""
                            quantInput = ""
                            percentGorjetaInput.clear()},
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey_dark))
                    ) {
                        Text(text = "Limpar", style = TextStyle(color = Color.White))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Divider(
                    color = colorResource(id = R.color.grey_dark),
                    thickness = 100.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        3 ->{
            Column(
                modifier = modifier
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .safeDrawingPadding()
                    .background(colorResource(id = R.color.teal)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Gorjeta Individual",
                    style = TextStyle(fontSize = 30.sp, color = Color.Black), modifier = Modifier.padding(top=10.dp,bottom = 10.dp)
                )
                while (percentGorjetaInput.size < quant) {
                    percentGorjetaInput.add("")
                }
                for (i in 0 until quant) {
                    Spacer(modifier = Modifier.height(10.dp))
                    FieldText(
                        labelText = stringResource(R.string.percentagem_de_gorjeta),
                        value = percentGorjetaInput[i],
                        onValueChange = { newValue ->
                            percentGorjetaInput[i] = newValue
                        },
                        action = ImeAction.Done
                    )
                    percentGorjeta = percentGorjetaInput[i].toDoubleOrNull() ?: 0.0
                    total = calcConta(quant, conta, percentGorjeta)
                    Text(
                        text = stringResource(
                            R.string.total_indivual,
                            i + 1,
                            total
                        ), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { currentStep = 2},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.grey_dark))
                ) {
                    Text(text = "Voltar atrás", style = TextStyle(color = Color.White))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DivisorPreview() {
    Divisor_ContaTheme {
        DivisorLayout(modifier = Modifier.fillMaxSize())
    }
}