public class Player {
    private String hero;
    private String pieceType;
    private int gold;
    private int attackPoints;
    private float ratio;


    public Player() {
    }


    public void printInfo(){
        System.out.println(
                this.hero+ "(" +
                        this.pieceType + "," +
                       this.gold + " Gold," +
                        this.attackPoints + " Attack )"
        );


    }

    public String getPieceType() {
        return pieceType;
    }

    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }
    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }



    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
