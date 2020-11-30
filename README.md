# Mail Test
<img src = "https://github.com/AppCraftDev/appcraft-mailru-test-android/blob/develop/screencast.gif" width = "320"/>

Приложение для просмотра записей в AB
## Функции:
- Экран списка контактов.
- Экран детального просмотра контакта.
## Что использовалось
- Kotlin, AndroidX, Material Design 2.0, Coroutines
- Clean Architecture, MVVM
- [Koin](https://github.com/InsertKoinIO/koin)
- [Epoxy](https://github.com/airbnb/epoxy)
- [Timber](https://github.com/JakeWharton/timber)
- [Cicerone](https://github.com/terrakok/Cicerone)
- [Glide](https://github.com/bumptech/glide)
- [Dexter](https://github.com/Karumi/Dexter)
## Структура проекта
Проект разбит на следующие модули:
#### App
Слой отвечает за логику отображения данных на экране.
#### Domain
Слой содержит бизнес-логику приложения.
#### Data
Слой для работы с данными
#### Resources
Слой содержит ресурсы приложения. 
## Сборка проекта
Для успешной сборки проекта требуется выполнить следующие шаги:
- Установить android studio 4.1
- Импортировать проект из данного репозитория в Android Studio
- Синхронизировать Gradle для обновления всех зависимостей от сторонних библиотек.
- Запустить проект, нажав 'Run' ^R
