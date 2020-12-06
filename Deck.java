import java.lang.Math;
public class Deck
{
    private Card[] deck;

    public final int DECKS = 2;
    public int cardsGone = 0;

    public Deck () {
        int cardIndex = 0;
        deck = new Card[DECKS * 52];
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j <= 13; j++) {
                deck[cardIndex] = new Card(i, j);
                if (DECKS == 1) deck[cardIndex] = new Card (i, j);
                else if (DECKS > 1) deck[cardIndex + DECKS - 1] = new Card(i, j);
                if (DECKS == 1) cardIndex ++;
                else if (DECKS > 1) cardIndex += DECKS;
            }
        }
        cardsGone = 0;
    }

    public void shuffle() {
        for (int card = 0; card <= (DECKS * 52) - 1; card++) {
            int randCard = (int) (Math.random() * 51);
            Card temp = deck[card];
            deck[card] = deck[randCard];
            deck[randCard] = temp;
        }
        cardsGone = 0;
    }

    public Card dealCard() {
        if (cardsGone == DECKS * 52) {
            System.out.println ("OUT OF CARDS. RESHUFFLE NEEDED.");
        }
        cardsGone++;
        return deck[cardsGone - 1];
    }

    public double deckLeft ()
    {
        int cardsLeft = (DECKS * 52) - cardsGone;
        double decksLeft = (double) cardsLeft / 52;
        double rounded = Math.round(decksLeft * 2) / 2.0;
        if (rounded > 0) return rounded;
        else return 0.5;
    }
}