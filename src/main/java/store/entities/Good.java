package store.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Data
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, message = "Не менее двух символов")
    private String name;

    private String description;

    @Min(value = 0, message = "Цена не может быть меньше 0")
    private BigDecimal price;

    @Min(value = 0, message = "Количество не может быть меньше 0")
    private long quantity;
}
