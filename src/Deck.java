import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public static Card[] defaultDeck = {new Card(Card.Type.ACE, Card.Color.BALLS), new Card(Card.Type.SEVEN, Card.Color.BALLS,2), new Card(Card.Type.EIGHT, Card.Color.BALLS), new Card(Card.Type.NINE, Card.Color.BALLS), new Card(Card.Type.TEN, Card.Color.BALLS), new Card(Card.Type.JEAN, Card.Color.BALLS), new Card(Card.Type.QUEEN, Card.Color.BALLS), new Card(Card.Type.KING, Card.Color.BALLS), new Card(Card.Type.ACE, Card.Color.HEARTS), new Card(Card.Type.SEVEN, Card.Color.HEARTS,2), new Card(Card.Type.EIGHT, Card.Color.HEARTS), new Card(Card.Type.NINE, Card.Color.HEARTS), new Card(Card.Type.TEN, Card.Color.HEARTS), new Card(Card.Type.JEAN, Card.Color.HEARTS), new Card(Card.Type.QUEEN, Card.Color.HEARTS), new Card(Card.Type.KING, Card.Color.HEARTS), new Card(Card.Type.ACE, Card.Color.LEAVES), new Card(Card.Type.SEVEN, Card.Color.LEAVES,2), new Card(Card.Type.EIGHT, Card.Color.LEAVES), new Card(Card.Type.NINE, Card.Color.LEAVES), new Card(Card.Type.TEN, Card.Color.LEAVES), new Card(Card.Type.JEAN, Card.Color.LEAVES), new Card(Card.Type.QUEEN, Card.Color.LEAVES), new Card(Card.Type.KING, Card.Color.LEAVES), new Card(Card.Type.ACE, Card.Color.ACORNS), new Card(Card.Type.SEVEN, Card.Color.ACORNS,2), new Card(Card.Type.EIGHT, Card.Color.ACORNS), new Card(Card.Type.NINE, Card.Color.ACORNS), new Card(Card.Type.TEN, Card.Color.ACORNS), new Card(Card.Type.JEAN, Card.Color.ACORNS), new Card(Card.Type.QUEEN, Card.Color.ACORNS), new Card(Card.Type.KING, Card.Color.ACORNS)};
    public Card lastCard = null;
    public ArrayList<Card> deck = new ArrayList<>();
    public static int posInDeck = -1;

    public static Card drawCard() {
        posInDeck++;
        if (posInDeck % defaultDeck.length == 0) Collections.shuffle(Instances.deck.deck);
        return Instances.deck.deck.get(posInDeck%defaultDeck.length);
    }

    @Deprecated
    public static Card drawRandomCard() {
        Card.Color color = switch ((int) (Math.random() * 3)) {
            case 0 -> Card.Color.ACORNS;
            case 1 -> Card.Color.BALLS;
            case 2 -> Card.Color.HEARTS;
            default -> Card.Color.LEAVES;
        };
        Card.Type type =
                switch ((int) (Math.random() * 7)) {
                    case 0 -> Card.Type.ACE;
                    case 1 -> Card.Type.SEVEN;
                    case 2 -> Card.Type.EIGHT;
                    case 3 -> Card.Type.NINE;
                    case 4 -> Card.Type.TEN;
                    case 5 -> Card.Type.JEAN;
                    case 6 -> Card.Type.QUEEN;
                    default -> Card.Type.KING;
                };
        return new Card(type, color);
    }
}
