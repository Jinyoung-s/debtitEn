/**
 * The UserMemo class is for the sessionUser of spring security
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/02/2020
 *
 * 5/08 implement spring security login functionality
 */
package so.debateit.domain.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import so.debateit.common.model.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class SessionUser implements UserDetails, Serializable {

    private static final long serialVersionUID = -7144174657188362966L;

    private Long userNmbr;
    private String username;
    private String password;
    private String userNick;
    private String userImag;

    public SessionUser(User user) {
        this.username = user.getUserId();
        this.password = user.getUserPass();
        this.userNmbr = user.getUserNmbr();
        this.userNick = user.getUserNick();
        this.userImag = user.getUserImag();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getUserNmbr() { return userNmbr; }

    public String getUserNick() {return userNick;}

    public String getUserImag () {return userImag;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionUser)) return false;
        SessionUser that = (SessionUser) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                ", username='" + username + '\'' +
                ", password=[Protected]" +
                '}';
    }


}