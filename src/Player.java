import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand = new ArrayList<>();
    public boolean won;

    public Player(int cardCount) {
        if (cardCount < 1) {
            cardCount = 1;
            System.out.println("WARN: Každý musí na začátku mít alespoň jednu kartu."); //TODO: Barva
        }
        for (int i = 0; i < cardCount; i++) {
            hand.add(Deck.drawCard());
        }
        won = false;
    }
}
