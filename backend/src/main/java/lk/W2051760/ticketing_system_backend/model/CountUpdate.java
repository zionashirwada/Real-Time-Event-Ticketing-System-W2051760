package lk.W2051760.ticketing_system_backend.model;

public class CountUpdate {
    private int vendorCount;
    private int customerCount;

    public CountUpdate(int vendorCount, int customerCount) {
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }
}
