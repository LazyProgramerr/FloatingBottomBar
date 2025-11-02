package com.lazyprogramerr.floatingbottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Stable API for consumers.
 */
@Stable
data class FloatingBottomBarState(
    val startVisible: Boolean = true
)

/**
 * DSL-friendly NavItem builder (see below).
 */
@Stable
data class NavItem(
    val iconPainterResId: Int? = null,
    val iconPainter: Painter? = null,
    val route: String,
    val contentDescription: String? = null
)

/**
 * Main entry point for the library.
 *
 * - Scenes: attach your NavGraph inside this lambda
 * - onItemSelected: optional callback when an item is clicked
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FloatingBottomBar(
    navController: NavHostController,
    navItems: List<NavItem>,
    startDestination: String,
    modifier: Modifier = Modifier,
    state: FloatingBottomBarState = FloatingBottomBarState(),
    itemShape: Shape = RoundedCornerShape(50),
    barShape: Shape = RoundedCornerShape(25),
    scrollBehavior: FloatingToolbarScrollBehavior? = null,
    scenes: NavGraphBuilder.(NavHostController) -> Unit,
    onItemSelected: (currentRoute: String?, clickedRoute: String) -> Unit = { _, _ -> }
) {
    val currentRoute by remember { navController.currentBackStackEntryAsState() }.let { stateFlow ->
        // reading is done below
        stateFlow.value?.destination?.route
    }

    Box(modifier = modifier) {
        // Nav host showing the screens
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            scenes(navController)
        }

        // animated visibility allows us to show/hide with fade
        AnimatedVisibility(
            visible = state.startVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 260)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200)),
        ) {
            HorizontalFloatingToolbar(
                expanded = false,
                scrollBehavior = scrollBehavior,
                shape = barShape,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                navItems.forEach { item ->
                    val isSelected = (currentRoute == item.route)

                    // use the consumer-provided painter if available, otherwise from res id
                    val painter = item.iconPainter ?: item.iconPainterResId?.let { res ->
                        painterResource(id = res)
                    }

                    ShapableButton(
                        onClick = { onItemSelected(currentRoute, item.route) },
                        iconRes = painter,
                        contentDescription = item.contentDescription ?: item.route,
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
}
