import java.util.*;
public class Hand
{

    private Vector<Card> hand;

    public Hand () {
        hand = new Vector<Card>();
    }

    public void discardHand()
    {
        hand.clear();
    }

    public void addCard(Card newCard)
    {
        hand.add(newCard);
    }

    public int numOfCards()
    {
        return hand.size();
    }

    public Card getCard(int n)
    {
        if (n >= 0 && n < hand.size()) {
            return (Card)(hand.get(n));
        }
        else return null;
    }
}