package com.example.applicationdegestionde

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterForm(modifier: Modifier = Modifier) {

    var name by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var passwordIsVisible by rememberSaveable { mutableStateOf(false) }

    var isChecked by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment
            .CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(20.dp)
            
        ) {
        Text(
            modifier = Modifier.padding(top = 50.dp),
            text = "Registration Form",

            fontWeight = FontWeight.ExtraBold,
            fontSize = 34.sp

        )

        Spacer(modifier = Modifier.height(height = 20.dp))


        OutlinedTextField( value = name,
            onValueChange = {name = it; if(name.length<6){ nameError=true} else{ nameError = false}
            }, label = {Text (  "Name")},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {IconButton  (onClick ={}) { Icon( imageVector = Icons.Default.AccountCircle,
                contentDescription = null) }},
            isError = nameError
        )
        if (nameError) {Text("valeur courte", color = MaterialTheme.colorScheme.error)}


        OutlinedTextField( value = email,
            onValueChange = { email = it; if(email.length < 6) emailError = true else emailError = false   }, label = {Text (  "Email")},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {IconButton  (onClick ={}) { Icon( imageVector = Icons.Default.Email,
                contentDescription = null) }},
            isError = emailError
        )
        if(emailError) Text("email incorrect", color = MaterialTheme.colorScheme.error)

        OutlinedTextField( value = password,
            onValueChange = { password = it ; if(password.length < 6) passwordError = true else passwordError = false},
            label = {Text (  "password")},
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {IconButton  (onClick ={ passwordIsVisible = !passwordIsVisible }) { Icon( imageVector = Icons.Default.Lock,
                contentDescription = null) }},
            visualTransformation = if(passwordIsVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        if(passwordError) Text("mot de passe incorrect", color = MaterialTheme.colorScheme.error)

        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.align(alignment = Alignment.Start)) {
            Checkbox( checked = isChecked, onCheckedChange = { isChecked = !isChecked })
            Text(text = "I accept the terms and conditions", fontSize = 18.sp, modifier = Modifier.padding(12.dp) )
        }

        Text("Genre" , fontSize = 18.sp, modifier = Modifier.align(alignment = Alignment.Start))

        Row(verticalAlignment = Alignment.CenterVertically,  modifier = Modifier.align(alignment = Alignment.Start)) {
            RadioButton(true, {})
            Text("Homme", fontSize = 18.sp, modifier = Modifier.padding(12.dp))

            Spacer(Modifier.width(6.dp))
            
            RadioButton(false, {})
            Text("Femme", fontSize = 18.sp , modifier = Modifier.padding(12.dp))
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            enabled = isChecked,
            colors = ButtonDefaults
                .buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Red,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Black),

            ) {

            Text(text = "Valider", fontSize = 18.sp, )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        RegisterForm()
    }
}