package pl.haladyj.email_config.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Size(min=3, max=15)
    private String login;

    @NotNull
    @Column(nullable = false)
    @Size(min=5, max=50)
    private String email;

    @NotNull
    @Column(nullable = false)
    @Size(min = 8, max = 20)
    private String password;

    @JsonIgnore
    private String activationKey;

    private boolean isActive;


}
