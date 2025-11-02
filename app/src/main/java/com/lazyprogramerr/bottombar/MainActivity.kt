package com.lazyprogramerr.bottombar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lazyprogramerr.bottombar.ui.theme.BottomBarTheme
import com.lazyprogramerr.floatingbottombar.FloatingBottomBar
import com.lazyprogramerr.floatingbottombar.NavItem

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding),

                    ){
                        val navController = rememberNavController()
                        val items = listOf(
                            NavItem(painterResource(R.drawable.ic_launcher_foreground),"S1"),
                            NavItem(painterResource(R.drawable.ic_launcher_foreground),"S2"),
                            NavItem(painterResource(R.drawable.ic_launcher_foreground),"S3"),
                        )
                        FloatingBottomBar(
                            modifier = Modifier.fillMaxWidth(),
                            navController = navController,
                            navItems = items,
                            startDestination = "S1",
                            scenes = {
                                composable(Routes.S1.route) { SS() }
                                composable(Routes.S2.route) { SS() }
                                composable(Routes.S3.route) { SS() }
                            }

                        ) { currentDestination, clickedRoute ->
                            if (currentDestination != clickedRoute){
                                navController.navigate(clickedRoute){
                                    Toast.makeText(this@MainActivity,clickedRoute, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class Routes(val route: String){
        object S1: Routes("S1")
        object S2: Routes("S2")
        object S3: Routes("S3")
    }

    @Composable
    fun SS(modifier: Modifier = Modifier) {

    }
}
