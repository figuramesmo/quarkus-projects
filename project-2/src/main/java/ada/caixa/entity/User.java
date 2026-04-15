package ada.caixa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name="usuarios") // The "user" and "users" table names are reserved in H2, using "usuarios" instead
public class User extends PanacheEntityBase {

    public User(){
    }

    public User(
        String name,
        String email,
        String password
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    public User(
        String name,
        String email,
        String password,
        String role
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}
