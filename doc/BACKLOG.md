* Use cases
    * Server
        * Receives request to create a game room
        * Receives request to join active game
            * Received request to join a game not yet set up
            * Sends required files to connecting client backend
        * Handle user disconnection/reconnection
        * Player tries to play a turn twice
            * Handle errer response on client
        * Records history
            * Scrub forward and past through the history
        * Get current player status
        * Gets rules of the game
        * Manage multiple games
        * Gets the properties of the game
        * Create web APIs
        * Run code on server and localhost
    * Game Engine
        * Parse JSON objects
        * Make web API calls
        * Encode JSON objects
        * Move bishop
        * Move a piece with custom movement
        * Capture piece with knight
        * Capture piece with pawn
        * Move pawn two squares on first move
        * Light up valid moves check piece selected
        * Promote pawn to queen
        * Move knight
        * Capture piece in atomic chess (all pieces around explode)
        * Move a piece when it lands on a blackhole tile
        * Create a custom piece that moves like knight+queen
        * Move knight+queen like a knight
        * Move knight+queen like a queen
        * en passant
        * Promote pawn to custom piece
        * Set piece on h3 (crazyhouse)
        * Offer draw
        * Resign
        * Pause clock
        * Add time to clock
        * Make illegal move
            * King in check
            * King in checkmate
            * King in stalemate
            * King moves into check
        * Misc
            * User rage quits (client disconnects unexpectedly)
    * Editor
        * Upload custom piece image
        * Copy placed piece/modifier
        * Customize board dimensions
            * User adds a row
            * User adds a modifier to a tile
            * User deletes a square
        * Drag and drop pieces from the library to their starting location
        * Create custom piece
            * Sets piece movement
            * Sets piece starting position
            * Sets piece modifier
        * Create power up
        * Change color themes of game display
        * Set new game objective
        * Change piece movement
        * Place power up for a tile
        * Powerups (placed on a tile on the board)
            * NO PIECE power up
            * Make Black Hole Square
            * Make Burn square (piece dies after X amount of consecutive turns on square)
            * Add Teleportation Power up to piece
            * Add Atomic Power up to piece
            * Add Gravity Power up to piece
        * Modifiers (for modifying custom piece behavior)
            * Game objective (triggers loss when piece dies)
            * Jumping (can go to destination with no path)
            * Invisibility (make piece only visable to you)
        * Misc
            * Host a game
                * Set the name of the room
                * Select game-wide modifiers (atomic)
            * Join a game
                * Enter host's game code
            * User wants to download a file they just created
            * User selects Spanish
            * User wants to continue editing a game
            * User clicks invalid square
            * User sets Application Style sheets (dark mode)
            * User wants to make the a2 pawn the winning piece