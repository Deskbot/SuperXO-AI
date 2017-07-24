package xyz.thomasrichards.superxo;

import xyz.thomasrichards.superxo.ai.Agent;
import xyz.thomasrichards.superxo.ai.Agent1;
import xyz.thomasrichards.superxo.game.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

	public static void main(String[] args) {
		Controller controllerX = null;
		Controller controllerO = null;

		//resolving arguments

		String argL;
		for (String arg : args) {
			argL = arg.toLowerCase();

			if (argL.equals("-h") || argL.equals("--help")) {
				System.out.println(
						"Arguments:\n" +
								"two arguments that are either of the following\n" +
								"-a | --ai\n" +
								"-p | --player\n"
				);
				System.exit(0);
			}
		}

		try {
			int i = 0;
			int depth;

			switch (args[i]) {
				case "-a":
				case "--ai":
					depth = Integer.parseInt(args[++i]);
					controllerX = new AIController(new Agent1(Player.X, depth));
					break;
				case "-p":
				case "--player":
					controllerX = new HumanController(Player.X);
					break;
				default:
					System.err.println("Argument " + args[i] + " was not understood.");
					System.exit(1);
			}

			switch (args[++i]) {
				case "-a":
				case "--ai":
					depth = Integer.parseInt(args[++i]);
					controllerO = new AIController(new Agent1(Player.O, depth));
					break;
				case "-p":
				case "--player":
					controllerO = new HumanController(Player.O);
					break;
				default:
					System.err.println("Argument " + args[i] + " was not understood.");
					System.exit(1);
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Invalid arguments given");
			System.exit(1);
		}

		//game start up

		Game g = new Game();
		Controller currentActor;
		Move move;

		while (!g.isWon()) {
			if (g.getTurnPlayer() == Player.X) {
				currentActor = controllerX;
			} else {
				currentActor = controllerO;
			}

			move = currentActor.chooseMove(g);
			System.out.println(move.getGridPos() + "," + move.getCellPos());

			try {
				g.inputTurn(move);
			} catch(InvalidMoveException e) {
				System.err.println("Error: Invalid move");
			}
		}

		System.out.println(g.getWinner());
	}
}

interface Controller {
	Move chooseMove(Game g);
}

class HumanController implements Controller {
	private Player player;

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	HumanController(Player p) {
		this.player = p;
	}

	public Move chooseMove(Game g) {
		Position gridPos = null;
		Position cellPos = null;

		while (true) {
			try {
				String[] input = br.readLine().split(",");
				gridPos = Position.valueOf(input[0]);
				cellPos = Position.valueOf(input[1]);
				return new Move(this.player, gridPos, cellPos);

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);

			} catch (IllegalArgumentException e) {
				System.err.println("Error: invalid position either " + gridPos + " or " + cellPos);
			}
		}
	}
}

class AIController implements Controller {
	private Agent agent;

	AIController(Agent a) {
		this.agent = a;
	}

	public Move chooseMove(Game g) {
		return this.agent.chooseMove(g);
	}
}