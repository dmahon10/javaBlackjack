//This program allows a user to play Blackjack against a dealer
import java.util.*;
class Blackjack
{
    public static void main(String [] args)
    {
        casinoTable();
    }

    public static void casinoTable()
    {
        int money = 100;
        int bet;
        System.out.println("Welcome to this Blackjack game.");
        Scanner keyboard = new Scanner(System.in);

        while (true) {
            do {
                System.out.println(" You have $" + money + ".");
                System.out.println("How much would you like to bet this round? (Place a bet of 0 to exit game)");
                bet = keyboard.nextInt();

                if (bet < 0 || bet > money) {
                    System.out.println("Please place a bet between 0 and " + money);
                    bet = keyboard.nextInt();
                }
            } while (bet < 0 || bet > money);
            if (bet == 0) {
                System.out.println("\nGame exited.");
                keyboard.close();
                break;
            }
            boolean playerWon = playGame();
            if (playerWon) money += bet;
            else money -= bet;
            if (money <= 0)
            {
                System.out.print("You're out of money!");
                System.out.println("\nGame exited.");
                keyboard.close();
                break;
            }
        }
    }

    public static boolean playGame()
    {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        deck.shuffle();

        //Create starting hands
        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        int playerScore = (playerHand.getCard(0)).valueInt() + (playerHand.getCard(1)).valueInt();
        int dealerScore = (dealerHand.getCard(0)).valueInt() + (dealerHand.getCard(1)).valueInt();


        System.out.println ("\nYour card values are are: " + (playerHand.getCard(0)).valueInt() + " and " +
            (playerHand.getCard(1)).valueInt() + " for a total of " + playerScore);

        System.out.println ("\nDealer's upcard value is: " + (dealerHand.getCard(0)).valueInt());

        if (playerScore == 21 && dealerScore == 21) {
            System.out.println("You and the dealer both hit Blackjack. It is a tie. Dealer wins.");
            return false;
        }
        else if (playerScore == 21) {
            System.out.print("\nYou hit blackjack! You win!");
            return true;
        }
        else if (dealerScore == 21) {
            System.out.println("\nDealer hit blackjack. Dealer wins.");
            return false;
        }

        while (playerScore < 21) {

            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nWould you like to hit or stand? Enter 1 to hit or 2 to stand: ");
            int choice = keyboard.nextInt();

            if (choice == 1){
                playerHand.addCard(deck.dealCard());
                playerScore += (playerHand.getCard(playerHand.numOfCards()-1).valueInt());
                System.out.println("\nYour card values are now: ");

                for (int i = 0; i < playerHand.numOfCards(); i++){
                    System.out.print((playerHand.getCard(i)).valueInt() + " ");
                }
                System.out.println("\nFor a score of: " + playerScore);
            }

            else if (choice == 2) {
                keyboard.close();
                break;
            }

            else {
                System.out.println("Invalid input.");
            }
            if (playerScore > 21) {
                System.out.print("Bust. You lose. \n");
                keyboard.close();
                return false;
            }
            keyboard.close();

        }

        System.out.println("Dealer reveals second card: " + (dealerHand.getCard(1)).valueInt());
        System.out.println("Dealer's card values are: " + (dealerHand.getCard(0)).valueInt() + " and " + (dealerHand.getCard(1)).valueInt());
        System.out.println("For a score of: " + dealerScore);
        while (dealerScore <= 16) {

            dealerHand.addCard(deck.dealCard());
            System.out.print("Dealer draws a " + dealerHand.getCard(dealerHand.numOfCards()-1).valueInt() + "  ");
            dealerScore += (dealerHand.getCard(dealerHand.numOfCards()-1).valueInt());
        }
        System.out.println("Dealer stands at: " + dealerScore);

        if (dealerScore > 21) {
            System.out.println("Dealer Busts. You win.");
            return true;
        }
        else if (dealerScore == playerScore){
            System.out.println("Tie. Dealer wins.");
            return false;
        }
        else if (dealerScore > playerScore) {
            System.out.print("Dealer wins.");
            return false;
        }
        else {
            System.out.println("You win!");
            return true;
        }
    }
}