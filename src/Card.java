public class Card {
    public Card(Type type, Color color){
        this.color = color;
        this.type = type;
    }

    public Color color;
    public Type type;
    public enum Color{
        HEARTS, BALLS, ACORNS, LEAVES
    }
    public enum Type{
        SEVEN, EIGHT, NINE, TEN, JEAN, QUEEN, KING, ACE
    }
    public boolean canUse(){
        
    }
}
