
# Spark, a simple Android app to explain the clean architecture

<img src="https://parkup.app/website/screens/1.jpg" alt="Android Architecture " width=200 />&nbsp;
<img src="https://parkup.app/website/screens/2.jpg" alt="Android Architecture " width=200 />&nbsp;
<img src="https://parkup.app/website/screens/3.jpg" alt="Android Architecture " width=200 />&nbsp; 
<img src="https://parkup.app/website/screens/4.jpg" alt="Android Architecture " width=200 />

<br>

### Spark app is a simple Android application to explain clean architecture in android + CI/CD with Github Actions
#### I've separated it into 3 major layers.

<img src="https://parkup.app/website/screens/5.png" alt="Android Architecture " width=250 />

#### 1- Presentation Layer
    - Fragments      (By Android Navigation Component, Single Activity)
    - ViewModels     (Communicate with Use-Cases, Return LiveData)
#### 2- Domain Layer
    - Entities       (Core of system. The app's units)
    - UseCases       (A bridge between Presentation and Data layers, System logic)
    - Contracts      (Explain how layers can provide/access to data)
#### 3- Data Layer
    - Repositories   (implement Domain layer's contracts, get Data from Repos and map them to Domain layer's entity)
    - DataEntities   (get from APIs or DB)   
    

### Test
I've covered the most important layers as much as possible. But still needs to write more Unit/Ui tests.

<img src="https://parkup.app/website/screens/7.png?" alt="Android Architecture " width=420 />

Also I'm using GitHub Actions as CI/CD. As I defined before, If I push the codes into any branch except master/release, All tests will run, Then If all of them passed, one APK will be built and uploads into the Github actions artifact.

<img src="https://parkup.app/website/screens/8.png" alt="Android Architecture " width=530 /> <img src="https://parkup.app/website/screens/9.png" alt="Android Architecture " width=450 />


### Technologies
- Full Kotlin
- Dagger Hilt for DI
- Coroutines
- LiveData
- Navigation Component
- Junit/Espresso
- GitHub actions as CI/CD
 


### APIs
I've created 6 APIs with PHP Laravel framework.
I've published the Postman collection by this link.
https://documenter.getpostman.com/view/6268446/TW74hQ3R

<img src="https://parkup.app/website/screens/6.png" alt="Android Architecture " width=290 />



----------------------------
AmirHossein Teymoori
teymoori.net@gmail.com
