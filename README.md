JavaFXWorkshop
==============

1. What is JavaFX and why should you care?
---------------
- successor of Swing
- Swing is dead! (Zombie-mode because for the time being it will be in the SDK)
- much more features like CSS-styling, rendering using graphic chip, binding, ...

2. Let's code!
--------------

Setup your tools:
- Eclipse IDE
- JDK >1.7u9 (with jfxrt.jar)
- SceneBuilder 1.1 (2.0 is still buggy) (http://www.oracle.com/technetwork/java/javafx/downloads/index.html)

**Part 1: SceneBuilder and .fxml-files**
- .fxml-file = user interface declaration
- Controller = Java class that controls one .fxml-file
- don't forget to set the controller in the SceneBuilder on your top component!
- fields and methods in your controller are bound to the .fxml with "@FXML"
- just a side note about the lifecycle of a JavaFX application (http://docs.oracle.com/javafx/2/api/javafx/application/Application.html)

>The entry point for JavaFX applications is the Application class. The JavaFX runtime does the following, in order, whenever an application is launched:
>
>    1. Constructs an instance of the specified Application class
>
>    2. Calls the init() method
>
>    3. Calls the start(javafx.stage.Stage) method
>
>    4. Waits for the application to finish, which happens when either of the following occur:
>
>    the application calls Platform.exit() OR the last window has been closed and the implicitExit attribute on Platform is true
>
>    5. Calls the stop() method

**Part 2: Properties and binding**
- Properties are a language extension - expect them to come around in the backend, too.
- extension of bean definition: 

    setMyFoo(...) {...}
    
    getMyFoo() {...}
    
    myFooProperty() {...}
    
- There is unidirectional and bidirectional binding: 

    myProperty.bind(...);
    
    myProperty.bindBidirectional(...);
    
- you can do math with properties, for example: sum.bind(amountOfApples.add(amountOfChips).add(amountOfPotatoes));
- you can listen on properties and get notified if there's a change

**Part 3: Charts - the pie chart**
- data behind chart = ObservableList. You can listen on that, too!
- because of that, you just have to change the data and the chart will be updated

**Part 4: Styling with CSS**
- either directly for one component in SceneBuilder or in the code or in a CSS (you want to have the latter!)
- behold: not all CSS tags are available in JavaFX and you have to add a "-fx" before them: 

    -fx-background-color:red
    
- just in JavaFX - CSS: dropshadow

That is what we build together:

![alt tag](awesomeFXShoppingList.png)

3. Swing Interop
----------------
- JavaFX in Swing easy. Swing in JavaFX not before JDK 8 (however possible)
- replace Swing components with FX components step by step
- maybe invert life cycle by setting a controller factory in FXML loader
- because of time constraints and focus of this workshop: Let's talk about that later and specific for your project. Also, there will be an article about that.

4. current state of JavaFX (Java 1.7)
-------------------------------------
- some basic components missing like date chooser
- some rough edges in the API
- huge hype with a lot of potential
- Have a look at controlsFX (http://fxexperience.com/controlsfx/)
- Definitively have a look at the Ensemble (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- and watch the first two minutes of http://www.youtube.com/watch?v=a3dAteWr40k&feature=youtu.be

5. some more stuff to have a look at
-------------------------------------
- Oracle http://docs.oracle.com/javafx/
- More tools: Scenic View  (http://fxexperience.com/scenic-view/)
- More tools: e(fx)clipse (http://www.eclipse.org/efxclipse/index.html)