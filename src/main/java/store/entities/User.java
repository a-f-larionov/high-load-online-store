package store.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, message = "Не менее 2 ух символов")
    private String username;

    @Size(min=2, message = "Не менее 2ух символов")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> role;
}
