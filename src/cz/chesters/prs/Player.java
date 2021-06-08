package cz.chesters.prs;

import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand = new ArrayList<>();
    public boolean isHuman;

    public Player(int cardCount, boolean isHuman) {
        if (cardCount < 1) {
            cardCount = 1;
            System.out.println(MikolasovyConsoleBarvy.PURPLE + "WARN: Každý musí na začátku mít alespoň jednu kartu.");
        }
        for (int i = 0; i < cardCount; i++) {
            hand.add(Main.game.deck.drawCard());
        }
        this.isHuman = isHuman;
    }

    public boolean won() {
        return hand.size() == 0;
    }

    public void playSth() {
        if (this.isHuman) {
            StringBuilder visualizedCards = new StringBuilder();
            visualizedCards.append(Main.game.vypsatkarty(hand));
            //debug System.out.println(this.hand.size());
            //debug cz.chesters.prs.Main.game.deck.lastCard.dump();
            visualizedCards.append(switch (Main.game.deck.lastCard.type) {
                case ACE -> "PASS";
                case SEVEN -> "DRW" + Main.game.deck.lastCard.drawMultiple;
                default -> "DRAW";
            });
            visualizedCards.append("\n");

            for (int i = 0; i < this.hand.size(); i++) {
                if (!Main.game.deck.canUse(hand.get(i)))
                    visualizedCards.append(MikolasovyConsoleBarvy.BG_BLACK);
                if (i < 10)
                    visualizedCards.append(" ").append(i).append("\t");
                else visualizedCards.append(i).append("\t");
                visualizedCards.append(MikolasovyConsoleBarvy.RESET);
            }
            visualizedCards.append(this.hand.size());
            System.out.println(visualizedCards);

            int chosen;
            while (true) {  // výběr karty
                try {
                    System.out.print("hodnotu pls: ");
                    chosen = Main.game.scanner.nextInt();
                    if (chosen > this.hand.size() || chosen < 0) throw new Exception();
                } catch (Exception e) {
                    System.out.println("Zvolil jsi neplatnou hodnotu. (" + e + ")");
                    continue;
                }
                if (Main.game.deck.lastCard.type == Card.Type.ACE) {
                    if (chosen == hand.size()) Main.game.deck.lastCard.type = Card.Type.ACE_USED;
                    else playCard(chosen);
                    break;
                } else if (Main.game.deck.lastCard.type == Card.Type.SEVEN) {
                    if (chosen == this.hand.size()) {
                        for (int i = 0; i < Main.game.deck.lastCard.drawMultiple; i++)
                            this.hand.add(Main.game.deck.drawCard());
                        Main.game.deck.lastCard.type = Card.Type.SEVEN_USED;
                        break;
                    } else if (Main.game.deck.canUse(hand.get(chosen)) && hand.get(chosen).type == Card.Type.SEVEN) {
                        playCard(chosen, Main.game.deck.lastCard.drawMultiple);
                        break;
                    }
                } else if (chosen == this.hand.size()) {
                    this.hand.add(Main.game.deck.drawCard());
                    break;
                } else if (hand.get(chosen).type == Card.Type.QUEEN) {
                    playCard(chosen);
                    System.out.println("Zvol barvu svrška");
                    System.out.println(MikolasovyConsoleBarvy.RED + "S" + MikolasovyConsoleBarvy.RESET + "rdce " + MikolasovyConsoleBarvy.YELLOW + "Z" + MikolasovyConsoleBarvy.RESET + "aludy " + MikolasovyConsoleBarvy.CYAN + "K" + MikolasovyConsoleBarvy.RESET + "oule " + MikolasovyConsoleBarvy.GREEN + "L" + MikolasovyConsoleBarvy.RESET + "isty" + MikolasovyConsoleBarvy.RESET);
                    Main.game.deck.lastCard.dump();
                    Main.game.deck.lastCard.color = Main.game.useQueen();
                    Main.game.deck.lastCard.dump();
                    break;
                } else if (Main.game.deck.canUse(hand.get(chosen))) {
                    playCard(chosen);
                    break;
                }
            }


        } else {    // bot ai
            System.out.println(MikolasovyConsoleBarvy.BLACK + MikolasovyConsoleBarvy.BG_YELLOW + "BOT AI IS IN EARLY ACCESS)" + MikolasovyConsoleBarvy.RESET);


            if (Main.game.deck.lastCard.type == Card.Type.ACE) {
                if (hasCard(Card.Type.ACE)) playCard(findCard(Card.Type.ACE));
                else {
                    Main.game.deck.lastCard.type = Card.Type.ACE_USED;
                }
            } else if (Main.game.deck.lastCard.type == Card.Type.SEVEN) {
                if (hasCard(Card.Type.SEVEN))
                    playCard(findCard(Card.Type.SEVEN), Main.game.deck.lastCard.drawMultiple);

                else {
                    for (int i = 0; i < Main.game.deck.lastCard.drawMultiple; i++)
                        this.hand.add(Main.game.deck.drawCard());
                    Main.game.deck.lastCard.type = Card.Type.SEVEN_USED;
                }
            } else if (hasCard(Main.game.deck.lastCard.color))
                playCard(findCard(Main.game.deck.lastCard.color));
            else if (hasCard(Main.game.deck.lastCard.type))
                playCard(findCard(Main.game.deck.lastCard.type));
        }
    }


    public void playCard(int index) {
        Main.game.deck.lastCard = hand.get(index);
        hand.remove(index);
    }

    public void playCard(int index, int drawmultiple) {
        Main.game.deck.lastCard = hand.get(index);
        hand.remove(index);
        Main.game.deck.lastCard.drawMultiple += drawmultiple;
    }

    public boolean hasCard(Card.Color color) {
        for (Card card : this.hand) {
            if (card.color == color) return true;
        }
        return false;
    }

    public boolean hasCard(Card.Type type) {
        for (Card card : this.hand) {
            if (card.type == type) return true;
        }
        return false;
    }

    public int findCard(Card desired) {
        for (int i = 0; i < this.hand.size(); i++) {
            if (hand.get(i) == desired) return i;
        }
        return -1;
    }

    public int findCard(Card.Color desired) {
        for (int i = 0; i < this.hand.size(); i++) {
            if (hand.get(i).color == desired) return i;
        }
        return -1;
    }

    public int findCard(Card.Type desired) {
        for (int i = 0; i < this.hand.size(); i++) {
            if (hand.get(i).type == desired) return i;
        }
        return -1;
    }
}