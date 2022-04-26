## Description

AI makes bad moves and occasionally crashes

## Expected Behavior

AI should be able to handle special game conditions without errors. It should evaluate positions logically
up to a depth of 2.

## Current Behavior

AI crashes when it detects special conditions such as check, capture, or checkmate. AI does not make
logical moves on hard difficulty.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. Run BetterCallTal -> Local Game -> SinglePlayer (AI) -> Hard -> Player 1
2. Upload the file doc/testing_directory/AI_Testing/MateInOne.json
3. Move a pawn up one square to a3. The game crashes >99% of the time.

## Failure Logs

Error popup says "WrongPlayerException: Expected: 0 Actual: 1"

## Hypothesis for Fixing the Bug

This bug is most likely caused by incorrect turn management in the AI. While testing moves, the AI should
only simulate turns being made and not modify the board's original turn state.