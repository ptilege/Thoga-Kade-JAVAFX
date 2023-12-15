package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderDetailTm extends RecursiveTreeObject<OrderDetailTm> {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
    private JFXButton btn;

    public OrderDetailTm(String orderId, String itemCode, int qty, double unitPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
