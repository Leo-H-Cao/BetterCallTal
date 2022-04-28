## Description
Selecting a package other than default would break the program.

## Expected Behavior
If package is selected, that package should be used. If the image isn't found, 
it should check the Default package to see if it's there. If it's not in default, 
it should show an error message.

## Current Behavior
Prints error to console

## Steps to Reproduce
1. Open Local Game
2. Click Fun package (or any package besides Default)
3. Click done or start

## Failure Logs
Exception in thread "JavaFX Application Thread" java.lang.NullPointerException: Cannot invoke "BoardTile.ErrorRun.accept()" because "this.ErrorRun" is null

## Hypothesis for Fixing the Bug
initializing the ErrorRun biconsumer. 

## NOTE
This error was due to a last second implementation. 