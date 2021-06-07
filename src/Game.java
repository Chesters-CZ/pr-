import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    public ArrayList<Player> players = new ArrayList<>();
    public Scanner scanner = new Scanner(System.in);
    public Deck deck = new Deck();

    public void begin() {
        System.out.println(MikolasovyConsoleBarvy.BLUE + "Vítej v prś, retardované verzi prší. Kolik karet mám na začátku rozdat?" + MikolasovyConsoleBarvy.RESET);
        int cards = scanner.nextInt();
        System.out.println(MikolasovyConsoleBarvy.BLUE + "Super. Kolik lidí hodláš mučit?" + MikolasovyConsoleBarvy.RESET);
        for (int i = scanner.nextInt(); i > 0; i--) players.add(new Player(cards, true));
        System.out.println(MikolasovyConsoleBarvy.BLUE + "k. kolik budeš chtít botů?" + MikolasovyConsoleBarvy.RESET);//Todo: lízat různý karty pro různý lidi
        for (int i = scanner.nextInt(); i > 0; i--) players.add(new Player(cards, false));
        System.out.println(MikolasovyConsoleBarvy.BLUE + "nice. hra začíná" + MikolasovyConsoleBarvy.RESET);
        while (!isOver()) {
            for (int i = 0; i < players.size(); i++) {
                System.out.println("hraje hráč " + i);
                System.out.println("Poslední zahraná karta je " +
                        switch (deck.lastCard.color) {  //todo: do funkce
                            case HEARTS -> MikolasovyConsoleBarvy.RED + "srdcov";
                            case BALLS -> MikolasovyConsoleBarvy.CYAN + "kulov";
                            case ACORNS -> MikolasovyConsoleBarvy.YELLOW + "žaludov";
                            case LEAVES -> MikolasovyConsoleBarvy.GREEN + "zelen";
                        } +
                        switch (deck.lastCard.type) {
                            case SEVEN, SEVEN_USED -> "á" + MikolasovyConsoleBarvy.RESET + " sedma";
                            case EIGHT -> "á" + MikolasovyConsoleBarvy.RESET + " osma";
                            case NINE -> "á" + MikolasovyConsoleBarvy.RESET + " devítka";
                            case TEN -> "á" + MikolasovyConsoleBarvy.RESET + " desítka";
                            case JEAN -> "ej" + MikolasovyConsoleBarvy.RESET + " spodek";
                            case QUEEN -> "ej" + MikolasovyConsoleBarvy.RESET + " svršek";
                            case KING -> "ej" + MikolasovyConsoleBarvy.RESET + " král";
                            case ACE, ACE_USED -> "ý" + MikolasovyConsoleBarvy.RESET + " eso";
                        });
                players.get(i).playSth();
            }
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).won()) System.out.println(MikolasovyConsoleBarvy.BLUE + "Hráč " + i + " snědl prś");
        }
    }

    public boolean isOver() {
        for (Player player : players) if (player.won()) return true;
        return false;
    }
}

