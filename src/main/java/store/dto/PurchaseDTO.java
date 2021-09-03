package store.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Data
public class PurchaseDTO {

    public Long id;

    public Long userId;

    public Long goodId;

    public Long quantity;

    private BigDecimal price;

    private Date createdAt;
}
