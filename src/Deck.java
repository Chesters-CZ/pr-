import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    public static Card drawCard() {
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
