This is just to note things that I did for the game.

Added a DialogueSystem class to have preset settings for every line of dialogue and the Atext class.  Has a method to call a range of text lines.  Also has dialogue flags and item flags to track the game state whenever you save the game.  Items that you picked up will be saved, and dialogue will continue from where you left off in the story (not during the dialogue phrase, so you can only save in gameplay mode).

Aded a RectSystem class to call the text files full of number presets.  Has logic for dialogue flags so the story can continue without issue.

Interpreter class has logic for rects, spritesheet subimage coordinates, background, and arrow navigation in gameplay mode.

Music and art was created by me.  I had random unfinished music in my drive, so I finished it for this project.  Feedback on it would be appreciated.  Hope you enjoy!