__APCS 1 Final Project - Ethan Morgan '20__

This project has two components.
- A _graphics_ package, which provided a framework to store and render 3D objects.
- A series of _demos_, which are created by me to display the functionality of the package. 

__How to Run this Thing:__

Enter src folder. All the Demos are right there.

_Compile with the lanterna.jar library:_
Windows: ```javac -cp "lanterna.jar;." Rubiks.java```
Unix: ```javac -cp lanterna.jar:. Rubiks.java```

_Run:_
Windows: ```java -cp "lanterna.jar;." Rubiks```
Unix: ```java -cp lanterna.jar:. Rubiks```

There are demos other than Rubiks. Run them with similar commands.

__Development Log:__

_Day 1 (2019-01-03):_
Added basic classes: Vector, Triangle, Particle, Camera
Most of the methods here are simple math. The only complex components done today were the Vector.intersectsTriangle() method and the methods for the Camera buffers.

_Day 2 (2019-01-04):_
Added rendering to the Camera. Added support for lanterna Screens in Camera.
Added Shape class.

_Day 3 (2019-01-05):_
Added 3D rotation algorithm for Vectors.
Added lighting when rendering faces.
The expected product is now complete. Running Driver.java will show a rotating cube.

_Day 4 (2019-01-06):_
Added the Rubik's Cube demo.