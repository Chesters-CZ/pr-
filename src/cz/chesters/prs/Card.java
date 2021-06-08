package cz.chesters.prs;

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
        return switch (this.color) {
            case HEARTS -> MikolasovyConsoleBarvy.RED + "srdcov";
            case BALLS -> MikolasovyConsoleBarvy.CYAN + "kulov";
            case ACORNS -> MikolasovyConsoleBarvy.YELLOW + "žaludov";
            case LEAVES -> MikolasovyConsoleBarvy.GREEN + "zelen";
        } +
                switch (this.type) {
                    case SEVEN, SEVEN_USED -> "á" + MikolasovyConsoleBarvy.RESET + " sedma";
                    case EIGHT -> "á" + MikolasovyConsoleBarvy.RESET + " osma";
                    case NINE -> "á" + MikolasovyConsoleBarvy.RESET + " devítka";
                    case TEN -> "á" + MikolasovyConsoleBarvy.RESET + " desítka";
                    case JEAN -> "ej" + MikolasovyConsoleBarvy.RESET + " spodek";
                    case QUEEN -> "ej" + MikolasovyConsoleBarvy.RESET + " svršek";
                    case KING -> "ej" + MikolasovyConsoleBarvy.RESET + " král";
                    case ACE, ACE_USED -> "ý" + MikolasovyConsoleBarvy.RESET + " eso";
                };
    }

    public void dump() {
        System.out.println("Dump of cz.chesters.prs.Card{ ");
        System.out.println("color=" + color);
        System.out.println(", type=" + type);
        System.out.println(", drawMultiple=" + drawMultiple);
        try {
            System.out.println(", game=" + Main.game);
        } catch (Exception ignored) {
        }
        System.out.println("}");
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
