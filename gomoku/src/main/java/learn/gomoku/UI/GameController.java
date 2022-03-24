package learn.gomoku.UI;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
import learn.gomoku.game.Stone;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameController {
    private final Scanner sc = new Scanner(System.in);
    Gomoku game;
    char[][] board = new char[15][15];
    private static Player player1;
    private static Player player2;


    public void run() {
        System.out.println("Welcome to Gomoku");
        System.out.println("=================");

        boolean end = false;

        while (end == false) {
            getPlayer();
            this.game = new Gomoku(player1, player2);
            ArrayList<Stone> stones = new ArrayList<>();
            char[][] board = new char[15][15];
            System.out.println("Randomizing..");
            System.out.println(game.getCurrent().getName() + " is going first");
            play();
            playAgain();

        }
    }

    private void getPlayer() {
        System.out.println("Player 1 is:\n" +
                "1. Human\n" +
                "2. Random Player\n" +
                "Select [1-2]: ");
        int input1 = Integer.parseInt(sc.nextLine());
        if (input1 == 1) {
            String name = readRequiredString("Your name? ");
            player1 = new HumanPlayer(name);
        } else {
            player1 = new RandomPlayer();
            System.out.println("Random player name is: " + player1.getName());
        }
        System.out.println("Player 2 is:\n" +
                "1. Human\n" +
                "2. Random Player\n" +
                "Select [1-2]: ");
        int input2 = Integer.parseInt(sc.nextLine());
        if (input2 == 1) {
            String name = readRequiredString("Your name? ");
            player2 = new HumanPlayer(name);
        } else {
            player2 = new RandomPlayer();
            System.out.println("Random player name is: " + player2.getName());
        }

    }

    private void play() {
        do {
            Stone stone = game.getCurrent().generateMove(game.getStones());
            Result result = null;
            if (stone == null) {
                int row = readInt("Enters row 1-15", 1, 15);
                int col = readInt("Enters row 1-15", 1, 15);
                stone = new Stone(row - 1, col - 1, game.isBlacksTurn());
                result = game.place(stone);

            } else {
                result = game.place(stone);
            }
            if (!result.isSuccess()) {
                System.out.println(result.getMessage());
            }
            printBoard();
        } while (!game.isOver());
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println(winner.getName() + " wins!!!!!!");
            System.out.println("*****************");
        }
    }

    private void printBoard() {
        printColumn();
        System.out.println("");
        printRow();

    }

    private void printColumn() {
        System.out.println("  ");
        for (int columnIndex = 0; columnIndex < Gomoku.WIDTH; columnIndex++) {
            System.out.printf(" %02d", columnIndex + 1);
        }
    }

    private void printRow() {
        for (int row = 0; row < Gomoku.WIDTH; row++) {
            for (int col = 0; col < Gomoku.WIDTH; col++) {
                board[row][col] = '-';
            }
        }

        for (Stone stone : this.game.getStones()) {
            board[stone.getRow()][stone.getColumn()] = stone.isBlack() ? 'X' : 'O';

        }

        for (int row = 0; row < Gomoku.WIDTH; row++) {
            System.out.printf("%02d ", (row + 1));
            for (int col = 0; col < Gomoku.WIDTH; col++) {
                System.out.print(" " + board[row][col] + " ");
            }
            System.out.println();
        }
    }

    private String readRequiredString(String message) {
        System.out.println(message);
        String input = sc.nextLine();
        while (input.equals("")) {
            System.out.println("Invalid entry...");
            input = sc.nextLine();
        }
        return input;
    }

    private int readInt(String message, int row, int col) {
        String input = readRequiredString(message);
        int placeHolder = Integer.parseInt(input);
        while (placeHolder < row || placeHolder > col) {
            input = readRequiredString("Outside of the bounds..");
            placeHolder = Integer.parseInt(input);
        }
        return placeHolder;
    }

    private boolean playAgain() {
        Scanner console = new Scanner(System.in);
        String input = readRequiredString("Play Again? [Y / N]: ");
        boolean response = false;
        if (input.equalsIgnoreCase("y")) {
            response = true;
        } else if (input.equalsIgnoreCase("n")) {
            response = false;
        } else {
            System.out.println("Enter Y or N");
            playAgain();
        }
        return response;
    }
}

