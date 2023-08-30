package com.folklore.domain.model

data class UserPreferences(
    val welcomeScreenDisplayed: Boolean,
    val cities: List<String>,
    val states: List<String>,
    val sortOrder: SortOrder,
)

enum class SortOrder {
    NONE,
    DISTANCE,
    DATE_ASC,
    DATE_DSC,
}
