🐦 Flappy Bird (Java Swing)

A simple recreation of the classic Flappy Bird game built using Java, Swing, and AWT.
This project demonstrates basic 2D game development concepts including animation loops, sprite rendering, collision detection, and event handling.


---

🎮 Features

Smooth 60 FPS game loop using javax.swing.Timer

Gravity-based bird physics

Randomly generated pipe obstacles

Score tracking + high score system

Spacebar to flap / restart

Pixel-sprite artwork included (bird, background, pipes)



---


📁 Project Structure

src/
 └── FlappyBird.java
resources/
 ├── flappybird.png
 ├── flappybirdbg.png
 ├── toppipe.png
 └── bottompipe.png

Make sure your images are located in the same resource path your code uses:

getClass().getResource("./flappybird.png")


---

🛠 How to Run

Option 1: Compile and run via terminal

javac FlappyBird.java
java FlappyBird

Option 2: import into an IDE

1. Open IntelliJ / Eclipse / NetBeans


2. Create a Java project


3. Add the FlappyBird.java file


4. Place the image files in the same directory as the .class files or configure resources


5. Run the main application frame




---

⌨ Controls

Key	Action

SPACE	Bird flap / restart when game over



---

📌 Requirements

Java 8 or higher

Swing (included with JDK)



---

🚀 Future Improvements (optional ideas)

Add sound effects

Improve collision box accuracy

Difficulty scaling

Bird rotation animation

Main menu + pause system



---

📄 License

This project is released under the MIT License.
Feel free to use, modify, and share!

