package com.seaso.seaso.modules.sys.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.common.persistance.Update;
import com.seaso.seaso.common.utils.idgen.IdGen;
import com.seaso.seaso.common.web.validation.annotation.Username;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.function.Consumer;

/**
 * User entity.
 *
 * @author Li Yuanming
 * @version 0.3
 */
@Entity
@Table(name = "user", indexes = {@Index(name = "user_user_id_uindex", columnList = "userId", unique = true)})
public class User extends DataEntity<User> {

    @Transient
    private static final long serialVersionUID = 912182812071648668L;

    @Null(message = "Set invalid field", groups = {Update.class})
    @Column(nullable = false, updatable = false, length = 64)
    private Long userId;

    @Range(max = 99, message = "Please set an age between 0 and 99")
    @Column(length = 2)
    private Integer age;

    @Username
    @Column(nullable = false, length = 32)
    private String username;

    @Null(message = "Set invalid field")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = Integer.MAX_VALUE)
    private byte[] avatar;

    @Size(max = 0, message = "Set invalid field", groups = {Update.class})
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String history = "";

    @Size(max = 0, message = "Set invalid field", groups = {Update.class})
    @Lob
    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String myLikes = "";

    public User() {
        super();
    }

    private User(String username, Integer age) {
        super();
        this.username = username;
        this.age = age;
    }

    public static class UserBuilder {
        public String username;
        public Integer age;

        public UserBuilder with(Consumer<UserBuilder> build) {
            build.accept(this);
            return this;
        }

        public User build() {
            return new User(username, age);
        }
    }

    public static User getGuestInstance() {
        User guest = new User("Guest", null);
        guest.userId = -1L;
        return guest;
    }

    @Override
    protected void setDataId() {
        userId = IdGen.generateId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getMyLikes() {
        return myLikes;
    }

    public void setMyLikes(String myLikes) {
        this.myLikes = myLikes;
    }
}
