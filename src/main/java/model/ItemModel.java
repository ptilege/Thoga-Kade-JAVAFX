package model;

import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    boolean saveItem(ItemDto dto);
    boolean updateItem(ItemDto dto);
    boolean deleteItem(String code);
    ItemDto getItem(String code) throws SQLException, ClassNotFoundException;
    List<ItemDto> allItems() throws SQLException, ClassNotFoundException;
    boolean updateItemQty(String code, int newQty) throws SQLException, ClassNotFoundException;


}
