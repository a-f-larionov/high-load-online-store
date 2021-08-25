package store.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data
public class Role {

    @Id
    private Long id;

    @Size(min = 2, message = "Не менее 2ух символов")
    private String name;
}
