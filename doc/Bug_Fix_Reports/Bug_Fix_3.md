## Description
Clicking export causes an error due to a missing resource bundle; likely due to renaming a file or class

## Expected Behavior
Click the export button in the editor pops up a file save location selector

## Current Behavior
Prints error to console

## Steps to Reproduce
 1. Open editor
 2. Click export

## Failure Logs
Exception in thread "JavaFX Application Thread" java.lang.NullPointerException: Cannot invoke "java.util.ResourceBundle.getString(String)" because "this.resources" is null

## Hypothesis for Fixing the Bug
Finding correct resource bundle and rename it

## NOTE
The test to fix this bug already existed and was failing on Sunday night. I was not aware of this until Wednesday, when I implemented this fix. This is why there was only one commit for this bug fix instead of two.
