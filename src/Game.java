import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    public ArrayList<Player> players = new ArrayList<>();

    public void begin() {
        Instances.deck.deck.addAll(Arrays.asList(Deck.defaultDeck));
        System.out.println(MikolasovyConsoleBarvy.BLUE + "Vítej v prś, retardované verzi prší. Kolik karet mám na začátku rozdat?" + MikolasovyConsoleBarvy.RESET);
        int cards = Instances.scanner.nextInt();
        System.out.println(MikolasovyConsoleBarvy.BLUE + "Super. Kolik lidí hodláš mučit?" + MikolasovyConsoleBarvy.RESET);
        for (int i = Instances.scanner.nextInt(); i > 0; i--) players.add(new Player(cards, true));
        System.out.println(MikolasovyConsoleBarvy.BLUE + "k. kolik budeš chtít botů?" + MikolasovyConsoleBarvy.RESET);
        for (int i = Instances.scanner.nextInt(); i > 0; i--) players.add(new Player(cards, false));
        System.out.println(MikolasovyConsoleBarvy.BLUE + "nice. hra začíná" + MikolasovyConsoleBarvy.RESET);

        while (!isOver()) {
            for (int i = 0; i < players.size(); i++) {
                System.out.println("hraje hráč " + i);
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

