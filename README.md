# MyCoffeeVenue
Nearest Coffee Venue  Details Demo

- [Download Apk](https://raw.githubusercontent.com/ddpatel2606/MyCoffeeVenue/master/apk/app-debug.apk)
  
ForeSquare NearbyVenue Listing Api 

 - [Venue Detail API Documentation](https://developer.foursquare.com/docs/api-reference/venues/explore)
 
 - [Direct Venue Detail Listing API](https://api.foursquare.com/v2/venues/explore?&client_id=UJ11TJWKALZ2LZJ2WDVSJX4D4I1K143EFHP251FNGCXOQDDW&client_secret=FIZJXGGOUTQQR3GEXBB3ANLO51HR3DEZ52H13KFPE3WZINEX&v=20210115&section=Coffee&ll=51.55156175729713%2C-0.2964009493821721)
 
## Instructions
- If you want to use your client secret and client Id 
  Go to --> ```API.kt``` file
  
  // Foursquare API client Id
  ```const val CLIENT_ID = "YOUR CLIENT ID"```
  
  // Foursquare API client secret
  ```const val CLIENT_SECRET = "YOUR CLIENT SECRET"```
  
 ## Activities
 - SplashActivity - Ask user permission to get current location.
 - MainActivity - Listing Nearby Coffee Venue and display on Map. 


 ## Tech stack & Open-source libraries
 - Minimum SDK level 24 to 30 (Latest)
 - [Kotlin](https://kotlinlang.org/) based, 
 - [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
 - [Coroutines Flow](https://developer.android.com/kotlin/flow)
 - Dagger dependency injection.
 
 - JetPack
   - LiveData - notify domain layer data to views.
   - Lifecycle - dispose of observing data when lifecycle state changes.
   - ViewModel - UI related data holder, lifecycle aware.
   
 - Architecture
   - MVVM Architecture (Model View ViewModel - DataBinding)
   - Repository Design Pattern
  
 - [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
 - [HttpInterceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) - An OkHttp interceptor which logs HTTP request and response data.
 - [Gson](https://github.com/google/gson) - A modern JSON library for Kotlin and Java.
 - [Picasso](https://github.com/square/picasso) - loading images.
 - [Timber](https://github.com/JakeWharton/timber) - logging.
 - [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
 - Custom Views
 - [CircularProgressView](https://github.com/rahatarmanahmed/CircularProgressView) - A polished and flexible CircleProgressView, fully customizable with animations.

## Images

```MainActivity : Dark Mode```


<img src="image/1.png" width="350"/>


```MainActivity : Light Mode```


<img src="image/2.png" width="350"/>
