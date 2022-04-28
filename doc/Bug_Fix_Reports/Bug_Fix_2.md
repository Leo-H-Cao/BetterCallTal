## Description

When exporting game configurations to JSON, pieces of different team have same movements, and overall some formatting
of JSON export was changed 

## Expected Behavior

Pieces of different teams should have different movements set by user, and JSON export format is reverted to a version that aligns with the parser.

## Current Behavior

Pieces of different team have same movements, and JSON format does not work with game player parser

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. Create custom piece
2. Set different movements for custom piece of white team and black team
3. Export
4. Try to load local game with the exported JSON

## Failure Logs

JSON object mapper exception

## Hypothesis for Fixing the Bug

Make sure two piece files are exported for each custom piece, revert JSON formatting to previous version. Also
make sure to export the custom pieces' images so that parser does not have issues finding the image