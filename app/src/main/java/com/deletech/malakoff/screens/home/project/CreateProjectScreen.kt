package com.deletech.malakoff.screens.home.project
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.deletech.malakoff.R
import com.deletech.malakoff.components.Loader
import com.deletech.malakoff.components.NormalButton
import com.deletech.malakoff.components.Toast
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.screens.home.HomeViewModel
import com.deletech.malakoff.screens.home.project.state.ProjectUiEvent
import com.deletech.malakoff.storage.PreferenceManager
import com.deletech.malakoff.ui.theme.AppTheme

@Composable
fun CreateProjectScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()){
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val projectState by remember {
        viewModel.projectState
    }
    val context = LocalContext.current
    val accessToken = PreferenceManager(context).getAccessToken()
    val createState by viewModel.createProjectRequestResult.collectAsState()
    LaunchedEffect(createState) {
        if (createState is Resource.Success ) {
            navController.navigate("home_screen")
            viewModel.resetStates()
        }
    }
    when (createState) {
        is Resource.Idle -> {
        }
        is Resource.Loading -> {
            Loader()
            Toast(message = "Loading...Please wait")
        }
        is Resource.Success -> {
                Toast(message = createState.data?.message.toString())
        }
        is Resource.Error -> {
            Toast(message = createState.message.toString())

        }

        else -> {}
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor)
    ) {
        // Background image
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .imePadding()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        modifier = Modifier
                            .padding(horizontal = AppTheme.dimens.paddingLarge)
                            .padding(bottom = AppTheme.dimens.paddingExtraLarge)
                    ) {

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(128.dp)
                                .padding(top = AppTheme.dimens.paddingSmall),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(data = R.drawable.playstore)
                                .crossfade(enable = true)
                                .scale(Scale.FILL)
                                .build(),
                            contentDescription = stringResource(id = R.string.create_project_heading_text)
                        )
                        CreateProjectInputs(
                            projectState = projectState,
                            onProjectNameChange = { inputString ->
                                viewModel.onUiEvent(
                                    projectUiEvent = ProjectUiEvent.ProjectNameChanged(
                                        inputString
                                    )
                                )
                            },
                            onProjectDescriptionChange = { inputString ->
                                viewModel.onUiEvent(
                                    projectUiEvent = ProjectUiEvent.ProjectDescriptionChanged(
                                        inputString
                                    )
                                )
                            },
                        )
                        NormalButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.create_button_text),
                            onClick = {
                                viewModel.onUiEvent(projectUiEvent = ProjectUiEvent.Submit)
                                if (projectState.isProjectSuccessful) {
                                    viewModel.createProject("Bearer $accessToken",viewModel.projectState.value.projectName.trim(),
                                        viewModel.projectState.value.projectDescription.trim())

                                }
                            }
                        )





                    }


                }

            }
        }
    }


}