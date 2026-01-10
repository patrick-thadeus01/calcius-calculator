package com.example.applicationdegestionde

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calculatrice() {
    CalculatricePreview()
}

@Composable
@Preview(showBackground = true)
fun CalculatricePreview() {
    var operande by rememberSaveable { mutableStateOf<Double?>(null) }
    var operator by rememberSaveable { mutableStateOf<Char?>(null) }
    var display by rememberSaveable { mutableStateOf("0") }

    // Remet la calculatrice à zéro
    fun onClear() {
        operande = null
        operator = null
        display = " 0 "
    }

    // Supprime le dernier caractère
    fun onDelete() {

        display = if (display.length <= 1) {
            "0"
        } else {
            display.dropLast(1)
        }
    }

    // Change le signe
    fun onToggleSign() {
        
        if (display == "0") return
        display = if (display.startsWith("-")) {
            display.removePrefix("-")
        } else {
            "-$display"
        }
    }

    // Ajoute un point décimal
    fun onDecimal() {
        if (!display.contains(".")) {
            display += "."
        }
    }

    // gerer les chiffres 0–9 
    fun onDigit(c: Char) {
        if (display == "0" && c != '0') {
            display = c.toString()
        } else if (display == "0" && c == '0') {
            return
        } else {
            display += c
        }
    }

    fun onEquals() {
        val left = operande
        val right = display.toDoubleOrNull()
        val op = operator

        if (left == null || right == null || op == null) return

        val result = when (op) {
            '+' -> left + right
            '-' -> left - right
            'x' -> left * right
            '÷' -> if (right == 0.0) Double.NaN else left / right
            else -> return
        }

        display = result.toString()
        operator = null
        operande = null
    }

    // Appelé pour les opérateurs + - x ÷
    fun onOperator(op: Char) {
        if (operator != null && operande != null) {
            onEquals()
        }
        operande = display.toDoubleOrNull()
        operator = op
        display = "0"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color.LightGray,
                    RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                )
                .padding(top = 60.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = display,
                fontSize = 36.sp,
                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        val btnModifier = Modifier
            .padding(5.dp)
            .weight(1f)
            .height(60.dp)

        // Ligne C DEL ± ÷
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = { onClear() },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text(text = "C")
            }

            OutlinedButton(
                onClick = { onDelete() },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text(text = "DEL")
            }

            OutlinedButton(
                onClick = { onToggleSign() },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text(text = "±")
            }

            OutlinedButton(
                onClick = { onOperator('÷') },
                modifier = btnModifier,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "÷", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 7 8 9 x
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in listOf('7', '8', '9')) {
                Button(
                    onClick = { onDigit(number) },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = btnModifier
                ) {
                    Text(text = "$number")
                }
            }

            OutlinedButton(
                onClick = { onOperator('x') },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text(text = "x")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 4 5 6 -
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in listOf('4', '5', '6')) {
                Button(
                    onClick = { onDigit(number) },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = btnModifier
                ) {
                    Text(text = "$number")
                }
            }

            OutlinedButton(
                onClick = { onOperator('-') },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text(text = "-")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 1 2 3 +
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in listOf('1', '2', '3')) {
                Button(
                    onClick = { onDigit(number) },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = btnModifier
                ) {
                    Text(text = "$number")
                }
            }

            OutlinedButton(
                onClick = { onOperator('+') },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text(text = "+")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 0 . =
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onDigit('0') },
                modifier = btnModifier.weight(1.5f),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "0")
            }

            OutlinedButton(
                onClick = { onDecimal() },
                modifier = btnModifier.weight(1.5f),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = ".")
            }

            OutlinedButton(
                onClick = { onEquals() },
                modifier = btnModifier,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "=")
            }
        }
    }
}