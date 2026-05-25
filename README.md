# 🧪 Rick & Morty Explorer (Android)

A modern Android application built with Kotlin that explores the **Rick and Morty API**, showcasing characters, episodes, and locations using clean architecture and Paging 3 for efficient data loading.

This project demonstrates real-world Android development practices including **MVVM architecture, pagination, and REST API integration**.

---

## 🚀 Features

- 🔄 Infinite scrolling with **Paging 3**
- 🌐 Integration with **Rick and Morty API**
- 🧑 Character listing with details view
- 📺 Episode browsing and episode details
- 📍 Location exploration (if implemented)
- ⚡ Retrofit + OkHttp for networking
- 🧠 MVVM architecture (Clean separation of concerns)
- 📦 Repository + PagingSource pattern
- 🔄 ViewModel state management
- 🖼️ RecyclerView optimized rendering
- ❌ Loading, error, and empty states handling

---

## 🏗 Architecture

```text
UI (Activity / Fragment)
        ↓
ViewModel
        ↓
Repository
        ↓
PagingSource / Remote Data Source
        ↓
Retrofit API (Rick & Morty API)
```

---

## 🧰 Tech Stack

- Kotlin
- Android Paging 3
- Retrofit
- OkHttp Logging Interceptor
- ViewModel (AndroidX)
- LiveData / StateFlow
- RecyclerView
- Material Components
- Coroutines

---

## 📁 Project Structure

```text
data
│
├── remote
│   ├── api (Retrofit services)
│   ├── dto models
│   └── paging (PagingSource)
│
├── repository
│   ├── CharacterRepository
│   ├── EpisodeRepository
│   └── LocationRepository
│
domain
│
├── model
│   ├── Character
│   ├── Episode
│   └── Location
│
presentation
│
├── ui
│   ├── activities / fragments
│   ├── adapters
│   └── viewmodels
│
└── MainActivity.kt
```

---

## 🌍 API Reference

Rick and Morty API:  
https://rickandmortyapi.com/api/

---

## 📸 Screenshots

![Pagination Demo](images/movies.png)

![Pagination Demo](images/detail.png)

---

## ⚙️ How It Works

1. App launches and loads first page of characters
2. PagingSource fetches data from API via Repository
3. Retrofit handles network calls
4. ViewModel exposes paginated data to UI
5. RecyclerView renders items efficiently
6. More pages load automatically when scrolling

---

## 💡 Key Concepts Demonstrated

- Paging 3 implementation (no manual pagination)
- MVVM clean architecture
- Repository pattern separation
- Efficient API data handling
- Scalable Android project structure
- Reactive UI updates

---

## 📦 Setup Instructions

1. Clone the repository

```bash
git clone https://github.com/moseskamira/rickandmorty-app.git
```

2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on emulator or device

---

## 📱 Requirements

- Android Studio Giraffe or newer
- Minimum SDK 24+
- Internet connection

---

## 🎯 Learning Outcomes

- Real-world API integration
- Paging 3 implementation
- MVVM architecture mastery
- Clean project structuring
- Efficient UI rendering strategies

---

## 📄 License

This project is open-source and available under the MIT License.