package com.deletech.malakoff.components
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.deletech.malakoff.R


/**
 * Password Text Field
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Done
) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val keyboardController = LocalSoftwareKeyboardController.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label,color=textColor)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Pin,
                contentDescription = "Password icon",
                tint = textColor
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {

                val visibleIconAndText = Pair(
                    first = Icons.Outlined.Visibility,
                    second = stringResource(id = R.string.icon_password_visible)
                )

                val hiddenIconAndText = Pair(
                    first = Icons.Outlined.VisibilityOff,
                    second = stringResource(id = R.string.icon_password_hidden),

                )

                val passwordVisibilityIconAndText =
                    if (isPasswordVisible) visibleIconAndText else hiddenIconAndText

                // Render Icon
                Icon(
                    imageVector = passwordVisibilityIconAndText.first,
                    contentDescription = passwordVisibilityIconAndText.second,
                    tint = textColor
                )
            }
        },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        isError = isError,
        supportingText = {
            if (isError) {
               // ErrorTextInputField(text = errorText)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor, // Set the text color here
            focusedBorderColor = textColor,
            unfocusedBorderColor = textColor,
            unfocusedTextColor =textColor,
            cursorColor =textColor,
        )
    )
}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PinTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Done
) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val keyboardController = LocalSoftwareKeyboardController.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label,color=textColor)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Pin,
                contentDescription = "Password icon",
                tint = textColor // Set the icon color to match the text color
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {

                val visibleIconAndText = Pair(
                    first = Icons.Outlined.Visibility,
                    second = stringResource(id = R.string.icon_password_visible)
                )

                val hiddenIconAndText = Pair(
                    first = Icons.Outlined.VisibilityOff,
                    second = stringResource(id = R.string.icon_password_hidden),

                    )

                val passwordVisibilityIconAndText =
                    if (isPasswordVisible) visibleIconAndText else hiddenIconAndText

                // Render Icon
                Icon(
                    imageVector = passwordVisibilityIconAndText.first,
                    contentDescription = passwordVisibilityIconAndText.second,
                    tint = textColor
                )
            }
        },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        isError = isError,
        supportingText = {
            if (isError) {
                // ErrorTextInputField(text = errorText)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor, // Set the text color here
            focusedBorderColor = textColor,
            unfocusedBorderColor = textColor,
            unfocusedTextColor =textColor,
            cursorColor =textColor,
        )
    )
}
/**
 * Email Text Field
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = TextStyle(color = textColor)) }, // Set text color here
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Username icon",
                tint = textColor // Set the icon color to match the text color
            )
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor,
            focusedBorderColor = textColor,
            unfocusedBorderColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = textColor,
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = TextStyle(color = textColor)) }, // Set text color here
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Username icon",
                tint = textColor // Set the icon color to match the text color
            )
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor,
            focusedBorderColor = textColor,
            unfocusedBorderColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = textColor,
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = TextStyle(color = textColor)) }, // Set text color here
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.PhoneIphone,
                contentDescription = "Username icon",
                tint = textColor // Set the icon color to match the text color
            )
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor,
            focusedBorderColor = textColor,
            unfocusedBorderColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = textColor,
        )
    )
}
@Preview
@Composable
fun UsernameTextFieldPreview() {
    // Define some state for preview
    var text by remember { mutableStateOf("") }

    // Call your composable with some default values
    UsernameTextField(
        value = text,
        onValueChange = { text = it },
        label = "Username",
        isError = false,
        errorText = "This is an error",
        imeAction = ImeAction.Next
    )
}




