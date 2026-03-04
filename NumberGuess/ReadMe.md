# :jigsaw:  How the Game Works 

The game randomly generates a target number between 1 and 100.

The player tries to guess the number. After every guess, the game provides hints:

⬆ Too Low – Increase the guessed number

⬇ Too High – Decrease the guessed number

🎉 Correct Guess – Player wins the round

Each guess counts as an attempt and is recorded for tracking performance and also determine accuracy of player on the basis of this
<br/>
## 🎯 Game Modes

The game supports three difficulty modes, each changing the attempt rules.
<dl>
-<dh> 🟢 Easy Mode</dh>

<dd>Unlimited attempts</br>
Player can keep guessing until the correct number is found</br>
All attempts are still counted for statistics</br>
Good for beginners who want to practice.</br>
</dd>

-<dh> 🟡 Challenge Mode</dh>
<dd>Player defines the maximum number of attempts</br>
If the correct number is not guessed within the limit → Game Over</br>
The target number is revealed after losing</br>
This mode adds strategy and difficulty.</br>
</dd>


-<dh>🔴 Hard Mode</dh>
<dd>
Only one attempt</br>
If the guess is wrong → immediate Game Over</br>
This mode tests pure luck and intuition.</br>
</dd>
</dl>

## 🖥️ UI Features

- The interface is built entirely with Java Swing and includes:

- Player information panel

- Guess input field

- Remaining attempt indicator

- Hint / suggestion display

- Game result notification

- Mode-specific behavior

- Dynamic UI updates during runtime
 </br>
<h3>⚙️ Technologies Used</h3>

* Java
* Java Swing
* Event-Driven Programming
* Object Oriented Programming
 </br>

🚀 How to Run

1 Clone the repository

- git clone https://github.com/Vansh0129/InfoBytes_projects.git
- Open the project in IntelliJ / Eclipse / VS Code
- Inside NumberGuess pakage.

2 Run the main class:

- javaApp.java

3 Enter player details and start the game.
