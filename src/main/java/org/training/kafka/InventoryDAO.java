package org.training.kafka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class InventoryDAO {

	public void dispatchInventoryInsertion(String[] storeInventorySplit) {

		PreparedStatement preparedStatement;

		try {

			preparedStatement = MySQLDatabaseConnection.dataBaseConnection().prepareStatement(
					"insert into dispatchInventory (shopID,productID,quantity,ddtm) values (?,?,?,CURRENT_TIMESTAMP)");

			preparedStatement.setString(1, storeInventorySplit[0]);
			preparedStatement.setString(2, storeInventorySplit[1]);
			preparedStatement.setString(3, storeInventorySplit[2]);

			int rowsInserted = preparedStatement.executeUpdate();
			System.out.println("Rows Inserted: " + rowsInserted);

		}

		catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void inventoryCount() {

		try {

			Connection conn = MySQLDatabaseConnection.dataBaseConnection();
			System.out.println(conn.toString());

			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT count(*) FROM dispatchInventory WHERE (ddtm BETWEEN ? AND ?)");

			preparedStatement.setTimestamp(1, Timestamp.valueOf("2021-05-21 12:33:00"));
			preparedStatement.setTimestamp(2, Timestamp.valueOf("2021-05-21 16:37:00"));

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {

				System.out.println(rs.getInt(1));
			}

			int count = rs.getInt(1);

			stockAvalibilityUpdation(count);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void stockAvalibilityUpdation(int count) {
		Connection conn = null;
		try {
			conn = MySQLDatabaseConnection.dataBaseConnection();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println(conn.toString());

		try {

			PreparedStatement preparedStatement = conn.prepareStatement("select * from stockAvalibility");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Inventory inventory = new Inventory();

				inventory.setProductID(rs.getString(1));
				inventory.setQuantity(rs.getInt(2));

				System.out.println("ProductID : " + inventory.getProductID());
				System.out.println("Quantity : " + inventory.getQuantity());

				int updatedQuantity = inventory.getQuantity() - count;

				System.out.println("Quantity : " + updatedQuantity);

				preparedStatement = MySQLDatabaseConnection.dataBaseConnection()
						.prepareStatement("update stockAvalibility set quantity =?");

				preparedStatement.setInt(1, updatedQuantity);

				int rowsInserted = preparedStatement.executeUpdate();

				System.out.println("Rows Inserted: " + rowsInserted);

				conn.commit();
			}

		} catch (SQLException e) {

			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void dispatchInventoryDataRetrieval() {

		try {

			PreparedStatement preparedStatement = MySQLDatabaseConnection.dataBaseConnection()
					.prepareStatement("select * from dispatchInventory");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Inventory inventory = new Inventory();

				inventory.setShopID(rs.getString(1));
				inventory.setProductID(rs.getString(2));
				inventory.setQuantity(rs.getInt(3));
				inventory.setDdtm(rs.getString(4));

				System.out.println("ShopID   : " + inventory.getShopID());
				System.out.println("ProductID : " + inventory.getProductID());
				System.out.println("Quantity : " + inventory.getQuantity());
				System.out.println("DDTM : " + inventory.getDdtm());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
