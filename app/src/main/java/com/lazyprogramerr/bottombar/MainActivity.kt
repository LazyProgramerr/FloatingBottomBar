package com.lazyprogramerr.bottombar

import android.os.Bundle
import android.provider.ContactsContract
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
                            item(Routes.Home.route, "Home", R.drawable.home)
                            item(Routes.Favourites.route, "Favourites", R.drawable.star)
                            item(Routes.Profile.route, "Profile", R.drawable.person)
                        }

                        FloatingBottomBar(
                            modifier = Modifier.wrapContentSize().align(Alignment.BottomCenter),
                            navController = navController,
                            navItems = items,

                            startDestination = Routes.Home.route,
                            itemsSpacedBy = Arrangement.spacedBy(3.dp),
                            scrollBehavior = scrollBehavior,
                            scenes = {
                                composable(Routes.Home.route) { Home() }
                                composable(Routes.Favourites.route) { Favourites() }
                                composable(Routes.Profile.route) { Profile() }
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
        object Home: Routes("Home")
        object Favourites: Routes("Favourites")
        object Profile: Routes("Profile")
    }

    @Composable
    fun Home(modifier: Modifier = Modifier) {
        Box(modifier = Modifier.fillMaxSize()){
            Text(modifier = Modifier.wrapContentSize().align(Alignment.Center), text = "this is Home Scene")
        }
    }
    @Composable
    fun Favourites(modifier: Modifier = Modifier) {
        Box(modifier = Modifier.fillMaxSize()){
            Text(modifier = Modifier.wrapContentSize().align(Alignment.Center), text = "this is Favourites Scene")
        }
    }

    @Composable
    fun Profile(modifier: Modifier = Modifier) {
        Box(modifier = Modifier.fillMaxSize()){
            Text(modifier = Modifier.wrapContentSize().align(Alignment.Center), text = "this is Profile Scene")
        }
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
                item(Routes.Home.route,"Home",R.drawable.home)
                item(Routes.Favourites.route,"Favourites",R.drawable.star)
                item(Routes.Profile.route,"Profile",R.drawable.person)
            }

            FloatingBottomBar(
                modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd),
                navController = navController,
                navItems = items,
                startDestination = Routes.Home.route,
                
                itemsSpacedBy = Arrangement.spacedBy(5.dp),
                scenes = {
                    composable(Routes.Home.route) { Home() }
                    composable(Routes.Favourites.route) { Favourites() }
                    composable(Routes.Profile.route) { Profile() }
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

