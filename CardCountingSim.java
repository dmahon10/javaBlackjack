import java.lang.Math;
class CardCountingSim
{
    public static void main(String [] args)
    {
        final int HANDS = 100;
        final int PURSE = 100;
        int handCounter = 0;
        double money = PURSE;
        double runningCount = 0;
        double trueCount = 0;
        double rightBet = 5;
        boolean playerWon;
        System.out.println("Welcome to this two deck Blackjack simulation.\n Simulation Running...");

        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        deck.shuffle();

        while (handCounter < HANDS && deck.cardsGone < (52 * deck.DECKS)-7)
        {
            if (handCounter > 0) {
                playerHand.discardHand();
                dealerHand.discardHand();
                trueCount = Math.round(runningCount / deck.deckLeft());
                if (trueCount <= 1) {
                    rightBet = 5;
                }
                else {
                    rightBet = (trueCount - 1) * 5;
                }
            }
            else rightBet = 5;

            //Deal cards and adjust running count
            playerHand.addCard(deck.dealCard());
            runningCount = runCount((playerHand.getCard(0)).valueInt(), runningCount);

            playerHand.addCard(deck.dealCard());
            runningCount = runCount((playerHand.getCard(1)).valueInt(), runningCount);

            dealerHand.addCard(deck.dealCard());
            runningCount = runCount((dealerHand.getCard(0)).valueInt(), runningCount);

            dealerHand.addCard(deck.dealCard());
            runningCount = runCount((dealerHand.getCard(1)).valueInt(), runningCount);

            //Correct for duality of Ace's value (1 or 11)
            int playerScore = 0;
            int dealerScore = 0;

            playerScore = BasicStrategy.aceScore((playerHand.getCard(0)).valueInt(), (playerHand.getCard(1)).valueInt());
            dealerScore = BasicStrategy.aceScore((dealerHand.getCard(0)).valueInt(), (dealerHand.getCard(1)).valueInt());

            if (playerScore == 21 && dealerScore == 21) {
                playerWon = false;
            }
            else if (playerScore == 21) {
                playerWon = true;
            }
            else if (dealerScore == 21) {
                playerWon = false;
            }

            //Basic Strategy Simulation with added High/Low Running Count technique
            int dealerUp = (dealerHand.getCard(0)).valueInt();

            while (playerScore < 21) {

                if (playerScore < 12) {
                    playerHand.addCard(deck.dealCard());
                    playerScore += (playerHand.getCard(playerHand.numOfCards()-1).valueInt());
                    runningCount = runCount(playerHand.getCard(playerHand.numOfCards()-1).valueInt(), runningCount);
                }

                else if (playerScore == 12) {
                    if (dealerUp > 3 && dealerUp < 7) {
                        break;
                    }
                    else {
                        playerHand.addCard(deck.dealCard());
                        playerScore += (playerHand.getCard(playerHand.numOfCards()-1).valueInt());
                        runningCount = runCount(playerHand.getCard(playerHand.numOfCards()-1).valueInt(), runningCount);
                    }
                }
                else if (playerScore > 12 && playerScore < 17) {
                    if (dealerUp < 7) {
                        break;
                    }
                    else {
                        playerHand.addCard(deck.dealCard());
                        playerScore += (playerHand.getCard(playerHand.numOfCards()-1).valueInt());
                        runningCount = runCount(playerHand.getCard(playerHand.numOfCards()-1).valueInt(), runningCount);
                    }
                }
                else {
                    break;
                }
                if (playerScore > 21) {
                    playerWon = false;
                }
            }

            //Dealer's play
            while (dealerScore <= 16) {
                dealerHand.addCard(deck.dealCard());
                dealerScore += (dealerHand.getCard(dealerHand.numOfCards()-1).valueInt());
                runningCount = runCount(dealerHand.getCard(dealerHand.numOfCards()-1).valueInt(), runningCount);
            }
            if (dealerScore > 21) {
                playerWon = true;
            }
            else if (dealerScore == playerScore){
                playerWon = false;
            }
            else if (dealerScore > playerScore) {
                playerWon = false;
            }
            else {
                playerWon = true;
            }

            handCounter++;

            //Distribute winnings
            if (playerWon) {
                money += rightBet;
            }
            else {
                money -= rightBet;
            }

            //Test the state of the game
            if (money <= 0)
            {
                System.out.print("You're out of money! ");
                System.out.print("You played " + handCounter + " hands. ");
                System.out.println("Game exited.");
                break;
            }
            if (handCounter > HANDS || deck.cardsGone > ((52 * deck.DECKS)-10))
            {
                if (deck.cardsGone > ((52 * deck.DECKS)-6)) {
                    System.out.println("Out of cards. Simulation over.");
                }
                else {
                    System.out.println("Hand limit reached.");
                }
                System.out.println(handCounter + " hands played. You started with $" + PURSE + " and ended with $" + money);
                break;
            }
        }
    }

    //High/Low card counting technique
    public static double runCount(int cardValue, double currentCount) {

        if (cardValue == 1 || cardValue > 9) {
            return currentCount - 1;
        }
        else if (cardValue > 1 && cardValue < 7) {
            return currentCount + 1;
        }
        else {
            return currentCount;
        }
    }
}