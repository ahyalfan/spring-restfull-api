package my.ahyalfan.restfull.restfull.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiration;

    @OneToMany(mappedBy = "user")
    private List<Contact> posts;

}
