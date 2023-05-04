package money.tracker.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="user_tb")
public class User {
    /** Порядковый номер записи */
    @Id
    @GeneratedValue
    private Long id;
    /** Тип записи */
    private String username;
    /** Указанная в записи стоимость */
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}