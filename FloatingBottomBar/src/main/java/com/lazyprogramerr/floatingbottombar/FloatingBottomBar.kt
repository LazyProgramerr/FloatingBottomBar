package com.lazyprogramerr.floatingbottombar

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState


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
@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun FloatingBottomBar(
    navController: NavHostController,
    navItems: List<NavItem>,
    startDestination: String,
    modifier: Modifier = Modifier,
    iconTextSpace: Dp = 2.dp,
    itemsSpacedBy : Arrangement.Horizontal = Arrangement.spacedBy(4.dp),
    innerPaddingValues: PaddingValues = PaddingValues(10.dp),
    itemShape: Shape = RoundedCornerShape(50),
    barShape: Shape = RoundedCornerShape(25),
    scrollBehavior: FloatingToolbarScrollBehavior? = null,
    scenes: NavGraphBuilder.(NavHostController) -> Unit,
    onItemSelected: (currentRoute: String?, clickedRoute: String) -> Unit = { _, _ -> }
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        scenes(navController)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        HorizontalFloatingToolbar(
            expanded = false,
            scrollBehavior = scrollBehavior,
            shape = barShape,
            modifier = modifier,
            contentPadding = innerPaddingValues,
        ) {
            Row (
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = itemsSpacedBy
            ){
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
                        spacing = iconTextSpace,
                        shape = itemShape,
                    )
//                    Spacer(modifier.width(2.dp))
                }
            }
        }
    }
}
