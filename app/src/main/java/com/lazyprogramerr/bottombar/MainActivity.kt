package com.lazyprogramerr.bottombar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lazyprogramerr.bottombar.ui.theme.BottomBarTheme
import com.lazyprogramerr.floatingbottombar.FloatingBottomBar
import com.lazyprogramerr.floatingbottombar.NavItem
import com.lazyprogramerr.floatingbottombar.navItems

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
                        exitDirection = FloatingToolbarExitDirection.Bottom
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .nestedScroll(scrollBehavior),

                    ){
                        val navController = rememberNavController()
                        val items = navItems {
                            item(Routes.S1.route,"S1",R.drawable.ic_launcher_foreground)
                            item(Routes.S2.route,"S2",R.drawable.ic_launcher_foreground)
                            item(Routes.S3.route,"S3",R.drawable.ic_launcher_foreground)
                        }


                        FloatingBottomBar(
                            modifier = Modifier.wrapContentSize().align(Alignment.BottomCenter),
                            navController = navController,
                            navItems = items,
                            startDestination = "S1",
                            itemsSpacedBy = Arrangement.spacedBy(3.dp),
                            scrollBehavior = scrollBehavior,
                            scenes = {
                                composable(Routes.S1.route) { SS() }
                                composable(Routes.S2.route) { S1() }
                                composable(Routes.S3.route) { S2() }
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val lt = List(75) {"Item #${it + 1}"}

            itemsIndexed(lt){
                    index,item ->
                Text(item)
            }
        }
    }
    @Composable
    fun S1(modifier: Modifier = Modifier) {
        Text("hello from page1")
    }

    @Composable
    fun S2(modifier: Modifier = Modifier) {
        Text("hello from page3")
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Preview(showSystemUi = true)
    @Composable
    private fun t() {
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .padding(innerPadding),

            ){
            val navController = rememberNavController()
            val items = navItems {
                item(Routes.S1.route,"S1",R.drawable.ic_launcher_foreground)
                item(Routes.S2.route,"S2",R.drawable.ic_launcher_foreground)
                item(Routes.S3.route,"S3",R.drawable.ic_launcher_foreground)
            }

            FloatingBottomBar(
                modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd),
                navController = navController,
                navItems = items,
                startDestination = "S1",
                
                itemsSpacedBy = Arrangement.spacedBy(5.dp),
                scenes = {
                    composable(Routes.S1.route) { SS() }
                    composable(Routes.S2.route) { SS() }
                    composable(Routes.S3.route) { SS() }
                }

            ) { currentDestination, clickedRoute ->
                if (currentDestination != clickedRoute){
                    navController.navigate(clickedRoute){
//                        Toast.makeText(this@MainActivity,clickedRoute, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

