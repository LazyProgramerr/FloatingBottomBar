# FloatingBottomBar
[![](https://jitpack.io/v/lazyprogramerr/FloatingBottomBar.svg)](https://jitpack.io/#lazyprogramerr/FloatingBottomBar)

A customizable floating bottom bar for Android Compose applications.

## Description

This library provides a composable floating bottom bar that integrates seamlessly with Jetpack Navigation. It offers flexibility in styling and customization, allowing developers to easily create a modern and visually appealing navigation experience for their users.  The bottom bar floats above the content and supports optional scrolling behavior.

## Features and Functionality

*   **Jetpack Navigation Integration:**  Built to work seamlessly with Jetpack Navigation, allowing easy navigation between different composable destinations.
*   **Customizable Appearance:**  Control the shape, colors, and spacing of the bar and its items.
*   **Icon and Text Support:**  Display icons and text on each navigation item.  You can specify either a drawable resource ID or a `Painter` object for the icon.
*   **Dynamic Item Selection:**  Highlights the currently selected navigation item.
*   **Scroll Behavior:** Supports a scrolling behavior that allows the bottom bar to hide when the user scrolls down, using `FloatingToolbarScrollBehavior`.
*   **NavItem DSL:** Uses a DSL (Domain Specific Language) for easy creation of navigation items.
*   **Shapable Buttons:** Navigation items are custom buttons (`ShapableButton`) that allow for fine-grained control over their appearance, including shape, colors, and text/icon alignment.

## Technology Stack

*   Kotlin
*   Jetpack Compose
*   Jetpack Navigation

## Prerequisites

*   Android Studio
*   Basic knowledge of Kotlin and Jetpack Compose
*   Jetpack Navigation library already set up in your project

## Installation Instructions

1.  Add the dependency to your `build.gradle.kts` (Module: app) file:

    ```kotlin
    dependencies {
        // ... other dependencies
        implementation("com.lazyprogramerr:floatingbottombar:<version>") //Replace <version> with the actual version of the library
    }
    ```

    *Note:* Replace `<version>` with the actual version number of the library. You can find the latest version on the GitHub Releases page or a Maven repository.  You might need to add a Maven repository to your `settings.gradle.kts` or `build.gradle.kts` if the library is not available on Maven Central.  For example:

    ```kotlin
    repositories {
        google()
        mavenCentral()
        // Add other repositories if needed, for example:
        // maven { url "https://jitpack.io" }
    }
    ```

2.  Sync your Gradle project.

## Usage Guide

1.  **Create Navigation Items:**  Use the `navItems` DSL function to define your navigation items.  You can specify a `route`, `contentDescription`, and either a drawable resource ID or a `Painter` for each item.

    ```kotlin
    import com.lazyprogramerr.floatingbottombar.navItems
    import com.lazyprogramerr.bottombar.R // Assuming R is from your app's resources

    val items = navItems {
        item(Routes.S1.route, "S1", R.drawable.ic_launcher_foreground)
        item(Routes.S2.route, "S2", painterResource(id = R.drawable.ic_launcher_background)) //Example using a Painter
        item(Routes.S3.route, "S3") // Example with no icon
    }
    ```

2.  **Integrate `FloatingBottomBar` in your composable:** Use the `FloatingBottomBar` composable in your layout.

    ```kotlin
    import com.lazyprogramerr.floatingbottombar.FloatingBottomBar

    // ... inside your composable function
    val navController = rememberNavController()

    FloatingBottomBar(
        navController = navController,
        navItems = items,
        startDestination = "S1",
        scenes = {
            composable(Routes.S1.route) { SS() } // Replace SS, S1, S2 with your composables
            composable(Routes.S2.route) { S1() }
            composable(Routes.S3.route) { S2() }
        },
        onItemSelected = { currentDestination, clickedRoute ->
            if (currentDestination != clickedRoute) {
                navController.navigate(clickedRoute) {
                    // Optional: Configure navigation options (e.g., popUpTo, launchSingleTop)
                }
            }
        }
    )
    ```

3.  **Define Routes (Important):**  Create sealed classes (or constants) to represent your navigation routes. This improves type safety and maintainability.

    ```kotlin
    sealed class Routes(val route: String) {
        object S1 : Routes("S1")
        object S2 : Routes("S2")
        object S3 : Routes("S3")
    }
    ```

4.  **Add Composables for each Route:**  Ensure you have composable functions defined for each route used in your navigation graph.

    ```kotlin
    @Composable
    fun SS() {
        // Content for Route S1
        Text("Content for Route S1")
    }

    @Composable
    fun S1() {
        // Content for Route S2
        Text("Content for Route S2")
    }

    @Composable
    fun S2() {
        // Content for Route S3
        Text("Content for Route S3")
    }
    ```

5.  **Optional: Implement Scrolling Behavior** If you want the bottom bar to hide on scroll, instantiate `FloatingToolbarScrollBehavior`

        ```kotlin
        import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
        import androidx.compose.material3.FloatingToolbarDefaults
        import androidx.compose.material3.FloatingToolbarExitDirection
        import androidx.compose.ui.input.nestedscroll.nestedScroll

        @OptIn(ExperimentalMaterial3ExpressiveApi::class)
        @Composable
        fun MyScreen(){
           val scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
                exitDirection = FloatingToolbarExitDirection.Bottom
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior),

            ){
                // your code
                FloatingBottomBar(
                    //your code
                    scrollBehavior = scrollBehavior
                    //your code
                )

            }
        }
        ```

    **Complete Example (from `app/src/main/java/com/lazyprogramerr/bottombar/MainActivity.kt`):**

    ```kotlin
    package com.lazyprogramerr.bottombar

    import android.os.Bundle
    import android.widget.Toast
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
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
    import androidx.compose.ui.unit.dp
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.lazyprogramerr.bottombar.ui.theme.BottomBarTheme
    import com.lazyprogramerr.floatingbottombar.FloatingBottomBar
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

    }
    ```

## API Documentation

### `FloatingBottomBar` Composable Function

```kotlin
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
)
```

**Parameters:**

*   `navController`: `NavHostController` - The navigation controller for managing navigation.
*   `navItems`: `List<NavItem>` - A list of `NavItem` objects representing the items in the bottom bar.
*   `startDestination`: `String` - The route of the initial destination.
*   `modifier`: `Modifier` - Modifier for styling and layout.
*   `iconTextSpace`: `Dp` - Space between the icon and text in each item.
*   `itemsSpacedBy`: `Arrangement.Horizontal` - Arrangement for spacing between items.  Defaults to `Arrangement.spacedBy(4.dp)`.
*   `innerPaddingValues`: `PaddingValues` - Padding inside the bottom bar.
*   `itemShape`: `Shape` - Shape of each individual navigation item. Defaults to `RoundedCornerShape(50)`.
*   `barShape`: `Shape` - Shape of the bottom bar. Defaults to `RoundedCornerShape(25)`.
*   `scrollBehavior`: `FloatingToolbarScrollBehavior?` - The scroll behavior for the bottom bar, allowing it to hide on scroll.
*   `scenes`: `NavGraphBuilder.(NavHostController) -> Unit` -  A lambda that defines the navigation graph using the `NavGraphBuilder`.  Use this to define your composable destinations.
*   `onItemSelected`: `(currentRoute: String?, clickedRoute: String) -> Unit` - A callback function that is invoked when a navigation item is clicked.  The first argument is the current route, and the second argument is the clicked route. You can use this to perform custom actions or update the UI.

### `NavItem` Data Class

```kotlin
data class NavItem(
    val iconPainterResId: Int? = null,
    val iconPainter: Painter? = null,
    val route: String,
    val contentDescription: String? = null
)
```

**Properties:**

*   `iconPainterResId`: `Int?` - Optional drawable resource ID for the icon. Use this, or `iconPainter`, but not both.
*   `iconPainter`: `Painter?` - Optional `Painter` object for the icon. Use this, or `iconPainterResId`, but not both.
*   `route`: `String` - The route associated with the navigation item.
*   `contentDescription`: `String?` - Content description for accessibility.

### `navItems` DSL Function

```kotlin
fun navItems(block: NavItemsBuilder.() -> Unit): List<NavItem>
```

A DSL-based function for creating a list of `NavItem` objects.

### `ShapableButton` Composable Function

```kotlin
@Composable
fun ShapableButton(
    modifier: Modifier = Modifier,
    iconRes: Painter? = null,
    text: String? = null,
    contentDescription: String? = null,
    iconSize: Dp = 22.dp,
    textSize: TextUnit = 17.sp,
    shape: Shape = RoundedCornerShape(12.dp),
    iconColor: Color = ButtonBlack,
    textColor: Color = ButtonBlack,
    onClickColor: Color = ButtonOrange,
    backgroundColor: Color = ButtonBlue,
    strokeColor: Color = ButtonBlack,
    uiAlignment: UiAlignment = UiAlignment.Center,
    isSelected: Boolean = false,
    spacing: Dp = 6.dp,
    onClick: () -> Unit
)
```

A custom button composable used for each navigation item.  It's highly customizable, allowing control over its appearance, content alignment, and colors.

**Parameters:**

*   `modifier`: Modifier for styling and layout.
*   `iconRes`: `Painter?` - The painter for the icon.
*   `text`: `String?` - The text to display on the button.
*   `contentDescription`: `String?` - Content description for accessibility.
*   `iconSize`: `Dp` - Size of the icon.
*   `textSize`: `TextUnit` - Size of the text.
*   `shape`: `Shape` - Shape of the button.
*   `iconColor`: `Color` - Color of the icon.
*   `textColor`: `Color` - Color of the text.
*   `onClickColor`: `Color` - Color of the button when clicked (selected).
*   `backgroundColor`: `Color` - Background color of the button.
*   `strokeColor`: `Color` - Color of the button's border.
*   `uiAlignment`: `UiAlignment` - Alignment of the icon and text within the button.
*   `isSelected`: `Boolean` - Indicates whether the button is currently selected.
*   `spacing`: `Dp` - Spacing between the icon and text.
*   `onClick`: `() -> Unit` - Callback function that is invoked when the button is clicked.

## Contributing Guidelines

Contributions are welcome! To contribute:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Commit your changes with clear and descriptive commit messages.
4.  Submit a pull request.

## License Information

License not specified. Please contact the repository owner for licensing details.

## Contact/Support Information

For questions, issues, or support, please contact the repository owner through GitHub.
