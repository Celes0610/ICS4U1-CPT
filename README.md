# ICS4U1-CPT
# Snake Game

 Overview
This is a two-player Snake game implemented in Java using Swing for the GUI and SuperSocketMaster for networking. The game allows one player to host a server while another joins as a client. Host can select difficulty levels, themes, and chat with each other during gameplay.

 Features
- Two-player online gameplay: Host and join functionality using sockets.
- Difficulty settings: Easy, Normal, Hard.
- Themes: Star Wars, Zelda.
- In-game chat: Players can send messages to each other.
- Dynamic food spawning: Food appears randomly on the map.
- Real-time movement: Smooth animation and snake movement.

 Getting Started
1. Starting the Game:
    - The first player clicks on `Create Server` to host the game. The game generates a random port number and displays the IP address.
    - The second player enters the IP address and port number, then clicks `Join Server`.

2. Setting Up:
    - Enter a username.
    - Choose a difficulty level: Easy, Normal, Hard.
    - Select a theme: Star Wars or Zelda.

3. Starting the Game:
    - Click `Ready` to start the game once both players are ready.

4. Playing the Game:
    - Control your snake using `W`, `A`, `S`, `D` keys.
    - The objective is to eat the food and grow your snake while avoiding collisions with the walls or the other snake.
    - Use the chat feature to communicate with the other player.

Contributor
- Ethan Lau, Celestine Lee, Rightruach Thai

Acknowledgements
- SuperSocketMaster: For handling socket communication.
- Java Swing: For the graphical user interface.
