package com.deletech.malakoff.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deletech.malakoff.R
import com.deletech.malakoff.components.Loader
import com.deletech.malakoff.components.Toast
import com.deletech.malakoff.data.Resource
import com.deletech.malakoff.models.projects.Data
import com.deletech.malakoff.models.projects.Projects
import com.deletech.malakoff.storage.PreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()){
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = textColor,
                ),
                title = {
                    Text("Project Management Interview ")
                },
                actions = {

                }



            )
        },
    ){ innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(innerPadding)
        ) {

UiSetup(viewModel = viewModel, navController = navController)

        }
    }

}
@Composable
private fun UiSetup(
    viewModel: HomeViewModel,
    navController: NavHostController,
) {
    val context = LocalContext.current
    val accessToken = PreferenceManager(context).getAccessToken()
    val projectState by viewModel.projectReportRequestResult.collectAsState()
    LaunchedEffect(projectState) {
        if (projectState is Resource.Idle) {
            viewModel.getProjects(
 "Bearer $accessToken"
            )
        }
    }
    when (projectState) {
        is Resource.Success -> {
            if (projectState.data != null) {
                Dashboard(navController, projectState)

            }
        }
        is Resource.Error -> {
            Toast(message = projectState.message.toString())
        }

        is Resource.Idle -> {}
        is Resource.Loading -> {
            Loader()
        }
    }

}
@Composable
fun ProjectCardView(projects: Projects?,navController: NavHostController) {
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        projects?.data?.forEach { project ->
            item {
                ProjectCard(project = project, navController = navController)
            }
        }
    }
}
@Composable
fun AddProjectCard(
    navController: NavHostController,
){
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(color = backgroundColor),
            contentAlignment = Alignment.Center

        )
        {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                        Text(
                            text = "Add Project",
                            modifier = Modifier
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            color = textColor
                        )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("create_project")
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp),
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "app Logo"

                    )
                }
            }


        }
    }

}
@Composable
fun ProjectCard(project:Data?,navController: NavHostController){
    val scope = rememberCoroutineScope()
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 14.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = backgroundColor)
                .fillMaxWidth(),
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        text = "Name:",
                        modifier = Modifier
                            .padding(5.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = textColor
                    )
                    Text(
                        text = "${project?.Name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 60.dp, end = 5.dp),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = textColor
                    )

                }
            }

        }}

}
@Composable
private fun Dashboard(
    navController: NavHostController,
    reportState: Resource<Projects>

) {
    Column {
       AddProjectCard(navController)
        ProjectCardView(reportState.data, navController)
    }
}
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun AddProjectCardPreview(){
    AddProjectCard(rememberNavController())

}
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
fun ProjectCardPreview(){
  //  ProjectCard()

}