package com.lazyprogramerr.floatingbottombar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState

data class NavItem(
    val icon: Painter,
    val route: String
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FloatingBottomBar(
    navController: NavHostController,
    navItems: List<NavItem>,
    startDestination: String,
    modifier: Modifier = Modifier,
    itemShape: Shape = RoundedCornerShape(50),
    barShape: Shape = RoundedCornerShape(25),
    scrollBehavior: FloatingToolbarScrollBehavior? = null,
    scenes: NavGraphBuilder.(NavHostController) -> Unit,
    itemOnClick: (currentDestination: String, clickedRoute: String) -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(modifier) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            scenes(navController)
        }

        HorizontalFloatingToolbar(
            expanded = false,
            scrollBehavior = scrollBehavior,
            shape = barShape,
            modifier = Modifier.navigationBarsPadding()
        ) {
            navItems.forEach { item ->

                val isSelected = (currentRoute == item.route)

                ShapableButton(
                    onClick = {
                        currentRoute?.let { itemOnClick(it, item.route) }
                    },
                    iconRes = item.icon,
                    contentDescription = item.route,
                    isSelected = isSelected,
                    text = item.route,
                    iconSize = 25.dp,
                    uiAlignment = UiAlignment.CenterBottom,
                    spacing = 2.dp,
                    shape = itemShape
                )
            }
        }
    }
}
