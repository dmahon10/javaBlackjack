import java.util.*;
class Main
{
    public static void main(String [] args)
    {
        int choice;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\nWelcome!");
        System.out.println("\nPlease enter 1 to play blackjack, 2 to run a basic strategy simulation game and 3 for a card counting simulation game");

        choice = keyboard.nextInt();

        String[] arguments = new String[] {""};

        if (choice == 1){
          Blackjack play_blackjack = new Blackjack();
          play_blackjack.main(arguments);
        }
        else if (choice == 2){
          BasicStrategy basic_simulation = new BasicStrategy();
          basic_simulation.main(arguments);
        }
        else {
          CardCountingSim counting_simulation = new CardCountingSim();
          counting_simulation.main(arguments);
        }
    }
}