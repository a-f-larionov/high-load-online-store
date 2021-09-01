package store.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PurchaseDTO {

    public Long id;

    public Long userId;

    public Long goodId;

    public Long quantity;
}
