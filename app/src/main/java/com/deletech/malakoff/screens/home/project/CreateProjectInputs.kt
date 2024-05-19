package com.deletech.malakoff.screens.home.project
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deletech.malakoff.R
import com.deletech.malakoff.components.ProjectDescriptionTextField
import com.deletech.malakoff.components.ProjectNameTextField
import com.deletech.malakoff.screens.home.project.state.ProjectState
import com.deletech.malakoff.ui.theme.AppTheme
@Composable
fun CreateProjectInputs(
    projectState: ProjectState,
    onProjectNameChange: (String) -> Unit,
    onProjectDescriptionChange: (String) -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    Column(modifier = Modifier.fillMaxSize() .padding(top = 128.dp),verticalArrangement = Arrangement.Center,) {
        Text(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            text = stringResource(id = R.string.create_project_heading_text),
            color= textColor
        )
        ProjectNameTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),

            value = projectState.projectName,
            onValueChange = onProjectNameChange,
            label = stringResource(id = R.string.project_name),
            isError = projectState.errorState.projectNameErrorState.hasError,
            errorText = stringResource(id = projectState.errorState.projectNameErrorState.errorMessageStringResource)
        )
        ProjectDescriptionTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = projectState.projectDescription,
            onValueChange = onProjectDescriptionChange,
            label = stringResource(id = R.string.project_description),
            isError = projectState.errorState.projectDescriptionErrorState.hasError,
            errorText = stringResource(id = projectState.errorState.projectDescriptionErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )

    }
}