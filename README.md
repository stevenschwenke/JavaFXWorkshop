JavaFXWorkshop
==============

Shippable status:
[![Build Status](https://api.shippable.com/projects/5593645bedd7f2c0524cb0b3/badge/master)](https://app.shippable.com/projects/5593645bedd7f2c0524cb0b3/builds/latest)

Travis status:
[![Build Status](https://travis-ci.org/stevenschwenke/JavaFXWorkshop.svg?branch=master)](https://travis-ci.org/stevenschwenke/JavaFXWorkshop)


Meta: About the workshop
--------------------------
The workshop is an internal event for my coworkers. Target audience are developers who haven't heard much about JavaFX and want to know basic concepts and be able to build modern user interfaces.

The code works with Java 7 and Java 8.

Part of this workshop is inspired by Hendrik Ebbers book "Mastering JavaFX Controls" and from his
repository at https://github.com/guigarage/mastering-javafx-controls.  

Content
--------------------------
- Building your first FX application: How does JavaFX generally work? What is an fxml-file? What is property binding?
- Compiling SceneBuilder: Since JDK 8u40, Oracle doesn't provide a binary for the SceneBuilder. This chapter shows you how to compile your own SceneBuilder.
- Advanced basics: What are tranisitions and timelines? How can I style my application window? What are the pitfalls when using the TableView? ... And a lot of other stuff. 
- JavaFX 3D: What does a 3D application look like?
- Custom controls: How can I write completely customized components?
- Testing FX applications
- SwingInterop: How can FX code be used in Swing applications?


Meta: About JavaFX
---------------
- successor of Swing
- Swing is dead! (Zombie-mode because for the time being it will be in the SDK)
- much more features like CSS-styling, rendering using graphic chip, binding, ...

Quick Start: The first chapter in detail
--------------

To get a good start in the workshop, I line out the first chapter ("Building your first FX application") in more detail. Read the following and have a look at the code. Then you are ready to go for the more advanced topics.

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

This is our first UI:

![alt tag](1.png)

**Part 2: Properties and binding**
- Properties are a language extension - expect them to come around in the backend, too.
- extension of bean naming convention: 

    setMyFoo(...) {...}
    
    getMyFoo() {...}
    
    myFooProperty() {...}
    
- There is unidirectional and bidirectional binding: 

    myProperty.bind(...);
    
    myProperty.bindBidirectional(...);
    
- you can do math with properties, for example: sum.bind(amountOfApples.add(amountOfChips).add(amountOfPotatoes));
- you can listen on properties and get notified if there's a change
- great article about difference between ChangeListener and InvalidationListener: http://blog.netopyr.com/2012/02/08/when-to-use-a-changelistener-or-an-invalidationlistener/ (in short: InvalidationListener doesn't give you old and new value and fires even without the data changing. ChangeListener however is less performant. 

With properties, we can calculate the sum of the items in our list:

![alt tag](2.png)

**Part 3: Charts - the pie chart**
- data behind chart = ObservableList. You can listen on that, too!
- because of that, you just have to change the data and the chart will be updated

A chart makes our shopping list much more interesting:

![alt tag](3.png)

**Part 4: Styling with CSS**
- either directly for one component in SceneBuilder or in the code or in a CSS (you want to have the latter!)
- behold: not all CSS tags are available in JavaFX and you have to add a "-fx" before them: 

    -fx-background-color:red
    
- just in JavaFX - CSS: dropshadow

With styling, we now have a realy nice looking shopping list:

![alt tag](4.png)

More resources
----------------
- Ensemble (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- controlsFX (http://fxexperience.com/controlsfx/)
- watch the first two minutes of http://www.youtube.com/watch?v=a3dAteWr40k&feature=youtu.be
- jfxtras (http://jfxtras.org/)
- Oracle http://docs.oracle.com/javafx/
- More tools: Scenic View  (http://fxexperience.com/scenic-view/)
- More tools: e(fx)clipse (http://www.eclipse.org/efxclipse/index.html)
- 3D container terminal monitor (http://www.youtube.com/watch?v=AS26gZrYNy8)

Meta: Copyright
----------------
All files in this repository are under Creative Commons 4.0 (see http://creativecommons.org/licenses/by/4.0/). 

You are free to:

- Share — copy and redistribute the material in any medium or format
- Adapt — remix, transform, and build upon the material for any purpose, even commercially.

The licensor cannot revoke these freedoms as long as you follow the license terms.

Under the following terms:

- Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
- No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
