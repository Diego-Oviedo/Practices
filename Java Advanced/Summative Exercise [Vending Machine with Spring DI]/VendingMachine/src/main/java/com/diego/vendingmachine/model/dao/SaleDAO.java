package com.diego.vendingmachine.model.dao;

import com.diego.vendingmachine.model.dto.*;

public interface SaleDAO {

	public Item addSale(Inventory inventory, Sale sale);
}
