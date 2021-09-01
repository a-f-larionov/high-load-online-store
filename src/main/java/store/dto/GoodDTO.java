package store.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Data
public class GoodDTO {

    public Long id;

    public String name;

    public String description;

    public BigDecimal price;

    public Long quantity;
}