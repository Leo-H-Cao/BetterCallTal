# DESIGN Document for Better Call Tal
## Luke, Jed, Jose, Leo, Nolan, Ritvik, Vincent


## Role(s)

* Luke - Game FrontEnd
    * FrontEnd for GameView (BoardGrid, BoardTile, most of Game folder in FrontEnd directory)
    * Frontend for HostGame and Join Game screens in menu view
* Jed
    * Wrote basic implementation of board setup from JSON file
    * Helped design abstractions for game logic: valid state checkers and game end conditions
    * Implemented a simple AI player that interfafces smoothly with the game API
* Jose
    * Impleneted some end conditions as well as tile modifications
    * Implemented the LocalPlay menu as well as it's functionality
    * Designed and Implemented the HistoryManager that allows a player to go back in time without modifying the game and also the History Display
    * Helped implement the AI functionality into the Frontend
* Leo - Editor backend
    * Implemented classes to track user changes to game configurations in editor and exporting the configurations into JSON to be loaded into game
    * Designed abstractions to keep different aspects of game options (such as pieces information, board information, game rules) separate from each other
* Nolan Gelinas - Editor frontend
    * Created reusable frontend structure that allowed Luke and I to easily create new views and nodes within a view
    * Connected backend to frontend using encapsulation
    * Separated language selection from the rest of the application
* Ritvik: Server
    * Created abstractions to maange history and turns
    * Created a RESTful API using Spring Boot on a local host
    * Ensured remote/local games worked as similar as possible
    * Worked on implementation of fratures through BoardSetup and ChessBoard classes.
* Vincent - game engine
    * Helped design abstractions that allow many variations
    * Finalized JSON format and parsing class
    * Worked with frontend on features like history/notation and promotion popup
    * Implemented variations of the basic chess game, including crazyhouse and connect four
    * Wrote tests for game engine classes


## Design Goals
* Game engine
    * Maximum flexibility with rules
        * Custom pieces, movements
        * Custom turn criteria
        * Custom end conditions
        * Custom valid state checkers
        * Custom tile actions
        * Custom movement modifiers
    * Data-driven design
        * Certain movements can be created with just data file additions
        * Custom classes can be configured with config files
    * Ease of use by others
        * Custom and detailed exceptions
        * Easy to easy external API methods like `getMoves()` and `move()`
* Server
    * Easy to use and hard to miss use
        * The server should not crash under any circumstance and should be able to interface with the game
    * Uniform Design
        * The server implementation of the gameboard should be minimally be varing between the local and server implementations
* Editor
    * Backend
        * Maximize game options for user in line with the possible customizations supported by game engine
        * Easy to use API's for editor frontend to track changes in game configurations
        * Keep different areas of game options such as pieces, board, rules separate, so backend is easy to extend in terms of adding more possible configurations
* Frontend
    * Game FrontEnd
        * Create a User Interface that interacts smoothly and flexibly with the Game Engine
        * Board that can be interacted with
        * Tiles that can hold the correct JavaFX objects
        * Menu screens (Join, Host, Local)
            * Functionality for all three screens
            * As many frontend options as possible (image packages, starting color, AI, etc.)

## High-Level Design
* Game engine
    * ChessBoard class interfaces with frontend
        * Provides access to movements a piece can make
        * Provides information to update after a move is made
        * Tells frontend when a game is over and what the scores are
    * ChessBoard is constructed with:
        * Collection of end conditions that provide logic for when game ends and score
        * Turn criteria for determining whose turn it is
        * Chess tiles that contain pieces and special actions that can execute when a piece lands on the square
        * Custom valid state checkers that determine whether a move is valid
        * Custom pieces
            * Custom movements
            * Custom movement modifiers
            * Custom images
        * Parsing class that reads in files and instantiates items listed above
* Server
    * Contains a session, each of which contains a history manager and a turn manager
        * These objects are the same of the local implementation
    * These controllers connect with remote implementations in the GamePlayer
        * Provides a standardized way to interface with the chessboard class
        * Minimally varies with the local implementations
        * Communication is done using JSON using the Jackson JSON package
* Editor
    * Backend
        * PiecesState, GameRulesState interfaces with editor frontend to track any changes that the user makes to these game options
            * PiecesState manages all the default and custom piece information and movements
        * ExportJSON package has own set of objects that are to be serialized into the exported JSON file
        * BoardState abstracts underlying EditorBoard, hides private EditorBoard state and logic from front end


* Frontend
    * Editor
        * Allow users to place default and custom pieces on the board
        * Allow users to create custom pieces with custom movements and images
    * Game Player
        * The board should be completely agnostic of the type of game people played (nothing should change if shoots and latters or chess is played)
            * This allows maximum flexibility for the program's frontend, any future game design can be accomodated by the board
            * Instead of creating variants of tiles, like a chess tile meant for functionality of chess games and a shoots-and-ladders tile, we instead wanted to generalize this functionality and let the backend handle exactly what happens.
            * Given we were doing chess, we generalized the tile's functionality to get possible movement tiles and move piece to that tile.

