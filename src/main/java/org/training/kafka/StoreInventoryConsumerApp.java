package org.training.kafka;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class StoreInventoryConsumerApp {

	public static void main(String args[]) {

		runConsumer();
	}

	static void runConsumer() {
		InventoryDAO inventoryDAO = new InventoryDAO();
		
		Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

		int noMessageFound = 0;

		while (true) {
			ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(100));
			// 1000 is the time in milliseconds consumer will wait if no record is found at
			// broker.
			if (consumerRecords.count() == 0) {
				noMessageFound++;
				System.out.println("noMessageFound: " + noMessageFound);
				if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
					// If no message found count is reached to threshold exit loop.
					break;
				else
					continue;
			}

			// print each record.
			consumerRecords.forEach(record -> {
				
		
				
				 String storeInventory = record.value();
				
		//		System.out.println("Record Key " + record.key());
				System.out.println("Record value " + record.value());
			//	System.out.println("Record partition " + record.partition());
		//		System.out.println("Record offset " + record.offset());
				
				
		
				
				String [] storeInventorySplit = storeInventory.split(",");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				
				inventoryDAO.dispatchInventoryInsertion(storeInventorySplit);
				
				System.out.println("StoreID " + storeInventorySplit[0]);
				System.out.println("ProductID " + storeInventorySplit[1]);
				System.out.println("Quantity Shipped " + storeInventorySplit[2]);
				
				
				
			});

			// commits the offset of record to broker.
			consumer.commitAsync();
			
			
		}
		consumer.close();
		
	}

}
