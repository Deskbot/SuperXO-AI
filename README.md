Super Noughts and Crosses (plus AI)
===================================
This repository contains code for running games of Super Noughts and Crosses in an un-user friendly fashion via IO streams. It also contains some intelligent agents that can play the game too.

How to Play
-----------

The best way to learn is to play the game at [thomasrichards.xyz/SuperXO](http://thomasrichards.xyz/SuperXO).

You are trying to win the big game of noughts and crosses. You can only fill in a cell by winning the game within it.

Players take turns to make a move in a small game. The position of the cell you chose in a small game is the position of the small game in the big game where the next player must choose a move.

If there is no valid place to move in the small game you're assigned, you can make your move anywhere.

Install \& Build
----------------

```
git clone https://github.com/Deskbot/SuperXO-AI
cd SuperXO-AI
mkdir out
./compile.sh
```

Run
---

```
cd SuperXO-AI
java -cp out xyz.thomasrichards.superxo.Main [args]
```

### Arguments
* `-h`: tells you what the arguments should be

The arguments should be 2 instances of either of the following:

* `[-a | --ai] [agent number] [difficulty int]`
* `[-p | --player]`

The leftmost argument is player X who goes first. The rightmost argument is player O who goes second.

i.e. it is possible to play human against human or ai against ai.

Update
------

```
git pull
```

IO Specification
----------------

Positions of cells and grids are represented by: `TL,TM,TR,ML,MM,MR,BL,BM,BR`, which should be self-explanatory.

Moves are written as a pair of comma separated positions, where the left position is of a 3x3 grid and the right position is of the cell within that grid, followed by a new line character.

e.g. `TL,BR\n` or `MM,MM\n`

### stdin

Player chosen moves can be sent in. This move is executed for the current turn player.

### stdout

For each move made whether by a human or AI: The symbol of that player, followed by a comma, followed by the move is sent out, followed by a new line character.

e.g. `X,TL,BR\n` or `O,BR,MM\n`

When a player wins, `Winner: ` is displayed followed by the symbol of the winner.

### stderr

All expected errors begin with `Error: ` followed by the reason for the message, then a new line charcter.

Note
----

At level 4 difficulty, during a game, Java ran out of heap space on my machine because each level is an extra level of depth in the search tree and for moves with a lot of choices this causes problems.
