package ec.com.edimca.posbackend.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ec.com.edimca.posbackend.validation.UserValidation;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@Entity
@Table(name = "\"user\"", schema = "public")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(groups = { UserValidation.New.class, UserValidation.Update.class })
    @NotEmpty(groups = { UserValidation.New.class, UserValidation.Update.class })
    @Column(name = "first_name")
    private String firstName;

    @NotNull(groups = { UserValidation.New.class, UserValidation.Update.class })
    @NotEmpty(groups = { UserValidation.New.class, UserValidation.Update.class })
    @Column(name = "last_name")
    private String lastName;

    @NotNull(groups = { UserValidation.New.class, UserValidation.Update.class })
    @NotEmpty(groups = { UserValidation.New.class, UserValidation.Update.class })
    @Email(groups = { UserValidation.New.class, UserValidation.Update.class })
    private String email;

    private Boolean active;

    @NotNull(groups = { UserValidation.New.class, UserValidation.Update.class })
    @NotEmpty(groups = { UserValidation.New.class })
    @Getter(AccessLevel.NONE)
    private String password;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<UserRole> roles = new ArrayList<UserRole>();

    // Custom Getters and Setters

    @JsonProperty(access = Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

}
