🎥 Android App using Kotlin, MVVM, Clean Architecture, Coroutines, LiveData, Flow, Retrofit, Gson, Glide, Paging library, Hilt, Navigation Component, ViewBinding and Material Design. This app is the remake of https://github.com/veselovvv/Movies with Clean Architecture, Coroutines, search and better UI.
This Android app displays a list of popular movies retrieved from this API - https://www.themoviedb.org, as well as detailed information about each movie. There is also a search for movies.
When the data is loading, a progress indicator is displayed. In addition, there is a fail screen. It will be displayed if there is no connection, the service is not available, or another error has occurred.
![1](https://github.com/veselovvv/Movies2.0/assets/76612421/cefa6aa2-53bc-498f-a3b2-fe814f5f19cd)
![2](https://github.com/veselovvv/Movies2.0/assets/76612421/fa056b45-300d-4881-95be-d43e4b2bc2cb)
![3](https://github.com/veselovvv/Movies2.0/assets/76612421/6794ba21-f4b9-4c04-b4f6-ea13cb38587d)
![4](https://github.com/veselovvv/Movies2.0/assets/76612421/17b27779-a789-422d-ad15-7c93961f3031)
![5](https://github.com/veselovvv/Movies2.0/assets/76612421/9683ef68-44f3-4a6b-93e3-a325f4e27b12)
![6](https://github.com/veselovvv/Movies2.0/assets/76612421/043537df-3609-43af-9fe7-006d65a330c2)
![7](https://github.com/veselovvv/Movies2.0/assets/76612421/2575e289-b3ff-4f66-92d4-ae1003bec706)
![8](https://github.com/veselovvv/Movies2.0/assets/76612421/63733e94-10cf-48a7-93c8-33795efd26ca)
![9](https://github.com/veselovvv/Movies2.0/assets/76612421/c779e83e-1c1f-4797-967e-7e4bd4c381c8)
#Getting Started
The first thing you need to do is to get key (https://developers.themoviedb.org/3/getting-started/introduction). After that you need to input your key in const API_KEY in core/di/CoreDataModule.kt. Now you can launch the app.
