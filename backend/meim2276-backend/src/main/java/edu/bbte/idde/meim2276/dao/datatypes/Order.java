package edu.bbte.idde.meim2276.dao.datatypes;

public class Order extends BaseEntity {
    private int buyerId;
    private String dateOfOrder;
    private String dateOfDelivery;
    private String status;
    private double total;

    public Order(int id, int buyerId, String dateOfOrder, String dateOfDelivery, String status, double total) {
        super();
        setId(id);
        this.buyerId = buyerId;
        this.dateOfOrder = dateOfOrder;
        this.dateOfDelivery = dateOfDelivery;
        this.status = status;
        this.total = total;
    }

    public Order() {
        super();
        setId(0);
        buyerId = 0;
        dateOfOrder = "";
        dateOfDelivery = "";
        status = "";
        total = 0.0;

    }

    public int getBuyerId() {
        return buyerId;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public String getDateOfDelivery() {
        return dateOfDelivery;
    }

    public String getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public void setDateOfDelivery(String dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
