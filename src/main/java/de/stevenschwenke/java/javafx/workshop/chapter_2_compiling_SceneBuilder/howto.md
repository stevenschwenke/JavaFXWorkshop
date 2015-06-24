
Since JDK 8u40, Oracle doesn't provide a binary for the SceneBuilder to avoid having to support all the different platforms.

1. Download Scene Builder Source from http://hg.openjdk.java.net/openjfx/8u40/rt/file/eb264cdc5828/apps/scenebuilder    
2. create a new project in your IDE. You should name it something like "SceneBuilder_8u40"
3. copy and paste the contents of the src/com folders of both (!) SceneBuilderApp AND SceneBuilderKit into your IDE
4. It should build without any errors. You can start the SceneBuilder via running app/SceneBuilderApp.java
5. Export as artifact:
    - In e(fx)clipse click File -> Export -> Java -> Runnable JAR with com/oracle/javafx/scenebuilder/app/SceneBuilderApp.java as start app and create a runnable jar.
    - In Intellij IDEA: In "project structure", create artifact of type "JavaFX Application". Set com/oracle/javafx/scenebuilder/app/SceneBuilderApp.java as main class. Don't forget to rename the artifact and the jar to something like "SceneBuilder_8u40"s.
TODO: How to use this jar in the context menu to open an fxml file?

Alternative download via gluon
Gluon, see http://gluonhq.com/, is a company that kindly provides a repository for downloading the source of the SceneBuilder, having a Gradle setup, see https://bitbucket.org/gluon-oss/scenebuilder/downloads