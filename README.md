# News App with Bookmarks Feature

This is a simple Android app that displays news articles fetched from a News API. Users can view the latest news, bookmark their favorite articles, and view them later in offline mode. The app has two primary sections: **News** and **Bookmarks**, both accessible through a bottom navigation UI.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Libraries Used](#libraries-used)
- [UI Design](#ui-design)
- [State Management](#state-management)
- [Screenshots](#screenshots)
- [How to Run](#how-to-run)

## Features

### Bottom Navigation UI
- **News Section** (Right side): Displays the latest news articles fetched from the News API.
- **Bookmarks Section** (Left side): Shows all bookmarked articles stored in a local database for offline access.

### News Screen
- Fetches and displays articles from the News API.
- Each news card shows:
  - **Source**: The name of the news source.
  - **Title**: The headline of the article.
  - **Description**: A short summary of the article.
  - **Image**: A thumbnail or relevant image for the article.
- Clicking on a news card opens a detailed view with:
  - Full content of the article
  - Author name
  - Publication date

### Bookmarks
- Users can bookmark news articles from both the news list and detail screens.
- Bookmarked articles are saved locally using Room Database for offline access.
- The "Bookmarks" section displays all saved articles.

### State Management
- Proper state handling for loading, empty states, and error messages.
- Loading indicators and error messages are shown appropriately based on the API response.

## Tech Stack
- **Kotlin**: Used as the primary programming language.
- **Android SDK**: Base framework for Android app development.
- **XML**: Used for UI design.

## Libraries Used
- **Retrofit**: For fetching news articles from the News API.
- **Room Database**: To store bookmarked news articles locally for offline viewing.
- **Picasso**: To load and display images in the news cards.
- **LiveData**: For observing data changes and managing state.
- **ViewModel**: To separate the UI logic from business logic and ensure the app handles configuration changes effectively.
- **Coroutines**: For managing asynchronous tasks like API calls and database operations.
- **Lottie**: For animations.

## UI Design
The app follows a clean and user-friendly design with:
- A **Bottom Navigation Bar** to switch between "News" and "Bookmarks" sections.
- News articles are presented in a **RecyclerView**, where each item is a card containing the article's title, source, description, and image.
- The detailed news screen shows the full content, author, and publication date of the article.
- XML is used for designing all layouts, with a focus on readability and maintainability.

## State Management
The app handles various states:
- **Loading**: A loading spinner is displayed when fetching data from the News API.
- **Error**: An error message is shown if the API request fails.
- **Empty**: An empty state message is shown if no articles are available or no bookmarks are saved.

## Screenshots
| News Screen | Bookmark Screen | News Detail Screen | Splash Screen
| --- | --- | --- | --- |
| ![News Screen](https://github.com/user-attachments/assets/03a075a6-d7bb-4bb5-91df-9d6e19c304b6) | ![Bookmark Screen](https://github.com/user-attachments/assets/0029141b-fb53-427e-9018-d40db3a78ec6) | ![News Detail Screen](https://github.com/user-attachments/assets/d0c1a2e1-2ce9-4154-8218-638663014f5b) | ![splash](https://github.com/user-attachments/assets/3bcc4a8e-8bba-4d8c-8df9-b096b7bc808e)


## Video

