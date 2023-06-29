package br.com.erudio.DTO.v1.security.jwt;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3569905621460420099L;

    private String username;
    private String password;

    public AccountCredentialDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialDTO that = (AccountCredentialDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
