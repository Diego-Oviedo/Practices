package com.diego.vendingmachine.model.dao.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.diego.vendingmachine.model.dao.*;
import com.diego.vendingmachine.model.dto.*;

@Component("inventory_dao")
public class InventoryDAOImpl implements InventoryDAO {

	public String FILE_PATH = "src/main/resources/";
	public static final String DELIMITER = "::";
	private Map<String, List<Item>> inventory = new HashMap<String, List<Item>>();

	public InventoryDAOImpl() {
		super();
		FILE_PATH = FILE_PATH + "inventory_data.txt";
	}

	public InventoryDAOImpl(String fILE_PATH) { 
		super();
		FILE_PATH = fILE_PATH;
	}

	public Inventory addInventory(String file_name) throws DataSourceException, InventoryException {
		Inventory new_inventory = null;
		Map<String, List<Item>> new_inventory_structure = null;
		try {
			File file = new File(file_name + ".txt");
			if (file.createNewFile()) { 

				new_inventory_structure = new HashMap<String, List<Item>>();

				new_inventory = new Inventory(new_inventory_structure);
			} else {
				throw new DataSourceException("File name already exists.");
			}
		} catch (Exception e) {
			if (e.getClass().equals(DataSourceException.class)) {
				throw new DataSourceException(e.getMessage(), e.getCause());
			} else if (e.getClass().equals(InventoryException.class)) {
				throw new InventoryException(e.getMessage(), e.getCause());
			} else {
				e.printStackTrace();

			}

		}
		return new_inventory;
	}

	public Inventory getInventory() throws DataSourceException, InventoryException {
		loadData();
		Inventory inventory = null;
		try {
			inventory = new Inventory();
			inventory.setInventory(this.inventory);

		} catch (Exception e) {
			if (e.getClass().equals(DataSourceException.class)) {
				throw new DataSourceException(e.getMessage(), e.getCause());
			} else if (e.getClass().equals(InventoryException.class)) {
				throw new InventoryException(e.getMessage(), e.getCause());
			} else {
				e.printStackTrace();

			}

		}

		return inventory;
	}

	public Inventory editInventory() throws DataSourceException, InventoryException {
		writeRecord();
		return getInventory();
	}

	public Inventory removeInventory() throws DataSourceException, InventoryException {
		Inventory inventory = null;
		Inventory deleted_inventory = null;

		try {
			inventory = getInventory();
			deleted_inventory = addInventory("BackUp_inventory_" + LocalDate.now() + ".txt");
			deleted_inventory = inventory;
			editInventory();

			inventory.getInventory().clear();

		} catch (Exception e) {
			if (e.getClass().equals(DataSourceException.class)) {
				throw new DataSourceException(e.getMessage(), e.getCause());
			} else if (e.getClass().equals(InventoryException.class)) {
				throw new InventoryException(e.getMessage(), e.getCause());
			} else {
				e.printStackTrace();

			}

		}

		return deleted_inventory;
	}

	// UTILITY METHODS

	private void loadData() throws DataSourceException, InventoryException {

		Scanner reader;

		try {

			File file = new File(FILE_PATH);
			reader = new Scanner(file);
			reader.useDelimiter(DELIMITER);
		} catch (FileNotFoundException e) {
			throw new DataSourceException("File not found.", e.getCause());
		}

		String currentLine;
		List<Item> current_item;

		while (reader.hasNextLine()) {// while there is data to persist

			currentLine = reader.nextLine();

			current_item = unmarshallObject(currentLine); // Convert a line into an object

			inventory.put(current_item.get(0).getSKU(), current_item);
		}

		reader.close();// once done, close the reader
	}

	private List<Item> unmarshallObject(String objectAsText) {

		String[] objectTokens = objectAsText.split(DELIMITER);// the split method will return an array of string with
																// every piece of data in each element

		Item item = new Item();

		item.setSKU(objectTokens[0]);

		item.setItem_description(objectTokens[1]);

		item.setUnit_price(new BigDecimal((objectTokens[2])).setScale(2, RoundingMode.HALF_UP));

		String path = "src/main/resources/icons/";

		item.setIcon(new ImageIcon(path + "itemDefault_icon.png"));

		String icon_name = objectTokens[3];

		item.getIcon().setDescription(objectTokens[3]);

		item.setIcon(new ImageIcon(path + item.getIcon().getDescription()));

		item.getIcon().setDescription(icon_name);

		int units_in_stock = Integer.valueOf((objectTokens[4]));

		String SKU = "ITM" + // Prefix
				item.getItem_description().substring(0, 2).toUpperCase() + // Description piece
				"000" + // Zeros
				LocalDate.now().getMonth() + // Month
				(String.valueOf(LocalDate.now().getYear()).substring(2, 4));// Year

		item.setSKU(SKU);

		List<Item> item_stock = new ArrayList<Item>();

		while (units_in_stock >= 0) {

			item_stock.add(item);

			units_in_stock--;
		}

		return item_stock;
	}

	private String marshallObject(Item item, int units_in_stock) {
		String itemAsText = item.getSKU() + DELIMITER;
		itemAsText += item.getItem_description() + DELIMITER;
		itemAsText += item.getUnit_price().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
		itemAsText += item.getIcon().getDescription() + DELIMITER;
		itemAsText += units_in_stock;

		return itemAsText;
	}

	private void writeRecord() throws DataSourceException, InventoryException {
		PrintWriter out;

		try {
			out = new PrintWriter(new FileWriter(FILE_PATH));
		} catch (IOException e) {
			throw new DataSourceException("Could not save data.", e);
		}

		String itemAsText;

		Inventory inventory = this.getInventory();

		Set<String> keys = inventory.getInventory().keySet();

		for (String key : keys) {

			List<Item> current_item = inventory.getInventory().get(key);

			if (!current_item.isEmpty()) {

				Item item = current_item.get(0);

				int units_in_stock = (current_item.size() - 1);

				itemAsText = marshallObject(item, units_in_stock);
				// write the object to the file
				out.println(itemAsText);
				// force PrintWriter to write line to the file
				out.flush();
			}
		}
		// Clean up
		out.close();

	}

}
