package com.lazyprogramerr.floatingbottombar

import androidx.compose.ui.graphics.painter.Painter

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

fun navItems(block: NavItemsBuilder.() -> Unit): List<NavItem> {
    val builder = NavItemsBuilder()
    builder.block()
    return builder.items
}
