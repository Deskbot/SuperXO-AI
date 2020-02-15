package xyz.thomasrichards.superxo;

import xyz.thomasrichards.superxo.ai.*;
import xyz.thomasrichards.superxo.game.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Controller controllerX = null;
		Controller controllerO = null;
		int rounds = 1;

		//resolving arguments

		//checking for help option

		String argL;
		for (String arg : args) {
			argL = arg.toLowerCase();

			if (argL.equals("-h") || argL.equals("--help")) {
				System.out.println(
					"Arguments:\n" +
					"two arguments that are either of the following\n" +
					"[-a | --ai] [agent int] [depth of search int]\n" +
					"[-p | --player]\n" +
					"[-r | --rounds] [quantity int]"
				);
				System.exit(0);
			}
		}

		try {
			Agent thisAgent;
			int aiNum;
			int depth;
			int i = -1; //first use of i is repeated and so needs to be ++i, -1 ensures the first usage is 0
			Player thisPlayer;

			// looks for agent type twice

			int playerNum = 1;

			while (i < args.length - 1) {
				thisPlayer = playerNum == 1 ? Player.X : Player.O;

				switch (args[++i]) {
					case "-a":
					case "--ai":
						try {
							aiNum = Integer.parseInt(args[++i]);
							depth = Integer.parseInt(args[++i]);
							thisAgent = makeNewAgent(aiNum, depth, thisPlayer);
							if (thisAgent == null) throw new NumberFormatException();
							if (playerNum == 1) controllerX = new AIController(thisAgent);
							else                controllerO = new AIController(thisAgent);

						} catch (NumberFormatException e) {
							System.err.println("Error: Invalid AI number given");
							System.exit(1);
							return;
						}
						playerNum++;
						break;

					case "-p":
					case "--player":
						if (playerNum == 1) controllerX = new HumanController(thisPlayer);
						else                controllerO = new HumanController(thisPlayer);
						playerNum++;
						break;

					case "-r":
					case "--rounds":
						rounds = Integer.parseInt(args[++i]);
						break;
					default:
						System.err.println("Argument " + args[i] + " was not understood.");
						System.exit(1);
						return;
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Invalid arguments given");
			System.exit(1);
		}

		// play all rounds
		Map<Player, Integer> wins = new HashMap<>();
		wins.put(Player.X, 0);
		wins.put(Player.O, 0);
		wins.put(null, 0);
		for (int currentRound = 0; currentRound < rounds; currentRound++) {
			Player winner = round(controllerX, controllerO);
			wins.put(winner, wins.get(winner) + 1);
		}

		if (rounds > 1) {
			System.out.println(Player.X + " won " + wins.get(Player.X));
			System.out.println(Player.O + " won " + wins.get(Player.O));
			System.out.println("Draws " + wins.get(null));
		}
	}

	private static Agent makeNewAgent(int aiNum, int depth, Player player) {
		switch(aiNum) {
			case 1: return new Agent1Shallow(player, depth);
			case 2: return new Agent2(player, depth);
			case 3: return new Agent3(player, depth);
			case 4: return new Agent4(player, depth);
			case 5: return new Agent5(player, depth);
			case 6: return new Agent6(player, depth);
			case 7: return new Agent7(player, depth);
			case 8: return new Agent1(player, depth);
			case 9: return new Agent6Fixed(player, depth);
			case 10: return new Agent6FixedAgain(player, depth);
		}

		return null;
	}

	private static Player round(Controller controllerX, Controller controllerO) {
		Game g = new Game();
		Controller currentActor;
		Move move;
		Player whoJustMoved;

		while (!(g.isWon() || g.isDraw())) {
			currentActor = g.getTurnPlayer() == Player.X ? controllerX : controllerO;

			move = currentActor.chooseMove(g);
			whoJustMoved = g.getTurnPlayer();

			try {
				g.inputTurn(move);
				System.out.println(whoJustMoved + "," + move.getGridPos() + "," + move.getCellPos());
			} catch(InvalidMoveException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		System.out.println(g.isWon() ? "Winner: " + g.getWinner() : "Draw");

		return g.getWinner();
	}
}

interface Controller {
	Move chooseMove(Game g);
}

class HumanController implements Controller {
	private final Player player;

	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	HumanController(Player p) {
		this.player = p;
	}

	public Move chooseMove(Game g) {
		Position gridPos, cellPos;
		String[] input;

		while (true) {
			try {
				input = br.readLine().split(",");
				gridPos = Position.valueOf(input[0]);
				cellPos = Position.valueOf(input[1]);
				return new Move(this.player, gridPos, cellPos);

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);

			} catch (IllegalArgumentException e) {
				System.err.println("Error: Invalid move syntax");
			}
		}
	}
}

class AIController implements Controller {
	private final Agent agent;

	AIController(Agent a) {
		this.agent = a;
	}

	public Move chooseMove(Game g) {
		return this.agent.chooseMove(g);
	}
}