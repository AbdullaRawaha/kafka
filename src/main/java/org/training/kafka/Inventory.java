package org.training.kafka;

public class Inventory {

	private String shopID;
	private String productID;
	private int quantity;
	private String ddtm;

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDdtm() {
		return ddtm;
	}

	public void setDdtm(String ddtm) {
		this.ddtm = ddtm;
	}

}
