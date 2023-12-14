package rs.raf.projekat2.radovan_markovic_rn4719.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.core.component.KoinComponent
import rs.raf.projekat2.radovan_markovic_rn4719.R
import rs.raf.projekat2.radovan_markovic_rn4719.ui.theme.Rsrafprojekat2radovan_markovic_rn4719Theme


class LoginActivity() : ComponentActivity(), KoinComponent {

    companion object {
        const val USER_KEY: String = "userKey"
        const val USERNAME: String = "Radovan"
        const val PASSWORD: String = "123456789"
        const val PASSWORD_KEY: String = "passKey"
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Rsrafprojekat2radovan_markovic_rn4719Theme {
                LoginScreen(getSharedPreferences(packageName, Context.MODE_PRIVATE))
            }
        }
    }
}

@Composable
fun LoginScreen (sharedPreference:SharedPreferences) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 200.dp),
        Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        Alignment.CenterHorizontally
    ) {
        val usernameState = remember { mutableStateOf(TextFieldValue())}
        Title()
        TextField(value = usernameState.value,
            onValueChange = {usernameState.value = it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text(stringResource(id = R.string.username))},
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent))

        val passwordState = remember { mutableStateOf(TextFieldValue())}
        val showPassword = remember {
            mutableStateOf(false)
        }
        TextField(value = passwordState.value,
            onValueChange = {passwordState.value = it},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            label = {Text(stringResource(id = R.string.password))},
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            visualTransformation = if(showPassword.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if(showPassword.value) {
                    IconButton(onClick = {showPassword.value = false}) {
                        Icon (
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "Hide Password"
                        )
                    }
                } else {
                    IconButton(onClick = {showPassword.value = true}) {
                        Icon (
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "Show Password"
                        )
                    }
                }
            })

        Button(onClick = {
                         if(usernameState.value.text.equals(LoginActivity.USERNAME)
                             && passwordState.value.text.equals(LoginActivity.PASSWORD)) {
                                 sharedPreference.edit().putString(LoginActivity.USER_KEY,LoginActivity.USERNAME).putString(LoginActivity.PASSWORD_KEY,LoginActivity.PASSWORD).apply()
                                 context.startActivity(Intent(context,ContentActivity::class.java))
                         } else {
                             Toast.makeText(context,"Credentials are wrong",Toast.LENGTH_SHORT).show()
                         }
        },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp,15.dp)) {
            Text(stringResource(id = R.string.logIn))
        }
    }
}

@Composable
fun Title () {
    Text(stringResource(id = R.string.rsh),
    style = MaterialTheme.typography.h4)
}
