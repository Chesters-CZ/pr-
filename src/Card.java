import org.jetbrains.annotations.NotNull;

import java.time.chrono.MinguoChronology;

public class Card {
    public Color color;
    public Type type;
    public int drawMultiple;

    public Card(Type type, Color color) {
        this.color = color;
        this.type = type;
        this.drawMultiple = -1;
    }

    public Card(Type type, Color color, int drawMultiple) {
        this.color = color;
        this.type = type;
        this.drawMultiple = drawMultiple;
    }

    @Override
    public String toString() {
        return "Card{" +
                "color=" + color +
                ", type=" + type +
                ", drawMultiple=" + drawMultiple +
                '}';
    }

    public void dump() {
        System.out.println("Dump of Card{ ");
        System.out.println("color=" + color);
        System.out.println(", type=" + type);
        System.out.println(", drawMultiple=" + drawMultiple);
        try {
            System.out.println(", game=" + Main.game);
        } catch (Exception ignored) {
        }

    }

    @Deprecated
    // z nějakýho důvodu házelo NullPointerException. bude fungovat pokud this.game != null, ale spoléhá na canUse() z Decku
    public boolean canUse(Card lastCard) {
        System.out.println(MikolasovyConsoleBarvy.PURPLE + "WARN: Used deprecated method");
        return Main.game.deck.canUse(this);
    }

    public enum Color {
        HEARTS, BALLS, ACORNS, LEAVES
    }

    public enum Type {
        SEVEN, SEVEN_USED, EIGHT, NINE, TEN, JEAN, QUEEN, KING, ACE, ACE_USED
    }
}
