package com.seaso.seaso.modules.sys.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.modules.sys.utils.UserUtils;

import javax.persistence.*;

@Entity
@Table(name = "user", indexes = {@Index(name = "user_user_id_uindex", columnList = "userId", unique = true)})
public class User extends DataEntity<User> {

    @Column(nullable = false, length = 64)
    private Long userId;

    @Column(nullable = false, length = 2)
    private Integer age;

    @Column(nullable = false, length = 32)
    private String username;

    @Transient
    private String password;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 16777215)
    private byte[] avatar;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 166777215)
    private String history;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 166777215)
    private String myLikes = "";

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 166777215)
    private String myDislikes = "";

    public User() {
        super();
    }

    @Override
    protected void setDataId() {
        userId = UserUtils.idGenerate(1).get(0);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMyDislikes() {
        return myDislikes;
    }

    public void setMyDislikes(String myDislikes) {
        this.myDislikes = myDislikes;
    }
}
