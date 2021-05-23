import java.util.Arrays;

public class Main {
    public static Game game; //nevyhnutelný zlo. bez tutoho mi to házelo null pointer exceptiony

    public static void main(String[] Args) {
        game = new Game();
        game.begin();
    }
}
