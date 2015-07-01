package de.stevenschwenke.java.javafx.workshop.chapter_3_advanced_basics;

/**
 * Some background information about JavaFX architecture.
 */
public class Architecture {

    /*
        See Architecture.png:

        Public Layer (top layer):
            Only use APIs of the public layer!

        Private Layer (Quantum Toolkit):
            Java, but shouldn't be used by a developer. APIs could change without warning.

        Native Layer (OpenGL, D3D, Glass Window Toolkit, Media Engine, Web Engine, Prism):
            Not developed in Java.
            Grant access to native OS layer.
            Prism for rendering JavaFX views. Several hardware-orientated implementations of Prism, like Direct3D on
            Windows.
            Also media- and web-engines.
            => The native layer is the reason why JavaFX cannot be delivered as a one-JAR framework: native components
               specific to OS



     */

}
