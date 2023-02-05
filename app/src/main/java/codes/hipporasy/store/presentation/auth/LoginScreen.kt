package codes.hipporasy.store.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codes.hipporasy.store.R
import codes.hipporasy.store.ui.theme.StoreTheme
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun LoginScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    var emailText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * 0.5).dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.3).dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Image(
                painter = painterResource(id = R.drawable.bg_login),
                contentDescription = stringResource(id = R.string.image),
                contentScale = ContentScale.Crop,
            )
        }



        Text(
            stringResource(R.string.let_sign_you_in),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary
        )

        TextField(
            value = emailText,
            onValueChange = { emailText = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
            ),
            placeholder = {
                          Text(text = stringResource(R.string.username_or_email))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = MaterialTheme.colors.secondary
                )
            },
            textStyle = MaterialTheme.typography.body1
        )
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StoreTheme {
        LoginScreen()
    }
}