## Assumptions or Simplifications
* Game engine
    * The board will only ever have square tiles
        * Removes logic needed for determining neighbors
        * Makes looping/streaming over the board easier
    * A movement can only happen on a piece
        * Can't create pieces out of thin air in a move - a piece must be "selected" to move
* Server
    * Server runs only on the local host
        * Server can only be run on the same computer between two different instances of the app on the same computer
    * Server has no checking for verification
        * Someone, with access to the API can submit any board and, currently, there is no anti-cheating checker.
* Editor
    * Piece names cannot be blank
    * All pieces must have an immutable unique id attached to it
    * Users cannot create their own "on interaction modifiers", "turn criteria", "end conditions", they are choosing from existing ones
    * Only main game configurations JSON is exported to location that user chooses, other movement files, pieces files are automatically put into Game Engine Resources directory

## Changes from the Plan
* Game engine
    * Flexibility allowed us to implement game modes we didn't think we could
        * Crazyhouse
        * Connect four
        * Chutes and ladders
    * Ahead of schedule - implement AI
* Server
    * It's not deployed on a remote server.
* Editor
    * Backend
        * Added additional single piece editing tracking API
        * No accessory game display options tracking, such as application theme colors, or board theme colors
        * Export JSON created a series of objects that hold the data that is to be exported when the objects are serialized to JSON
* Frontend
    * Game Player
        * To create a Frontend that smoothly interfaces with Engine
        * No Game logic completed in the frontend
        * Handle update requests smoothly
        * relay relavent user input information (clicked tiles, etc) succinctly with Game Engine in a clear, easy-to-use-hard-to-misuse manner
        * Hierarchy within Frontend should be logical and clean
            * BoardGrid is only class that interfaces with BoarTile, for example
            * GameView only class that directly interaces other parts of project with the components in the Game View.
    * Editor
        * Removed ability to reopen an edited piece due to added complexity of reparsing json
        * Opted for clicking on each tile instead of dragging pieces because its faster to use
## How to Add New Features
* New game mode with existing classes (e.g. crazyhouse with 10x10 board)
    * Add new board JSON
    * Add new configuration files if necessary
        * e.g. crazyhouse with 10x10 board would need new bank config file so the bank knows to start at col = 10
    * *NO* new code necessary
    * Frontend: As long as images remain in tile modifier, piece modifier, or piece categories, no update needed
* New basic movements (anything with a direction and a boolean 'extends infinitely')
    * Add data file for movement
    * Add piece with movement file name
    * Add board JSON using new piece
    * *NO* code additions
    * NO FrontEnd change (as long as logic for possible movement tiles is sound on backend, no change for frontend)
* New complex movement
    * Implement MovementInterface
    * Add piece/board JSON referencing class name
    * Add config files for new movement if necessary
    * NO Frontend change (see previous bullet point)
* New turn criteria
    * Implement TurnCriteria
    * Add board JSON referencing class name
    * NO Frontend changes
        * Turn Criteria is checked in backend, so if player clicks on piece whose turn it is not, no tiles will light up -> no move can be made
* New end conditions
    * Implement EndCondition interface
    * Add board JSON referencing class name
    * Add config files if necessary
    * NO Frontend change
        * each time move is made, Frontend checks to see if GameOver Boolean is true on backend, to which a game over display is shown
* New valid state checkers
    * Implement VSC
    * Add board JSON referencing class name
    * Add config files if necessary
    * NO Frontend change (for reasons described in previous bullet points)
* New tile action
    * Implement TileAction interface
    * Add board JSON referencing class name
    * Add config files if necessary
    * No Frontend change at all, as long as image files are in correct directory
* New movement modifiers
    * Implement MovementModifier
    * Add board JSON referencing class name
    * Add config files for if necessary
    * No Frontend change at all, as long as image files are in correct directory
* Configuring any of the new items above
    * Add new config file in GameEngineResources/Other
    * Add new board JSON referencing config file with associated class
    * *NO* new code needed
    * No Frontend change at all, as long as image files are in correct directory
* New language
    * Create new file in resources/oogasalad/Frontend/Menu/languages named "languageName.properties" where languageName is the display name for the newly added language
    * Ensure that all key/value pairs are copied from English.properties and translate all values to desired language
* New AI settings
    * Change depth of AI on each difficulty level by editing the file resources/oogasalad/GamePlayer/BotGameModes.properties
    * When creating new game end conditions, write the appropriate utility function for the AI under the ArtificialPlayer/UtilityFunctions directory. Match the end condition with the utility function in BotObjectives.properties. The AI generated for this new game will adjust its playing style to account for the new end condition.