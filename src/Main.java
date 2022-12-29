import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int MAX_GUESS = 100;
    private static final int MIN_GUESS = 0;
    private static final double MULTIPLY_CONSTANT = 0.8;
    public static int currentPlayers = 5;
    private static final String NAME_PROMPT = "Please enter your name:";
    private static final String WELCOME_PROMPT = "Welcome to the Balance Game";
    private static final String GUESS_PROMPT = "Please enter a number between [0-100]";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> playerArrayList = new ArrayList<>();
        String playerName = null;
        int roundCounter = 1;

        System.out.println(WELCOME_PROMPT);
        printRules();
        System.out.println(NAME_PROMPT);

        playerName = scanner.nextLine();


        while (playerName.equals("")) {
            System.out.println(NAME_PROMPT);
            playerName = scanner.nextLine();
        }


        Player p1 = new Player(playerName, 10, -99, false);
        playerArrayList.add(p1);


        for (int i = 0; i < 4; i++) {
            int playerNum = i + 2;
            Player tmp = new Player("Player " + String.valueOf(playerNum), 10, -99, false);
            playerArrayList.add(tmp);
        }

        while (p1.lives > 0) {

            System.out.println("Round " + roundCounter + " begins");

            do {
                try {
                    System.out.println(GUESS_PROMPT);
                    p1.guess = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input");

                }
                scanner.nextLine();
            } while (p1.guess < MIN_GUESS || p1.guess > MAX_GUESS);


            Player winner = calculateWinner(playerArrayList);
            updateScores(playerArrayList, winner);
            printLives(playerArrayList);

            if (currentPlayers == 1) {
                System.out.println("You win!");
                break;
            }

            if (p1.isOut) {
                System.out.println("You have lost!");
                System.out.println("==============================");
                break;

            }

            System.out.println("==============================");


            roundCounter++;


        }

        System.out.println("Thank you for playing!");

    }

    public static Player calculateWinner(ArrayList<Player> playerArrayList) {

        Random random = new Random();

        // set guesses 4 other players

        for (int i = 1; i < playerArrayList.size(); i++) {
            playerArrayList.get(i).guess = random.nextInt(101);
        }

        double average = 0;

        // calculates average of all players
        for (int i = 0; i < playerArrayList.size(); i++) {
            if (!playerArrayList.get(i).isOut) {
                average = average + playerArrayList.get(i).guess;
            }
        }
        double tmpAvg = average / playerArrayList.size();

        // multiples average by constant
        average = tmpAvg * MULTIPLY_CONSTANT;

        Player tmp = playerArrayList.get(0);

        // finds the player closest to average
        double min = average - tmp.getGuess();

        for (int i = 1; i < playerArrayList.size(); i++) {
            double tmpCalc = average - playerArrayList.get(i).getGuess();
            if (tmpCalc < 0) {
                tmpCalc = tmpCalc * -1;
            }

            if (tmpCalc < min) {
                min = tmpCalc;
                tmp = playerArrayList.get(i);
            }
        }

        // print scores/result for the round
        System.out.println("==============================");
        printScores(playerArrayList);
        System.out.printf("The average is %.2f multiplied by the constant 0.8 is %.2f", tmpAvg, average);
        System.out.println();
        System.out.println(tmp.getName() + " wins!");


        return tmp;
    }

    public static void updateScores(ArrayList<Player> playerArrayList, Player winner) {


        // update each player score if they aren't the winner

        for (int i = 0; i < playerArrayList.size(); i++) {
            if (!playerArrayList.get(i).name.equals(winner.name) && playerArrayList.get(i).getLives() > 0) {
                int curLives = playerArrayList.get(i).lives - 1;
                playerArrayList.get(i).setLives(curLives);

                if (curLives <= 0) {
                    playerArrayList.get(i).isOut = true;
                    currentPlayers--;
                    System.out.print(playerArrayList.get(i).getName() + " is out ");
                    System.out.println();
                }

            }
        }

    }

    public static void printScores(ArrayList<Player> playerArrayList) {

        for (int i = 0; i < playerArrayList.size(); i++) {

            if (!playerArrayList.get(i).isOut) {
                System.out.print(playerArrayList.get(i).getName() + ": " + playerArrayList.get(i).getGuess() + " ");
            }
        }
        System.out.println();
    }

    public static void printLives(ArrayList<Player> playerArrayList) {

        for (int i = 0; i < playerArrayList.size(); i++) {

            if (!playerArrayList.get(i).isOut) {
                System.out.print("Player" + (i + 1) + " lives: " + playerArrayList.get(i).getLives() + " ");
            }
        }
        System.out.println();
    }

    public static void printRules() {

        System.out.println("==============================");
        System.out.println("Rules: ");
        System.out.println("Each person will choose a number from [0-100]");
        System.out.println("We will take the average of 5 numbers and multiply it by 0.8");
        System.out.println("The player who chooses the number closest to the final answer of the multiplication is the winner of the round");
        System.out.println("Every other player besides the winner will lose 1 of their lives");
        System.out.println("If your lives reach 0, you are out");
        System.out.println("Good Luck!");
        System.out.println("==============================");
    }
}