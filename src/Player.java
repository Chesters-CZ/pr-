import java.util.ArrayList;
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
            hand.add(Deck.drawCard());
        }
        this.isHuman = isHuman;
    }

    public boolean won() {
        return hand.size() == 0;
    }

    public void playSth() {
        if (this.isHuman) {
            StringBuilder visualizedCards = new StringBuilder();
            for (int i = 0; i < hand.size(); i++) {
                if (this.hand.get(i).canUse())
                    visualizedCards.append(switch (this.hand.get(i).color) {
                        case ACORNS -> MikolasovyConsoleBarvy.YELLOW;
                        case BALLS -> MikolasovyConsoleBarvy.CYAN;
                        case HEARTS -> MikolasovyConsoleBarvy.RED;
                        case LEAVES -> MikolasovyConsoleBarvy.GREEN;
                        default -> MikolasovyConsoleBarvy.PURPLE;
                    });
                else visualizedCards.append(MikolasovyConsoleBarvy.BLACK);
                visualizedCards.append(switch (this.hand.get(i).type) {
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
            visualizedCards.append(switch (Instances.deck.lastCard.type) {
                case ACE -> "PASS";
                case SEVEN -> "DRW" + Instances.deck.lastCard.drawMultiple;
                default -> "DRAW";
            });
            visualizedCards.append("\n");

            for (int i = 0; i < this.hand.size(); i++) {
                if (i < 10)
                    visualizedCards.append(" ").append(i).append("\t");
                else visualizedCards.append(i).append("\t");
            }
            visualizedCards.append(this.hand.size());
            System.out.println(visualizedCards);

            int chosen;
            while (true) {
                try {
                    chosen = Instances.scanner.nextInt();
                    if (chosen > this.hand.size() || chosen < 0) throw new Exception();
                } catch (Exception e) {
                    System.out.println("Zvolil jsi neplatnou hodnotu.");
                    continue;
                }
                if (Instances.deck.lastCard.type == Card.Type.ACE) {
                    Instances.deck.lastCard.type = Card.Type.ACE_USED;
                    break;
                } else if (Instances.deck.lastCard.type == Card.Type.SEVEN) {
                    if (chosen == this.hand.size()) {
                        for (int i = 0; i < Instances.deck.lastCard.drawMultiple; i++) this.hand.add(Deck.drawCard());
                        Instances.deck.lastCard.type = Card.Type.SEVEN_USED;
                        break;
                    } else if (hand.get(chosen).canUse() && hand.get(chosen).type == Card.Type.SEVEN) {
                        Instances.deck.lastCard.drawMultiple += 2;
                        break;
                    }
                } else if (chosen == this.hand.size()) {
                    this.hand.add(Deck.drawCard());
                    break;
                } else if (hand.get(chosen).canUse()) {
                    Instances.deck.lastCard = hand.get(chosen);
                    hand.remove(hand.get(chosen));
                    break;
                }
            }

        } else {
            int chosen = (int) (Math.random() * hand.size());
            if (Instances.deck.lastCard.type == Card.Type.ACE) {
                Instances.deck.lastCard.type = Card.Type.ACE_USED;
            } else if (Instances.deck.lastCard.type == Card.Type.SEVEN) {
                if (chosen == this.hand.size()) {
                    for (int i = 0; i < Instances.deck.lastCard.drawMultiple; i++) this.hand.add(Deck.drawCard());
                    Instances.deck.lastCard.type = Card.Type.SEVEN_USED;
                } else if (hand.get(chosen).canUse() && hand.get(chosen).type == Card.Type.SEVEN) {
                    Instances.deck.lastCard.drawMultiple += 2;
                }
            } else if (chosen == this.hand.size()) {
                this.hand.add(Deck.drawCard());
            } else if (hand.get(chosen).canUse()) {
                Instances.deck.lastCard = hand.get(chosen);
                hand.remove(hand.get(chosen));
            }
            System.out.println("AI played " + Instances.deck.lastCard.type + " of " + Instances.deck.lastCard.color);
        }
    }
}
