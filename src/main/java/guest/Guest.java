package guest;

public class Guest {


    private String name;
    private double currentWallet;
    private double spending;


    public Guest(String name, Double currentWallet, Double spending) {
        this.name          = name;
        this.currentWallet = currentWallet;
        this.spending      = spending;
    }

    public String getName() {
        return name;
    }

    public Double getCurrentWallet() {
        return currentWallet;
    }

    public Double getSpending() {
        return spending;
    }
}
