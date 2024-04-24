# Android Project with Clean Architecture,  MVVM,  Hilt,  Retrofit, and Navigation

This is a sample Android project showcasing the implementation of an Android application using Clean Architecture, MVVM design pattern, Hilt for dependency injection, Retrofit for API communication, and the Navigation Component for seamless navigation between screens. The application fetches data from the [TMDB](https://developer.themoviedb.org/reference/discover-movie) API.

## Features

- Implements Clean Architecture for separation of concerns.
- Follows MVVM design pattern for better testability and maintainability.
- Uses Hilt for dependency injection, making it easy to manage dependencies.
- Utilizes Retrofit for network communication.
- Utilizes the Navigation Component for navigation between screens.

## Libraries Used

- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Store and manage UI-related data.
- [Coroutines](https://developer.android.com/kotlin/coroutines): Asynchronous programming.
- [Hilt](https://dagger.dev/hilt/): Dependency injection framework.
- [Retrofit](https://square.github.io/retrofit/): Type-safe HTTP client for Android.
- [GSON](https://github.com/google/gson): Library for JSON serialization and deserialization.
- [OkHttp](https://square.github.io/okhttp/): HTTP client that efficiently handles HTTP requests.
- [Navigation Component](https://developer.android.com/guide/navigation): Provides a framework for navigation within your app.

## Architecture

This project follows the Clean Architecture principles, which separates the application into different layers:


1. **Data Layer**: This layer fetches data from external sources. It includes API interfaces, data sources, and data models.

2. **Domain Layer**: This layer contains the business logic of the application. It defines entities, use cases, and repositories.

3. **Presentation Layer** (UI): This layer contains the Jetpack Compose components, ViewModels, and UI-related logic.

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/nightfury96/MovieDb.git
```

2. Open the project in Android Studio.

3. Build and run the application on an Android emulator or a physical device.

## Usage

1. Upon launching the app, it will fetch data from the TMDB API and display it in a list.

2. You can search the Movies.

3. Use the Navigation Component to navigate between screens.
