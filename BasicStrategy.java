//A two deck Blackjack basic strategy simulation.
class BasicStrategy
{
    public static void main(String [] args)
    {
        simulateCasino();
    }

    public static void simulateCasino()
    {
        final int HANDS = 20;
        final int PURSE = 100;
        int handCounter = 0;
        int money = PURSE;
        int bet;
        System.out.println("Welcome to this two deck Blackjack simulation.\n Simulation Running...");

        while (true) {
            // Uses basic flat bet blackjack strategy. Same small bet for each hand.
            bet = 5;
            handCounter ++;
            boolean playerWon = simulateGame();
            if (playerWon) money += bet;
            else money -= bet;
            if (money <= 0)
            {
                System.out.print("You're out of money! ");
                System.out.print("You played " + handCounter + " hands. ");
                System.out.println("Game exited.");
                break;
            }
            if (handCounter > HANDS)
            {
                System.out.println("Hand limit reached. Simulation Over.");
                System.out.println("You played " + HANDS + " hands. You started with $" + PURSE + " and ended with $" + money);
                break;
            }
        }
    }

    public static boolean simulateGame()
    {
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        deck.shuffle();

        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        //Accounts for duality of Ace value ( 1 or 11)
        int playerScore = 0;
        int dealerScore = 0;

        playerScore = BasicStrategy.aceScore((playerHand.getCard(0)).valueInt(), (playerHand.getCard(1)).valueInt());
        dealerScore = BasicStrategy.aceScore((dealerHand.getCard(0)).valueInt(), (dealerHand.getCard(1)).valueInt());

        if (playerScore == 21 && dealerScore == 21) {
            return false;
        }
        else if (playerScore == 21) {
            return true;
        }
        else if (dealerScore == 21) {
            return false;
        }

        //Basic Strategy Simulation

        int dealerUp = (dealerHand.getCard(0)).valueInt();

        while (playerScore < 21) {

            if (playerScore < 12) {
                playerHand.addCard(deck.dealCard());
                playerScore += (playerHand.getCard(playerHand.numOfCards()-1).valueInt());
            }

            else if (playerScore == 12) {
                if (dealerUp > 3 && dealerUp < 7) {
                    break;
                }
                else {
                    playerHand.addCard(deck.dealCard());
                    playerScore += (playerHand.getCard(playerHand.numOfCards()-1).valueInt());
                }
            }
            else if (playerScore > 12 && playerScore < 17) {
                if (dealerUp < 7) {
                    break;
                }
                else {
                    playerHand.addCard(deck.dealCard());
                    playerScore += (playerHand.getCard(playerHand.numOfCards()-1).valueInt());
                }
            }
            else {
                break;
            }
            if (playerScore > 21) {
                return false;
            }
        }

        while (dealerScore <= 16) {

            dealerHand.addCard(deck.dealCard());
            dealerScore += (dealerHand.getCard(dealerHand.numOfCards()-1).valueInt());
        }

        if (dealerScore > 21) {
            return true;
        }
        else if (dealerScore == playerScore){
            return false;
        }
        else if (dealerScore > playerScore) {
            return false;
        }
        else {
            return true;
        }
    }

    //Accounts for ace value duality (1 or 11)
    public static int aceScore(int card1, int card2)
    {
        int score = 0;

        if ( card1 == 1 && card2 + 11 <= 21 ) {
            return score = card2 + 11;
        }
        else if (card1 == 1 && card2 + 11 > 21) {
            return score = card2 + 1;
        }
        else if ( card2 == 1 && card1 + 11 <= 21 ) {
            return score = card1 + 11;
        }
        else if (card2 == 1 && card1 + 11 > 21) {
            return score = card1 + 1;
        }
        else {
            return score = card1 + card2;
        }
    }
}