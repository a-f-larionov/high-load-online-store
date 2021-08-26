package store.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserDTO {

    /**
     * User id
     */
    public Long id;

    /**
     * User name
     */
    public String username;
}