* player/model
    * Custom exceptions
        * e.g. OutsideOfBoardException
    * Testable functions with return values
        * e.g. move() returns boolean
        * e.g. captureCriteria() returns boolean
    * Test scenarios
        * Move rook horizontally (happy)
        * Capture piece with piece (happy)
        * Move when King is in check (happy)
        * Move piece outside of board (sad)
        * Move rook through another piece (sad)
* player/view
    * Pieces/Board
        * Happy testing
            * On click the correct tiles should light up
            * Clicking a lit up tile will move the piece that made the tile light up
        * Sad testing
            * User shouldn't be able to make an error from the GameView, thus no errors to test
                * Action-Based program, so nothing will happen in program unless we write a method to support it
    * Modifier view testing
        * Happy Testing
            * Make board go through iterations that activate a modifer, check to see if stage updated correctly
        * Sad Testing
            * Again errors should be impossible, so no sad testing.
    * Clock testing
        * Make sure the clocks properly start and stop correctly.
    * Reaction Testing
        * Make sure the reaction emojis are popping up
    * Move History Testing
        * Make sure the move history screen is updating correctly
* editor/model
    * Custom exceptions
        * e.g. InvalidBoardSizeException
    * Testable functions with return values
        * exportJSON() returns boolean
        * checkBoardSizeValidity() returns boolean
        * removePiece() returns removed piece
        * removePieceModifier() returns removed modifier
    * Test scenarios
        * Check creation of new piece
            * Test if all info involved with this new piece such as its image, color, movement rules, is correctly sent to the server/communicated with the game backend
        * Check invalid user input functionality (sad)
            * Assuming our program will have restrictions on what the user can configure for the game, such as a limit on how big the board can be, test if program displays an error message when user puts in a restricted value like a board size that is too big
        * Check edit game rules functionality
            * Test if the new set of rules defined by user is processed and sent to server accurately/in the correct format

* editor/view
    * Custom Board
        * Test that the column and row expansions are working properly
            * Happy: expand a valid number
            * Sad: expand an invalid number and check for error message
        * Test that pieces can be placed correctly
        * Test that the modifiers can be added to the tiles correctly
    * Custom Pieces
        * Test editing squares available
        * Test modifier added correctly
    * Toggle between tabs
        * Test that tabs change correctly
    * Pieces History
        * Test that board history is maintained correctly