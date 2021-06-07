import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public ArrayList<Player> players = new ArrayList<>();
    public Scanner scanner = new Scanner(System.in);
    public Deck deck = new Deck();

    public void begin() {
        int cards;
        System.out.print(MikolasovyConsoleBarvy.BLUE + "Vítej v prś, retardované verzi prší. ");
        do {
            System.out.println(MikolasovyConsoleBarvy.BLUE + "Kolik karet mám na začátku rozdat?" + MikolasovyConsoleBarvy.RESET);
            cards = scanner.nextInt();
            if (cards < 1) System.out.println(MikolasovyConsoleBarvy.PURPLE + "ERR: Nemůžeš hrát bez karet");
        } while (cards < 1);
        createplayers(cards);
        System.out.println(MikolasovyConsoleBarvy.BLUE + "nice. hra začíná" + MikolasovyConsoleBarvy.RESET);
        while (!isOver()) {
            for (int i = 0; i < players.size(); i++) {
                System.out.println("hraje hráč " + i);
                System.out.println("Poslední zahraná karta je " + Main.game.deck.lastCard);
                players.get(i).playSth();
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).won()) {
                System.out.println(MikolasovyConsoleBarvy.BLUE + "Hráč " + i + " snědl prś");
                break;
            }
        }
    }

    public boolean isOver() {
        for (Player player : players) if (player.won()) return true;
        return false;
    }

    public void createplayers(int cards) {
        int plrs;
        int bots;
        do {
            System.out.println(MikolasovyConsoleBarvy.BLUE + "Super. Kolik lidí hodláš mučit?" + MikolasovyConsoleBarvy.RESET);
            plrs = scanner.nextInt();

            System.out.println(MikolasovyConsoleBarvy.BLUE + "k. kolik budeš chtít botů? " + MikolasovyConsoleBarvy.BLACK + MikolasovyConsoleBarvy.BG_YELLOW + "(EARLY ACCESS)" + MikolasovyConsoleBarvy.RESET);
            bots = scanner.nextInt();

            if ((plrs < 0 && bots < 0) || plrs + bots < 1)
                System.out.println(MikolasovyConsoleBarvy.PURPLE + "ERR: Nemůžeš hrát bez hráčů!");
            else break;
        } while ((plrs < 0 && bots < 0) || plrs + bots < 1);


        for (; plrs > 0; plrs--) players.add(new Player(cards, true));       //lidi
        for (; bots > 0; bots--) players.add(new Player(cards, false));      //boti
    }

    public Card.Color useQueen() {
        vyberbarvy:
        while (true) {  //TODO: replace with do-while?
            switch (Main.game.scanner.next().charAt(0)) { //TODO: proč se to tady rozbíjelo s nextline
                case 's', 'S' -> {
                    return Card.Color.HEARTS;
                }           //srdce
                case 'z', 'Z', 'ž', 'Ž' -> {
                    return Card.Color.ACORNS;
                } //žalud
                case 'k', 'K' -> {
                    return Card.Color.BALLS;
                }           //koule
                case 'l', 'L' -> {
                    return Card.Color.LEAVES;
                }           //listy

                default -> {
                    System.out.println("Zadanou barvu neznám. Zkus to znova.");
                    continue vyberbarvy;
                }
            }
        }
    }
}

