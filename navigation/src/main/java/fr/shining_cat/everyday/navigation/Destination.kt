package fr.shining_cat.everyday.navigation

sealed class Destination {

    class HomeDestination : Destination()

    class SettingsDestination : Destination()

    class SessionDestination() : Destination()

}