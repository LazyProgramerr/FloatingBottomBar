# FloatingBottomBar

A customizable Floating Bottom Bar for Android Compose applications.

## Features and Functionality

*   **Composable Component:** Provides a `FloatingBottomBar` composable function to easily integrate a bottom navigation bar into your Android Compose UI.
*   **Navigation Integration:**  Seamlessly integrates with the Android Navigation Component (`androidx.navigation.compose`).
*   **Customizable Appearance:** Allows customization of item shape, bar shape, icon spacing, colors, and scroll behavior.
*   **DSL for NavItems:** Uses a Kotlin DSL (`navItems`) to define navigation items with icons, routes, and content descriptions.
*   **Icon Support:** Supports both drawable resource IDs and Painter objects for icons.
*   **Selection Indication:** Visually indicates the currently selected navigation item.
*   **Optional Callback:**  Provides an `onItemSelected` callback to handle item clicks and perform custom actions.
*   **Scroll Behavior**: Supports `FloatingToolbarScrollBehavior` to hide or show the bar on scroll.
*   **Shapable Button**: Uses custom `ShapableButton` composable to create clickable components with customizable shapes, colors, and text/icon alignment.

## Technology Stack

*   Kotlin
*   Android Jetpack Compose
*   Android Navigation Component
*   Material Design 3 (Experimental Material3ExpressiveApi)

## Prerequisites

*   Android Studio
*   Basic knowledge of Kotlin and Android development
*   Familiarity with Jetpack Compose
*   Android SDK

## Installation Instructions

1.  Add the JitPack repository to your project's `settings.gradle.kts` file:

    ```kotlin
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            maven { url = "https://jitpack.io" }
        }
    }
    ```

2. Add the dependency to your app's `build.gradle.kts` file:

    ```kotlin
    dependencies {
        implementation("com.github.LazyProgramerr:FloatingBottomBar:Tag") // Replace Tag with the latest tag or commit
    }
    ```
   *Note:* Replace `"Tag"` with the latest tag or commit hash from the repository.

## Usage Guide

1.  **Create NavItems:** Use the `navItems` DSL to define your navigation items. Example:

    ```kotlin
    import com.lazyprogramerr.floatingbottombar.navItems
    import com.lazyprogramerr.floatingbottombar.NavItem
    import com.lazyprogramerr.bottombar.R // Assuming your R file is in this package
    import androidx.compose.ui.res.painterResource

    val items = navItems {
        item("S1", "S1", R.drawable.ic_launcher_foreground) //Using Drawable Resource ID
        item("S2", "S2", painterResource(id = R.drawable.ic_launcher_foreground)) //Using Painter
        item("S3", "S3", R.drawable.ic_launcher_foreground)
    }

    //Alternatively, you can define the list manually
    val itemsManually : List<NavItem> = listOf(
        NavItem(iconPainterResId = R.drawable.ic_launcher_foreground, route = "S1", contentDescription = "S1"),
        NavItem(iconPainter = painterResource(id = R.drawable.ic_launcher_foreground), route = "S2", contentDescription = "S2")
    )
    ```

2.  **Integrate FloatingBottomBar in your Composable:**

    ```kotlin
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.lazyprogramerr.floatingbottombar.FloatingBottomBar

    @Composable
    fun MyScreen() {
        val navController = rememberNavController()

        Scaffold { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                FloatingBottomBar(
                    navController = navController,
                    navItems = items,
                    startDestination = "S1", // Define your starting route
                    scenes = {
                        composable("S1") { Screen1() }
                        composable("S2") { Screen2() }
                        composable("S3") { Screen3() }
                    },
                    onItemSelected = { currentRoute, clickedRoute ->
                        // Handle item selection (e.g., navigate if not already selected)
                        if (currentRoute != clickedRoute) {
                            navController.navigate(clickedRoute)
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun Screen1() {
        Text("Screen 1 Content")
    }

    @Composable
    fun Screen2() {
        Text("Screen 2 Content")
    }

    @Composable
    fun Screen3() {
        Text("Screen 3 Content")
    }
    ```

3.  **Define Routes**: In the provided `app/src/main/java/com/lazyprogramerr/bottombar/MainActivity.kt` the routes are defined like this:

    ```kotlin
       sealed class Routes(val route: String){
            object S1: Routes("S1")
            object S2: Routes("S2")
            object S3: Routes("S3")
        }
    ```
    You can adjust it as per your requirement

4.  **Customize Appearance (Optional):**

    ```kotlin
    FloatingBottomBar(
        navController = navController,
        navItems = items,
        startDestination = "S1",
        itemShape = RoundedCornerShape(20.dp), // Customize item shape
        barShape = RoundedCornerShape(30.dp),  // Customize bar shape
        iconTextSpace = 4.dp,               // Customize space between icon and text
        scenes = { /* ... */ },
        onItemSelected = { /* ... */ }
    )
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
    itemShape: Shape = RoundedCornerShape(50),
    barShape: Shape = RoundedCornerShape(25),
    scrollBehavior: FloatingToolbarScrollBehavior? = null,
    scenes: NavGraphBuilder.(NavHostController) -> Unit,
    onItemSelected: (currentRoute: String?, clickedRoute: String) -> Unit = { _, _ -> }
)
```

*   `navController`:  `NavHostController` instance for navigation.
*   `navItems`:  A list of `NavItem` objects representing the navigation items.
*   `startDestination`: The route of the initial screen to display.
*   `modifier`: Modifier for the bottom bar.
*   `iconTextSpace`:  Space between the icon and text in each item.
*   `itemShape`: Shape of each navigation item.
*   `barShape`:  Shape of the bottom bar itself.
*   `scrollBehavior`: `FloatingToolbarScrollBehavior` to control visibility on scroll.  See `FloatingToolbarDefaults.exitAlwaysScrollBehavior` in Material3 library.
*   `scenes`:  A lambda defining the navigation graph using the `NavGraphBuilder`.
*   `onItemSelected`:  A callback function invoked when a navigation item is clicked.  Receives the current route and the clicked route as arguments.

### `NavItem` Data Class

```kotlin
data class NavItem(
    val iconPainterResId: Int? = null,
    val iconPainter: Painter? = null,
    val route: String,
    val contentDescription: String? = null
)
```

*   `iconPainterResId`:  Resource ID of the icon drawable.  Use either `iconPainterResId` or `iconPainter`.
*   `iconPainter`: Painter object for the icon. Use either `iconPainterResId` or `iconPainter`.
*   `route`: The route associated with the navigation item.
*   `contentDescription`: Content description for accessibility.

### `navItems` DSL Function

```kotlin
fun navItems(block: NavItemsBuilder.() -> Unit): List<NavItem>
```

A DSL function to create a list of `NavItem` objects.  Example usage:

```kotlin
val items = navItems {
    item("home", "Home", R.drawable.ic_home)
    item("profile", "Profile", R.drawable.ic_profile)
}
```

## Contributing Guidelines

Contributions are welcome! Please follow these guidelines:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Implement your changes.
4.  Write tests for your changes.
5.  Submit a pull request.

## License Information

No license has been specified for this project.  All rights are reserved by the author.

## Contact/Support Information

For questions, bug reports, or feature requests, please open an issue on the GitHub repository: [https://github.com/LazyProgramerr/FloatingBottomBar](https://github.com/LazyProgramerr/FloatingBottomBar)