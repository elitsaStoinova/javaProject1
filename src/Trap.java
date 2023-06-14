public class Trap {
    private boolean isActive;
    private boolean isOpened;
    private int position;
    private String type;
    private Player setByPlayer;
    private Player openedByPlayer;

    public int getPosition(){
        return position;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type=type;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public void setActive(boolean active){
        isActive = active;
    }

    public boolean getIsOpened(){
        return isOpened;
    }

    public void setOpened(boolean opened){
        this.isOpened = opened;
    }
    public Player getSetByPlayer(){
        return setByPlayer;
    }

    public void setSetByPlayer(Player player){
        this.setByPlayer = player;
    }

    public void getOpenedByPlayer(Player player){
        this.openedByPlayer = player;
    }

    public Player getOpenedByPlayer(){
        return getOpenedByPlayer();
    }

    public Trap(int pos){
        position = pos;
        isActive=false;
        isOpened=false;
    }

    public Trap(){

    }


}
