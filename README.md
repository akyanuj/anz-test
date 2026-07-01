# Anz-test
Anz - test assesment for android development 

## Tech Stack
- Kotlin
- Jetpack Compose
- MVVM/MVI - not pure mvi(reducers not used)
- Clean Architecture
- Multi Module
- Hilt
- Retrofit
- Coroutines
- Flow
- Material 3
- Coil

## Architecture
-app
--core
--feature-users
--feature-user-detail

## Features
- Users List
- User Detail
- Refresh
- Error Handling
- Loading State

## Unit Tests

- UsersViewModel
- GetUsersUseCase
- UsersRepository

## Assumptions

- No Room database was added because offline support was not part of the requirements.
- The selected User is passed between screens instead of re-fetching because the API does not expose a Get User By Id endpoint.
- Manual Parcelable implementation was used due to a Parcelize tooling issue with the current Kotlin/AGP setup.

# Directory Structure

```
├── app
│      ├── common  
|      ├── network
|      ├── di
|      └── ui
├── feature-users
│   ├── data
│   │   ├── remote
│   |   ├── di
│   │   ├── mapper
│   │   └── repository
│   │
│   ├── domain
│   │   ├── model
│   │   ├── repository
│   │   └── usecase
│   │
│   └── presentation
│       ├── screen
│       ├── state
│       ├── viewmodel
│
├── feature-user-detail
|   ├── data
│   │   └── repository
│   ├── domain
│   └── presentation
│       ├── screen
│       ├── state
```

## Demo Video
[Watch Demo](media/working_anzr.mp4)

## Screen shots
User List
[user-list-screen](media/user-list.png)

User Details
[user-detail-screen](media/detail.png)

Error - Retry
[error](media/error.png)

## Improvements

- Add Room for offline caching
- Pagination
- Search
- Retry button
- Pull to refresh
- UI tests
- Paging 3









