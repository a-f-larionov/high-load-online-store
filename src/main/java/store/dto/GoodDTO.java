package store.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GoodDTO {

    public Long id;

    public String name;

    public String description;
}