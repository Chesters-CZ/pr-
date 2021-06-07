import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Card> deck = defaultDeck();
    public Card lastCard = drawCard();

    public ArrayList<Card> defaultDeck() {
        ArrayList<Card> out = new ArrayList<>();
        for (int i = 0; i < Card.Color.values().length; i++)
            for (int j = 0; j < Card.Type.values().length - 2; j++)
                out.add(new Card(switch (j) {
                    case 0 -> Card.Type.SEVEN;
                    case 1 -> Card.Type.EIGHT;
                    case 2 -> Card.Type.NINE;
                    case 3 -> Card.Type.TEN;
                    case 4 -> Card.Type.JEAN;
                    case 5 -> Card.Type.QUEEN;
                    case 6 -> Card.Type.KING;
                    case 7 -> Card.Type.ACE;
                    default -> null;
                }, switch (i) {
                    case 0 -> Card.Color.HEARTS;
                    case 1 -> Card.Color.LEAVES;
                    case 2 -> Card.Color.ACORNS;
                    case 3 -> Card.Color.BALLS;
                    default -> null;
                }, j == 0 ? 2 : 1));    //kondicionální operátor - dobrý, ne?
        Collections.shuffle(out);
        return out;
    }

    public Card drawCard() {
        if (deck.size() != 0) {
            Card out = deck.get(0);
            deck.remove(0);
            return out;
        } else {
            deck = defaultDeck();
            return drawCard();
        }
    }


    public boolean canUse(Card card) {
        return switch (lastCard.type) {
            case SEVEN -> card.type == Card.Type.SEVEN;
            case ACE -> card.type == Card.Type.ACE;
            default -> card.type == lastCard.type || card.color == lastCard.color;
        };
    }
}
