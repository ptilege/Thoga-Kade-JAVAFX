package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDetailsDto;
import dto.tm.OrderDetailTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.OrderDetailsModel;
import model.impl.OrderDetailsModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<OrderDetailTm> tblOrderDetail;

    @FXML
    private TableColumn  colOrderId;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableColumn colOption;

    private OrderDetailsModel orderdetailModel = new OrderDetailsModelImpl();

    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadOrderDetailsTable();


    }
    private void loadOrderDetailsTable() {
        ObservableList<OrderDetailTm> tmList = FXCollections.observableArrayList();

        try {
            List<OrderDetailsDto> dtoList = orderdetailModel.allOrders();

            for (OrderDetailsDto dto : dtoList) {
                JFXButton btn = new JFXButton("Delete"); // Use JFXButton here

                OrderDetailTm o = new OrderDetailTm(
                        dto.getOrderId(),
                        dto.getItemCode(),
                        dto.getQty(),
                        dto.getUnitPrice(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    try {
                        if (orderdetailModel.deleteOrder(o.getOrderId())) {
                            loadOrderDetailsTable();
                        } else {
                            System.out.println("Failed to delete order with ID: " + o.getOrderId());
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

                tmList.add(o);
            }

            tblOrderDetail.setItems(tmList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) tblOrderDetail.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private JFXTextField txtSearchOrderId;
    public void searchButtonOnAction(ActionEvent actionEvent) {
        String searchOrderId = txtSearchOrderId.getText().trim();

        if (!searchOrderId.isEmpty()) {
            ObservableList<OrderDetailTm> filteredList = FXCollections.observableArrayList();

            try {
                List<OrderDetailsDto> dtoList = orderdetailModel.allOrders();

                for (OrderDetailsDto dto : dtoList) {
                    if (dto.getOrderId().toLowerCase().contains(searchOrderId.toLowerCase())) {
                        JFXButton btn = new JFXButton("Delete");

                        OrderDetailTm o = new OrderDetailTm(
                                dto.getOrderId(),
                                dto.getItemCode(),
                                dto.getQty(),
                                dto.getUnitPrice(),
                                btn
                        );

                        btn.setOnAction(event -> {
                            try {
                                if (orderdetailModel.deleteOrder(o.getOrderId())) {
                                    loadOrderDetailsTable();
                                } else {
                                    System.out.println("Failed to delete order with ID: " + o.getOrderId());
                                }
                            } catch (SQLException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        });

                        filteredList.add(o);
                    }
                }

                tblOrderDetail.setItems(filteredList);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // If the search text is empty, reload the entire table
            loadOrderDetailsTable();
        }
    }
}
