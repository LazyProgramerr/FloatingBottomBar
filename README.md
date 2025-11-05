# FloatingBottomBar
[![](https://jitpack.io/v/lazyprogramerr/FloatingBottomBar.svg)](https://jitpack.io/#lazyprogramerr/FloatingBottomBar)

A customizable floating bottom bar component for Android Compose applications.

## Description

This library provides a `FloatingBottomBar` composable that allows you to easily create a floating navigation bar in your Android application using Jetpack Compose. It supports customizable icons, colors, shapes, and scrolling behavior.  It uses the experimental Material 3 Expressive API.

<p align="center">
  <img src="https://raw.githubusercontent.com/LazyProgramerr/FloatingBottomBar/master/Sources/images/BottomBar.png" alt="Floating Bottom Bar Preview" width="80%">
</p>


## Features and Functionality

*   **Navigation:** Handles navigation between different screens using `NavHostController`.
*   **Customizable Appearance:** Offers extensive customization options, including:
    *   Icon and text colors (`iconColor`, `onClickColor`, `itemColor`)
    *   Icon size (`iconSize`)
    *   Text size (`textSize`)
    *   Shape of the bar and items (`barShape`, `itemShape`)
    *   Stroke color and width (`itemStrokeColor`, `itemStrokeWidth`)
    *   Spacing between items (`itemsSpacedBy`)
    *   Inner padding (`innerPaddingValues`)
    *   Arrangement of icon and text within the button (`iconsArrangement`)
*   **Scroll Behavior:** Integrates with `FloatingToolbarScrollBehavior` to react to scrolling events.
*   **NavItem DSL:** Provides a DSL for defining navigation items using the `navItems` builder.
*   **ShapableButton:** A custom button composable that allows for highly customizable shapes and styles.
*   **Icon support**: Supports both `Painter` and `@DrawableRes` resource IDs for icons.

## Technology Stack

*   Kotlin
*   Jetpack Compose
*   AndroidX Navigation Compose
*   Material 3 Expressive API

## Prerequisites

*   Android Studio
*   Basic knowledge of Kotlin and Jetpack Compose
*   Minimum SDK version: Not explicitly defined, but assumes compatibility with Jetpack Compose.

## Installation Instructions

1.  Add the repository to your `settings.gradle.kts` file (or `settings.gradle`):

    ```kotlin
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            // Add any other repositories your project needs here
            // Example : mavenCentral()
            // Example : maven { url 'https://jitpack.io' } //If hosted on jitpack
        }
    }
    ```

2. Add the dependency to your app's `build.gradle.kts` file (or `build.gradle`):

    ```kotlin
    dependencies {
        implementation("androidx.core:core-ktx:1.9.0") // Or higher
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2") // Or higher
        implementation("androidx.activity:activity-compose:1.8.2") // Or higher
        implementation(platform("androidx.compose:compose-bom:2023.03.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
        implementation("androidx.navigation:navigation-compose:2.7.5") // Or higher
        // Add the floatingbottombar module as a dependency
        implementation(project(":FloatingBottomBar")) // Adjust path if needed.

    }
    ```

3.  Sync your Gradle project.


## Screenshots

<p align="center">
  <img src="https://raw.githubusercontent.com/LazyProgramerr/FloatingBottomBar/master/Sources/images/Home_Screen.jpg" width="30%" />
  <img src="https://raw.githubusercontent.com/LazyProgramerr/FloatingBottomBar/master/Sources/images/Favourites_Screen.jpg" width="30%" />
  <img src="https://raw.githubusercontent.com/LazyProgramerr/FloatingBottomBar/master/Sources/images/Profile_Screen.jpg" width="30%" />
</p>


## Usage Guide

1.  **Define Navigation Items:** Use the `navItems` DSL to create a list of `NavItem` objects.  Each `NavItem` should have a unique `route`, a `contentDescription`, and either an `iconPainterResId` (resource ID of a drawable) or an `iconPainter` (a Compose `Painter`).

    ```kotlin
    import com.lazyprogramerr.floatingbottombar.navItems
    import com.lazyprogramerr.bottombar.R

    val items = navItems {
        item("Home", "Home", R.drawable.home)
        item("Favourites", "Favourites", R.drawable.star)
        item("Profile", "Profile", R.drawable.person)
    }
    ```

2.  **Create a `NavHostController`:** Instantiate a `NavHostController` to manage navigation.

    ```kotlin
    import androidx.navigation.compose.rememberNavController

    val navController = rememberNavController()
    ```

3.  **Implement the `FloatingBottomBar` composable:** Use the `FloatingBottomBar` composable in your layout, providing the `NavHostController`, the list of `NavItem` objects, and a start destination.  You also need to define the composable content for each route within the `scenes` lambda.

    ```kotlin
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import androidx.navigation.compose.composable
    import com.lazyprogramerr.floatingbottombar.FloatingBottomBar
    import androidx.compose.foundation.layout.Arrangement

    @Composable
    fun HomeScreen() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Home Screen")
        }
    }

    @Composable
    fun FavouritesScreen() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Favourites Screen")
        }
    }

    @Composable
    fun ProfileScreen() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Profile Screen")
        }
    }


    FloatingBottomBar(
        navController = navController,
        navItems = items,
        startDestination = "Home",
        itemsSpacedBy = Arrangement.spacedBy(3.dp), //Optional
        scenes = {
            composable("Home") { HomeScreen() }
            composable("Favourites") { FavouritesScreen() }
            composable("Profile") { ProfileScreen() }
        },
        onItemSelected = { currentDestination, clickedRoute ->
            //Optional: Handle navigation events.  For example, navigate only if the current destination is different from the clicked route
            if (currentDestination != clickedRoute) {
                navController.navigate(clickedRoute)
            }
        }
    )
    ```

4.  **Add Navigation Logic:** Define the composables associated with each route and ensure you set up your navigation using the `NavHostController`.

## API Documentation

### `FloatingBottomBar` Composable

```kotlin
@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun FloatingBottomBar(
    navController: NavHostController,
    navItems: List<NavItem>,
    startDestination: String,
    modifier: Modifier = Modifier,
    iconTextSpace: Dp = 2.dp,
    iconSize: Dp = 25.dp,
    textSize: TextUnit = 17.sp,
    iconColor: Color = ButtonBlack,
    onClickColor: Color = ButtonOrange,
    itemColor: Color = ButtonBlue,
    itemStrokeColor: Color = ButtonBlack,
    itemStrokeWidth: Dp = 2.dp,
    iconsArrangement: UiAlignment = UiAlignment.CenterTop,
    itemsSpacedBy : Arrangement.Horizontal = Arrangement.spacedBy(4.dp),
    innerPaddingValues: PaddingValues = PaddingValues(10.dp),
    itemShape: Shape = RoundedCornerShape(50),
    barShape: Shape = RoundedCornerShape(25),
    scrollBehavior: FloatingToolbarScrollBehavior? = null,
    scenes: NavGraphBuilder.(NavHostController) -> Unit,
    onItemSelected: (currentRoute: String?, clickedRoute: String) -> Unit = { _, _ -> }
)
```

*   `navController`: The `NavHostController` instance.
*   `navItems`: A list of `NavItem` objects representing the navigation items.
*   `startDestination`: The route to navigate to initially.
*   `modifier`: Modifier for the composable.
*   `iconTextSpace`:  The space between the icon and text in each button.
*   `iconSize`: The size of the icon.
*   `textSize`: The size of the text.
*   `iconColor`: The color of the icon when not selected.
*   `onClickColor`: The color of the button when selected.
*   `itemColor`: The background color of the navigation item.
*   `itemStrokeColor`: The stroke color of the navigation item.
*   `itemStrokeWidth`: The stroke width of the navigation item.
*   `iconsArrangement`:  The arrangement of the icon and text within the `ShapableButton`.  Uses the `UiAlignment` enum.
*    `itemsSpacedBy`:  Arrangement for spacing between items
*   `innerPaddingValues`: The inner padding of the toolbar.
*   `itemShape`: The shape of the individual navigation items.
*   `barShape`: The shape of the entire bottom bar.
*   `scrollBehavior`: Optional `FloatingToolbarScrollBehavior` for handling scroll events.
*   `scenes`: A lambda that defines the navigation graph using the `NavGraphBuilder`.
*   `onItemSelected`: An optional callback that is invoked when a navigation item is clicked. Receives the current route and the clicked route.

### `NavItem` Data Class

```kotlin
data class NavItem(
    val iconPainterResId: Int? = null,
    val iconPainter: Painter? = null,
    val route: String,
    val contentDescription: String? = null
)
```

*   `iconPainterResId`: The resource ID of the icon drawable.  Use this if you have your icon defined as a drawable resource.  Mutually exclusive with `iconPainter`.
*   `iconPainter`: A Compose `Painter` object for the icon.  Use this if you have a more complex icon or want to generate it programmatically. Mutually exclusive with `iconPainterResId`.
*   `route`: The route associated with the navigation item.
*   `contentDescription`: The content description for accessibility.

### `navItems` DSL

```kotlin
fun navItems(block: NavItemsBuilder.() -> Unit): List<NavItem>
```

A DSL for creating a list of `NavItem` objects.

```kotlin
class NavItemsBuilder {
    private val _items = mutableListOf<NavItem>()
    val items: List<NavItem> get() = _items

    fun item(route: String, contentDescription: String? = null, painter: Painter) {
        _items += NavItem(iconPainter = painter, route = route, contentDescription = contentDescription)
    }

    fun item(route: String, contentDescription: String? = null, @androidx.annotation.DrawableRes resId: Int) {
        _items += NavItem(iconPainterResId = resId, route = route, contentDescription = contentDescription)
    }
}
```

Example:

```kotlin
import com.lazyprogramerr.floatingbottombar.navItems
import com.lazyprogramerr.bottombar.R

val items = navItems {
    item("Home", "Home", R.drawable.home)
    item("Favourites", "Favourites", R.drawable.star)
    item("Profile", "Profile", R.drawable.person)
}
```

## Contributing Guidelines

Contributions are welcome! To contribute:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Implement your changes and write tests.
4.  Submit a pull request.

## License Information

No license has been specified for this project. All rights are reserved unless otherwise specified.

## Contact/Support Information

For questions or support, please open an issue on the GitHub repository: [https://github.com/LazyProgramerr/FloatingBottomBar](https://github.com/LazyProgramerr/FloatingBottomBar)
