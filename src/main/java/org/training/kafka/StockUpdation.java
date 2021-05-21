package org.training.kafka;

public class StockUpdation {

	public static void main(String args[]) {
		
		InventoryDAO inventory = new InventoryDAO();
		
	inventory.dispatchInventoryDataRetrieval();
	inventory.inventoryCount();
		
		
	}
	
}
