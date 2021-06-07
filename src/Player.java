import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

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
            //debug System.out.println(this.hand.size());
            for (Card card : hand) {
                if (!Main.game.deck.canUse(card)) visualizedCards.append(MikolasovyConsoleBarvy.BG_BLACK);
                visualizedCards.append(switch (card.color) {
                    case ACORNS -> MikolasovyConsoleBarvy.YELLOW;
                    case BALLS -> MikolasovyConsoleBarvy.CYAN;
                    case HEARTS -> MikolasovyConsoleBarvy.RED;
                    case LEAVES -> MikolasovyConsoleBarvy.GREEN;
                    default -> MikolasovyConsoleBarvy.PURPLE;
                });
                visualizedCards.append(switch (card.type) {
                    case ACE, ACE_USED -> " A";
                    case TEN -> "10";
                    case JEAN -> " J";
                    case KING -> " K";
                    case QUEEN -> " Q";
                    case SEVEN, SEVEN_USED -> " 7";
                    case EIGHT -> " 8";
                    case NINE -> " 9";
                    default -> MikolasovyConsoleBarvy.PURPLE + "??";
                }).append("\t").append(MikolasovyConsoleBarvy.RESET);
            }
            //debug Main.game.deck.lastCard.dump();
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
            while (true) {
                try {
                    chosen = Main.game.scanner.nextInt();
                    if (chosen > this.hand.size() || chosen < 0) throw new Exception();
                } catch (Exception e) {
                    System.out.println("Zvolil jsi neplatnou hodnotu.");
                    continue;
                }
                if (Main.game.deck.lastCard.type == Card.Type.ACE) {
                    Main.game.deck.lastCard.type = Card.Type.ACE_USED;
                    break;
                } else if (Main.game.deck.lastCard.type == Card.Type.SEVEN) {
                    if (chosen == this.hand.size()) {
                        for (int i = 0; i < Main.game.deck.lastCard.drawMultiple; i++)
                            this.hand.add(Main.game.deck.drawCard());
                        Main.game.deck.lastCard.type = Card.Type.SEVEN_USED;
                        break;
                    } else if (Main.game.deck.canUse(hand.get(chosen)) && hand.get(chosen).type == Card.Type.SEVEN) {
                        Main.game.deck.lastCard.drawMultiple += 2;
                        Main.game.deck.lastCard.color = hand.get(chosen).color;
                        hand.remove(chosen);
                        break;
                    }
                } else if (chosen == this.hand.size()) {
                    this.hand.add(Main.game.deck.drawCard());
                    break;
                } else if (hand.get(chosen).type == Card.Type.QUEEN) {
                    Main.game.deck.lastCard = hand.get(chosen);
                    hand.remove(chosen);
                    System.out.println("Zvol barvu svrška");
                    System.out.println(MikolasovyConsoleBarvy.RED + "SRDCE " + MikolasovyConsoleBarvy.YELLOW + "ŽALUDY " + MikolasovyConsoleBarvy.CYAN + "KOULE " + MikolasovyConsoleBarvy.GREEN + "LISTY" + MikolasovyConsoleBarvy.RESET);
                    boolean repeat = true;  //todo: move to game
                    while (repeat)
                        switch (Main.game.scanner.nextLine().replaceAll("\\p{M}", "").toLowerCase(Locale.ROOT)) {
                            case "srdce" -> {
                                Main.game.deck.lastCard.color = Card.Color.HEARTS;
                                repeat = false;
                            }
                            case "zaludy" -> {
                                Main.game.deck.lastCard.color = Card.Color.ACORNS;
                                repeat = false;
                            }
                            case "koule" -> {
                                Main.game.deck.lastCard.color = Card.Color.BALLS;
                                repeat = false;
                            }
                            case "listy" -> {
                                Main.game.deck.lastCard.color = Card.Color.LEAVES;
                                repeat = false;
                            }
                            default -> System.out.println("Zadanou barvu neznám. Zkus to znova.");
                        }
                } else if (Main.game.deck.canUse(hand.get(chosen))) {
                    Main.game.deck.lastCard = hand.get(chosen);
                    hand.remove(hand.get(chosen));
                    break;
                }
            }

        } else {    // bot ai
            boolean playedsth = false;
            int chosen = -1;
            switch (Main.game.deck.lastCard.type) {
                case SEVEN -> {
                    if (hasCard(Card.Type.SEVEN)) {
                        for (int i = 0; i < this.hand.size(); i++)
                            if (this.hand.get(i).type == Card.Type.SEVEN) {
                                chosen = i;
                                break;
                            }
                        Main.game.deck.lastCard.drawMultiple += 2;
                        Main.game.deck.lastCard.color = hand.get(chosen).color;
                        hand.remove(chosen);
                    } else {
                        for (int i = 0; i < Main.game.deck.lastCard.drawMultiple; i++)
                            this.hand.add(Main.game.deck.drawCard());
                        Main.game.deck.lastCard.type = Card.Type.SEVEN_USED;
                    }
                }

                case ACE -> {
                    if (hasCard(Card.Type.ACE)) {
                        for (int i = 0; i < this.hand.size(); i++)
                            if (this.hand.get(i).type == Card.Type.ACE) {
                                chosen = i;
                                break;
                            }
                        Main.game.deck.lastCard = hand.get(chosen);
                        hand.remove(chosen);
                    } else Main.game.deck.lastCard.type = Card.Type.ACE_USED;
                }
                default -> {
                    if (hasCard(Main.game.deck.lastCard.color) || hasCard(Main.game.deck.lastCard.type)) {
                        do {
                            chosen = (int) (Math.random() * hand.size());
                        } while (!Main.game.deck.canUse(hand.get(chosen)));
                        Main.game.deck.lastCard = hand.get(chosen);
                        hand.remove(chosen);
                    } else hand.add(Main.game.deck.drawCard());
                }
            }
        }
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
}
