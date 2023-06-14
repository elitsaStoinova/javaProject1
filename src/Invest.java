public class Invest {
    private String index;
    private String name;
    private double minSum;
    private double coefficient;
    private int minInterval;
    private int maxInterval;


    public String getIndex(){
        return index;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getMinSum(){
        return minSum;
    }

    public void setMinSum(double minSum){
        this.minSum = minSum;
    }

    public double getCoefficient(){
        return coefficient;
    }

    public void setCoefficient(double coefficient){
        this.coefficient = coefficient;
    }

    public int getMinInterval(){
        return minInterval;
    }

    public void setMinInterval(int maxInterval){
        this.minInterval = minInterval;
    }

    public int getMaxInterval(){
        return maxInterval;
    }

    public void setMaxInterval(int maxInterval){
        this.maxInterval = maxInterval;
    }

    public Invest(){

    }

    public Invest(String index, String name, double minSum, double coefficient, int minInterval, int maxInterval){
        this.index = index;
        this.name = name;
        this.minSum = minSum;
        this.coefficient = coefficient;
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
    }

}
