public class Player {
    private String name;
    private String nickname;
    private double money;
    private int position;

    private Steal steal;

    public void setSteal(Steal steal){
        this.steal = steal;
    }

    public Steal getSteal(){
        return steal;
    }


    public int getPosition(){
        return position;
    }

    public String getName(){
        return name;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nckname){
        this.nickname = nckname;
    }
    public void setPosition(int position){
        this.position = position;
    }

    public double getMoney(){
        return money;
    }
    public void setMoney(){
        this.money = 1000;
    }
    public void setMoney(double money){
        this.money = money;
    }
    public Player(String name){
        this.name = name;
    }


    public double playerLoseMoney(double moneyToGive){
        return (this.money - moneyToGive);
    }

    public double playerGetsMoney(double moneyToGet){
        return (this.money + moneyToGet);
    }
}
