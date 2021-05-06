public class Card {
    public Color color;
    public Type type;
    public int drawMultiple;

    public Card(Type type, Color color) {
        this.color = color;
        this.type = type;
    }

    public Card(Type type, Color color, int drawMultiple) {
        this.color = color;
        this.type = type;
        this.drawMultiple = drawMultiple;
    }

    public boolean canUse() {
        return switch (Instances.deck.lastCard.type) {
            case SEVEN -> this.type == Type.SEVEN;
            case ACE -> this.type == Type.ACE;
            default -> this.type == Instances.deck.lastCard.type || this.color == Instances.deck.lastCard.color;
        };
    }

    public enum Color {
        HEARTS, BALLS, ACORNS, LEAVES
    }

    public enum Type {
        SEVEN, SEVEN_USED, EIGHT, NINE, TEN, JEAN, QUEEN, KING, ACE, ACE_USED
    }
}
