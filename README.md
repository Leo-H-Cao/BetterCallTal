README Better Call Tal
====

This project implements an authoring environment and player for multiple related games.

Names:
Vincent Chen
Luke McSween
Jed Yang
Ritvik Janamsetty
Nolan Gelinas
Leo Cao
Jose Santillan

### Timeline

Start Date:
3/21/22
Finish Date:
4/29/22
Hours Spent:
* Vincent Chen: 40-60 hours
* Luke McSween: 40 hours
* Jed Yang: 45 hours
* Ritvik: 35 hours
* Nolan Gelinas: 40 hours
* Leo Cao: 40 hours
* Jose Santillan: 30 hours
### Primary Roles
* Luke:
    * Handled GameView Frontend, namely the Game package within FrontEnd (with Jose's help) as well as the HostGame and JoinGame
    * Constructed flexible FrontEnd APIs that BackEnd (Game Engine) could interact with to update the board properly
* Jed:
    * Wrote basic implementation of board setup from JSON file
    * Helped design abstractions for game logic: valid state checkers and game end conditions
    * Implemented a simple AI that interfafces smoothly with the game API
* Jose - Gameplayer Backend + LocalPlay Frontend
    * Impleneted some end conditions as well as tile modifications
    * Implemented the LocalPlay menu as well as it's functionality
    * Designed and Implemented the HistoryManager that allows a player to go back in time without modifying the game and also the History Display
    * Helped implement the AI functionality into the Frontend
* Leo - Editor backend
    * Implemented classes to track user changes to game configurations in editor and exporting the configurations into JSON to be loaded into game
    * Designed abstractions to keep different aspects of game options (such as pieces information, board information, game rules) separate from each other
* Nolan - Editor frontend
    * Implemented View and NodeContainer in the editor frontend
    * This structure helped separate each component of the editor into different classes, which made my code cleaner
* Ritvik - Server + Turn/History Managment
    * Created server implementation of the game using Spring
    * Wrote design abstractions to standardize interactions of the chessboard the same with local and server interactions
    * Created History and Turn Manager abstractions
* Vincent - game engine
    * Helped design abstractions that allow many variations
    * Finalized JSON format and parsing class
    * Worked with frontend on features like history/notation and promotion popup
    * Implemented variations of the basic chess game, including crazyhouse and connect four
    * Wrote tests for game engine classes

### Resources Used


### Running the Program

Main class:
* Server.ServerApplication
* Main

Data files needed:
* doc.GameEngineResources
* doc.games
* doc.testing_directory
* resources.images
* resources.oogasalad

Features implemented:
* Game engine
    * Support for custom movements
        * Basic ones can be defined by data only with no extra code
    * Support for custom pieces
        * Completely data-driven
    * Support for custom movement modifiers
        * e.g. atomic
        * Can be configured with their own mini-config files
    * Support for custom end conditions
        * e.g. antichess
        * Supports multiple players
        * Can be configured with their own mini-config files
    * Support for custom turn criteria
        * e.g. constantly increasing
        * Supports multiple players
    * Support for custom tiles
        * e.g. promotion
        * Custom images, actions
        * Can be configured with their own mini-config files
    * Support for custom valid state checkers
        * e.g. check
        * Can be configured with their own mini-config files
    * Support for AI
        * 3 gamemodes: easy, medium, hard
        * AI makes moves using a set of guidelines given by the end conditions of the game being played
* Server
    * Support for multiple games
        * Multiple games can be run at once and managed
    * Support for History and Turn Management
        * History and Turn management works exactly the same locally and remote
* Editor Backend
    * Board
        * Board size editing
            * Placed pieces don't get removed
        * Settings special effects on board tiles such as promotion, blackhole, etc.
        * Setting starting locations of pieces
    * Pieces
        * Changing piece movement using a movement grid
        * Set piece as main piece (objective of game)
        * Set custom image of piece
        * Change piece name, point value
        * Allow custom moves for piece such as double first move, en passant
        * Set on interaction modifiers for piece such as "atomic"
    * Game rules
        * Change win conditions (checkmate, stalemate, etc.)
        * Change turn criteria, valid state checkers (relevant for game engine backend)
    * Export game settings to JSON

* Frontend
    * Editor
        * Board editor
            * Create custom sized board
            * Place selected piece on the board
            * View default piece movements or edit these movements by right clicking
        * Piece editor
            * Change piece movement locations
            * Change piece name
        * All updates to custom pieces automatically propigate to all pieces with that piece id that have already been placed on the board, such as the piece image or name
            * This allows our editor to feel more fluid becayse it doesn't require users to save or load files to the modifiy pieces
    * Game Player
        * BoardGrid
            * Clicking on a tile that contains a piece lights up the border of all the possible tiles that piece can move to
                * take into account turn criteria, legal moves, etc, all logic handled by engine
            * Clicking on a tile whose border is lit up will move the piece originally clicked to light it up to that lit up tile
            * Any click to a tile that is not lit up will turn off all previously lit up tiles
        * BoardTile
            * BoardTiles can hold a single piece/piece modifier and/or a tile modifier
            * Each turn the tiles that have changed are completely updated from scratch, no logic involved on frontend side
        * Board History
            * Each move made is recorded in a history scroll pane
            * pressing 'a' button will reverse the board state by 1 move, pressing 'd' will go forward a single move in board history
                * attempting to make a move in a state that is not the current state throws an error.
    * Host game
        * Upload any JSON file
        * Choose color they would like to start with
        * Choose piece image package they would like to use locally
        * Enter a RoomID for the server to use (other player will enter RoomID to join that game)
        * Press Confirm button, if RoomID is not an empty string then start button will appear
        * Pressing start will send the JSON file and all selected information to other parts of program to initialize game view
            * Error message shown for:
                * incorrect JSON
                * Unable to connect to server
                * Server Room taken
                * if piece isn't in either selected package or default package
    * Join game
        * User enters non-empty-string Server Room ID
        * Select piece image package to be used locally
        * Clicks start to join
            * Errors thrown for server issues
    * Other
        * Easily add new langauges by simply creating a new properties file in resources/


### Notes/Assumptions

Assumptions or Simplifications:
* Game engine
    * The board will only ever have square tiles
        * Removes logic needed for determining neighbors
        * Makes looping/streaming over the board easier
    * A movement can only happen on a piece
        * Can't create pieces out of thin air in a move - a piece must be "selected" to move
    * The king is able to castle out of check and through check
        * Adding this logic would make the castling class even messier than it is now and would require additional testing. Since this is a relatively minor feature, we decided not to pursue it.
* Server
    * Server runs only on the local host
        * Server can only be run on the same computer between two different instances of the app on the same computer
    * Server has no checking for verification
        * Somone, with access to the API can submit any board and, currently, there is no anti-cheating checker.
* Editor
    * Piece names must not be blank
        * This is a side effect of the name updating mechanism for the frontend; decided it wasn't a big enough deal to spend time fixing
    * Users cannot create their own "on interaction modifiers", "turn criteria", "end conditions", they are choosing from existing ones
    * Only main game configurations JSON is exported to location that user chooses, other movement files, pieces files are automatically put into Game Engine Resources directory


* Frontend
    * Language Resource Bundle
    * Error messages only in English because some error messages need to be formatted and we didn't have enough time to think of a work around
    * Board assumes only type of interaction with board would be selecting piece to move and moving piece
    * Board assumes only one piece/piece modifier/tile modifier will be on tile at any given time (ie two of same type not supported due to javaFX limitations, but a piece and a tile modifier, for example, is supported)
    *

### Interesting data files:
* Crazyhouse
    * Part of the board is used as a piece bank
* Nonsquare
    * Board shape is not a rectangle
* Chutes and ladders
    * Not even chess
    * Teleporting tiles
* ConnectFour and ConnectFourRotated
    * Also not even chess
    * Shows how a game can be modified with just data file changes

### Known Bugs:
* Promotion tiles are team agnostic, so if somehow a pawn manages to move backwards (e.g. in the move absorption gamemode), it will be able to promote on the 1st rank and 8th rank, instead of just on the 8th rank.
* Modifier placement not yet implemented
* Server
    * When no move is made and both the host and guests join the game
    * The gameover message on the loosing board shows an error message rather than the default message
    * Custom images do not work on the join game. All images need to be in the pieces/default section
    * Chutes and Ladders does not work on the server due to JSON issues.

Extensions completed:
* AI
    * Uses the minimax algorithm to search the solution space up to a certain depth. Hard difficulty searches up to depth 2: two moves ahead.
    * Based on the game's end conditions, the AI uses a different set of functions to evaluate board positions. For example, the objetive of the chess variant King of the Hill is to for the player's king to reach the center of the board. Thus, an AI instance generated to play King of the Hill will take into account the distance of the players' king from the center of the board, as well as the total piece value and avoiding checkmate (two other conditions relevant to this variant.)
* Server
    * Connects two instances of the game using a Spring Boot server on the localhost. Has a centralized history and turn management that works the exact same way between history and turn management.

### Impressions
* Assignment was generally enjoyable and we were able to use many of the concepts we learned in class as part of a large, final project we are proud of.