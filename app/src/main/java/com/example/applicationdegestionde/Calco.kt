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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun CalcoPrev(){
    var operand by rememberSaveable { mutableStateOf<Double?>(null) }
    var operator by rememberSaveable { mutableStateOf<Char?>(null) }
    var display by rememberSaveable { mutableStateOf("0") }

    // Reinitialisation de l'ecran
    fun onClear(){
        operand = null
        operator = null
        display = "0"
    }


    // Supprimer le dernier chiffre sur l'ecran

    fun onDelete(){
        //on verifit d'abord si la calculatrice contient un ou moins d'un element afficher sur l'ecran

        display = if (display.length <= 1)
            "0"
        else
            display.dropLast(1)
    }

    // Changer de signe

    fun onToggle(){
        //on verifit si aucun chiffre n'a ete saisi sur l'ecran
        if(display == "0") return

        //on verifit s'il y a deja le signe (-) devant un nombre
        if (display.startsWith("-")) //si le chiffre contient deja le signe (-) alors
            display.removePrefix("-") //on retire le signe(on change de signe, autrement dit, on annule le signe)
        else
            "-$display" //dans le cas contraire, on change le signe

    }

    fun onEquals(){
        var left = operand
        var right = display.toDoubleOrNull()
        var op = operator

        // Si aucune operation n'a ete effectuer
        if (left == null || right == null || op == null) return

        // Operation en fonction du choix de l'operateur
        val result = when (op){
            '+' -> left + right
            '-' -> left - right
            '*' -> left * right
            '÷' -> if (right == 0.0) Double.NaN else left / right

            else -> return
        }

        //Display prend la nouvelle valeur suite aux operations qui ont ete effectuees
        display = result.toString()
        operator = null
        operand = null

    }

    // Ajouter une decimale

    fun onDecimal(){
        //on verifit si notre operande ne contient pas un (.)
        if(!display.contains(".")){
            display += "." //on ajoute la decimale
        }
    }

    fun onOperator(op : Char){
        // On verifit si l'utilisateur a bien effectuer une operation
        if (operator != null && operand != null) // si l'operateur et l'operande ont bien ete choisi alors
            onEquals() //on appelle la fonction onEquals pour afficher le resultat

        operand = display.toDoubleOrNull()
        operator = op
        display = "0"
    }

    // Traitement des chiffres 0-9

    fun onDigit(c : Char){
        if (display == "0" && c!='0')// Si aucune valeur n'a ete saisie a l'ecran(ecran ne contient que la valeur par defaut "0") et que le caractere 0 n'a pas ete saisi alors
            display = c.toString() // on reccupere la valeur pas defaut de l'ecran
        if (display == "0" && c == '0')// Si on a seulement saisi le chiffre 0 et que l'ecran affiche uniquement 0 alors
            return //on ne fait rien
        else{
            display += c
        }
    }
    
    // Gestion des composants

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)
    )
    {

        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .background(
                color = Color.LightGray,
                RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
            )
            .padding(top = 60.dp),
            Alignment.BottomEnd
        )
        {
            Text(display,
                fontSize = 36.sp,
                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp)
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        val btnModifier = Modifier
            .padding(5.dp)
            .weight(1f)
            .height(60.dp)
        //Creation des boutons : Ligne C DEL ± ÷

        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            OutlinedButton(
                onClick = {onClear()},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ),
                modifier = btnModifier
            ) {
                Text("C")
            }

            OutlinedButton(
                onClick = {onDelete()},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text("DEL")
            }

            OutlinedButton(
                onClick = {onToggle()},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text("±")
            }

            OutlinedButton(
                onClick = {onOperator('÷')},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text("÷")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Gestion des boutons : 7 8 9 x
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in listOf<Char>('7', '8', '9'))
            {
                Button(
                    onClick = {onDigit(number)},
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors
                        (
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = btnModifier
                ) {
                    Text("$number")
                }
            }

            OutlinedButton(
                onClick = {onOperator('x')},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text("x")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        //Gestion des boutons :  4 5 6 -

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in listOf<Char>('4', '5', '6'))
            {
                Button(
                    onClick = {onDigit(number)},
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors
                        (
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = btnModifier
                ) {
                    Text("$number")
                }
            }

            OutlinedButton(
                onClick = {onOperator('-')},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text("-")
            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (number in listOf<Char>('1', '2', '3'))
            {
                Button(
                    onClick = {onDigit(number)},
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors
                        (
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = btnModifier
                ) {
                    Text("$number")
                }
            }

            OutlinedButton(
                onClick = {onOperator('+')},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = btnModifier
            ) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        // 0 . =

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = {onDigit('0')},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1.5f)
            ) {
                Text("0")
            }

            OutlinedButton(
                onClick = {onDecimal()},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1.5f)
            ) {
                Text(".")
            }

            OutlinedButton(
                onClick = {onEquals()},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors
                    (
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("=")
            }
        }

    }
}
