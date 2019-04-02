package com.seaso.seaso.modules.sys.entity;

import com.seaso.seaso.common.persistance.DataEntity;
import com.seaso.seaso.common.utils.idgen.IdGen;

import javax.persistence.*;

@Entity
@Table(name = "t_user", indexes = {@Index(name = "user_user_id_uindex", columnList = "userId", unique = true)})
public class User extends DataEntity<User> {

    private static final long serialVersionUID = 912182812071648668L;

    @Column(nullable = false, length = 64)
    private Long userId;

    @Column(length = 2)
    private Integer age;

    @Column(nullable = false, length = 32)
    private String username;

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

    public String getMyDislikes() {
        return myDislikes;
    }

    public void setMyDislikes(String myDislikes) {
        this.myDislikes = myDislikes;
    }
}
