# anz-test
Anz - test assesment for android development. using MVI-MVVM (not pure mvi - no reducers), compose, hilt, flows and coroutines  

# Directory Structure 

```
├── app
│      ├── common  
|      ├── model
|      ├── network
|      └── ui
├── feature-users
│   ├── data
│   │   ├── remote
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
│       └── components
│
├── feature-user-detail
|   ├── data
│   │   ├── remote
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
│       └── components
│
└──