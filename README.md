[04.04.25]
Please, I'll write this here again. DON'T BE STRICT ABOUT MY CODE. I HAVE JUST STARTED LEARNING ALL THOSE THINGS.

P.S 
Sprites were made very fast. And It's fourth time I rewrited my code :D

[06.04.25]
Okay. So, I continue making the game. Made some random generators of different staff in different tiles. Especially on grass - deco, like flowers and plants, and random tree generator. Also added on sand tile random generator of cactuses. That's really cool and fun to make stuff like that. I will continue working on this game and continue learning that type of stuff. 

[15.04.25] 
Stay on track. Tasks now getting pretty tought to make and understand. Basically, I use AI the most, because find those tasks that I exactly want to do in youtube is really hard one. So, what about changes? I made mobs - simple, silly entities with random movement. One problem just kicked my ass off really hard - I needed to made collisions with entities work normally, but after each attempt they kept getting stucked at each other after collision, so, yeah, this was really complicated one. Also, as you can see, changed my player sprites, because slime was really kinda stupid and spontaneous idea to draw. To be honest, just stole from one of the notch's game, called "Minicraft" (I'm also trying to learn something watching his saved streams on youtube). Another one obstacle in my way is school. I need to focus on some subjects, where I have a really big problems, in reason of my absent for almost a two months. So, yeah, keep going, keep learning (sad enough to realise, that the process will be twice as slow).

[19.04.25]
Finally get to work and FINALLY made those camera borders. A little bit of explanation - when player camera reaches the border of the map, It will stop following the player, and player camera will go free. So, now here is the next tasks for future:

Bugs:
Fix every bug that actually interrupting player's gameplay or game's optimization.
Some of those: 
1. When player collide with mob while he is moving, seems like player not actually goes with him normally and he just slows the players movement (of course, the main problem is in reverting movement when player collide with entities)
2. Actually, we are setting up a new variable that randomize the mob spawning. Of course, they doesn't spawn on solid blocks like water, tree and so on. Basically, seems like, that we are setting levelX to tile size and only after checking that the entity is not in tree, water or in the player itself we are setting levelX to randomX variable, that chooses random tile on the whole level map. However, seems like, they actually spawning in 48 by 48 tile at the start of the map, which actually ruins the optimization in game.
3. Sometimes, entities can spawn on each other, that, actually, also interrupting game's optimization.

To do in future list:
1. Map generation by using Diamond-square algorithm
2. Attack implementation for the player
3. Damage receiving from the cactuses or other entities, when they collide the player

Probably, that is all for today, keep working and trying to learn something while doing this stuff. I hope, It works and I'm ACTUALLY learning

[20.04.25] 
So, fixed two of the provided above bugs. Now, I need to focus on my goals, so that I can learn a lot of new stuff again here. KEEP ON TRACK!!11!!!11!!
