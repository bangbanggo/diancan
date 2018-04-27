package edu.black.entity;

public class Food {
    private int id;
    private String name;
    private double price;
    private int amount;
    private double totalprice;
    private String image;
    private String more;
    private int salevolums;
    private String status;

    public Food(String name, double price, int amount, double totalprice, String image, String more) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.totalprice = totalprice;
        this.image = image;
        this.more = more;
    }

    public Food() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSalevolums() {
        return salevolums;
    }

    public void setSalevolums(int salevolums) {
        this.salevolums = salevolums;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

}
