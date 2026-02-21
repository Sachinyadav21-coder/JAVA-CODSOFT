import java.util.Random;
import java.util.Scanner;

public class MultiplayerNumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean playAnotherGame = true;

        while (playAnotherGame) {
            // Ask for number of players
            System.out.print("Enter number of players: ");
            int numberOfPlayers = scanner.nextInt();
            scanner.nextLine(); // consume newline

            String[] playerNames = new String[numberOfPlayers];
            int[] playerScores = new int[numberOfPlayers];
            int[] roundsWon = new int[numberOfPlayers];

            // Get player names
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.print("Enter name for Player " + (i + 1) + ": ");
                playerNames[i] = scanner.nextLine();
            }

            // Ask how many rounds to play
            System.out.print("Enter number of rounds to play: ");
            int totalRounds = scanner.nextInt();

            int maxAttempts = 10;

            for (int roundNumber = 1; roundNumber <= totalRounds; roundNumber++) {
                System.out.println("\n========== ROUND " + roundNumber + " ==========");

                for (int i = 0; i < numberOfPlayers; i++) {
                    int secretNumber = random.nextInt(100) + 1;
                    int attemptsUsed = 0;
                    boolean guessedCorrectly = false;

                    System.out.println("\n" + playerNames[i] + "'s turn");
                    System.out.println("You have " + maxAttempts + " attempts.");

                    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                        System.out.print("Attempt " + attempt + ": Guess a number (1–100): ");
                        int guess = scanner.nextInt();
                        attemptsUsed++;

                        if (guess < secretNumber) {
                            System.out.println("Too low!");
                        } else if (guess > secretNumber) {
                            System.out.println("Too high!");
                        } else {
                            System.out.println("Correct! 🎉");
                            guessedCorrectly = true;
                            roundsWon[i]++;
                            break;
                        }
                    }

                    int roundScore = 0;
                    if (guessedCorrectly) {
                        roundScore = (maxAttempts + 1) - attemptsUsed;
                        playerScores[i] += roundScore;
                        System.out.println("Points earned this round: " + roundScore);
                    } else {
                        System.out.println("Out of attempts! The number was: " + secretNumber);
                        System.out.println("Points earned this round: 0");
                    }
                }

                // Scoreboard after each round
                System.out.println("\n----- SCOREBOARD AFTER ROUND " + roundNumber + " -----");
                for (int i = 0; i < numberOfPlayers; i++) {
                    System.out.println(
                        playerNames[i] +
                        " | Score: " + playerScores[i] +
                        " | Rounds Won: " + roundsWon[i]
                    );
                }
            }

            // Final results
            System.out.println("\n===== FINAL RESULTS =====");
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.println(
                    playerNames[i] +
                    " | Total Score: " + playerScores[i] +
                    " | Rounds Won: " + roundsWon[i]
                );
            }

            // Ask if players want to play a new game
            System.out.print("\nDo you want to play a new game? (yes/no): ");
            String response = scanner.next();
            playAnotherGame = response.equalsIgnoreCase("yes");
        }

        System.out.println("\nThanks for playing! 👋");
        scanner.close();
    }
}
