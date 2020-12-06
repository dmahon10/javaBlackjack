// Codes:
//CLUBS = 1, DIAMONDS = 2, HEARTS = 3, SPADES = 4
//ACE = 1, JACK = 11, QUEEN = 12, KING = 13
public class Card
{
    final int suit, value;

    public Card (int cardSuit, int cardValue) {

        suit = cardSuit;
        value = cardValue;
    }

    public int valueInt() {
        if (value >= 10) return 10;
        else return value;
    }
}