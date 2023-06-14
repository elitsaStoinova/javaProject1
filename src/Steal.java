public class Steal {
    private int type;

    private int position;
    private boolean active;

    public void setType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean getActive(){
        return active;
    }

    public Steal(){}
    public Steal(int type){
        this.type=type;
        this.active = false;
    }
}
