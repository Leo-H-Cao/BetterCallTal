* player/model
    * Shown in UML diagram
    * Board contains collection of tiles
    * Each tile contains piece(s)
    * Pieces each handle thier own movement/captures, which can be modified with movement modifiers
    * Open to new piece types, different board sizes, >2 teams, etc. without modifying existing code
    * Use of JSON files
        * Create pieces with custom movement
        * Create pieces with custom point values
        * Create different "king" piece
        * Boards with different sizes
        * Games with different number of players
        * Games with different win conditions
        * Flexible inheritance hierarchy
        * Use of Reflection to create objects based on data files without using large, unmaintable if statements
* Frontend
    * The frontend will have a continuous, action-event based set up
        * The user will be able to navigate to both the create game end of the project and the play game end of the project from within the same view
    * Game Editor End
        * The game editor UI will have all the options available to the user easily accessible
        * The back end of the game editor will update information of the game file as the user creates the game.
        * When the user presses Apply and Download button, the information will be converted to a JSon on the backend and saved into their choice of directory
        * This design plan will be easy to use and hard to misuse for the user.
        * The user will also have the option to continue editing, which will come in the form of a prompt for a previously developed JSon file.
        * Error throwing will take place on backend.
    * Play Game End
        * The play game side of the project will take the form of the user inputting a JSon file from a prompt.
        * The updating of the board during the game will come from the Server sending JSon files to the backend, which will parse and set up board for each move
            * This will require an updateBoard() API on the frontend with all the relevant info
        * This design structure will also be easy to use and hard to misuse, as the user will only be concerned about uploading a JSon.

* editor/model
    * Shown in UML diagram
    * Editor frontend will communicate mostly with ModelState class, since it "owns" the instances of most of the components (pieces, board, rules)
        * contains the necessary API calls to modify pieces, board, rules
    * Similar to player backend, to track changes that user makes, board contains a collection of tiles, each tile contains piece
    * Other classes used for external API include Validator to validate user input for certain settings, and GameDisplayOptions which manages color theme for the application and board
        * Validator throws errors if input is not valid
    * Manager exports JSON for use by player
    