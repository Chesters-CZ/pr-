package cz.chesters.prs;

public class Main {
    public static Game game; //nevyhnutelný zlo. bez tutoho mi to házelo null pointer exceptiony, viz starší verze kódu, i guess

    public static void main(String[] Args) {
        game = new Game();
        game.begin();
    }
}
