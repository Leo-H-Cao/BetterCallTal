* view/model
* view/editor
    * Test
        * Backend
            * Game
                * Basic movement for all classic pieces
                * Detect check
                * Return board state after a move
            * Editor
                * Keeping track of changes made in front end, such as pieces starting position
                * Export JSON
        * Frontend
* player/model
    * Sprint 1
        * Basic movement for all pieces, including error checking for illegal moves
            * Detect checkmate, stalemate, draw
        * Capture piece
        * Promote pawn (also supports custom pieces)
        * Support loading custom pieces with combinations of standard movement
    * Sprint 2
        * Support creating pieces with custom movement
        * Custom board size and shape
        * Custom game rules (e.g. special squares)
    * Complete/Demo week
        * Implement all variations of chess described in EXAMPLE_GAMES.md
        * Resign, draw button support in backend
* editor/model
    * Sprint 1
        * Custom board size and shape
        * Placing pieces for starting position
        * JSON export
    * Sprint 2
        * Custom pieces and movement
        * Change game rules (piece modifiers, tile effects)
    * Complete/Demo week
        * Load in variations of chess
        * Additional customization functionality such as key input interactions