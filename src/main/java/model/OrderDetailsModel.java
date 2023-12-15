package model;

import dto.OrderDetailsDto;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsModel {
    boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException;
    List<OrderDetailsDto> allOrders() throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException;

    OrderDto getOrder(String orderId) throws SQLException, ClassNotFoundException;
}
