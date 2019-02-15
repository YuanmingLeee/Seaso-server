package com.seaso.seaso.modules.user.entity;

import com.seaso.seaso.common.persistance.DataEntity;

import javax.persistence.*;

@Entity
@Table(name = "user", indexes = {@Index(name = "user_user_id_uindex", columnList = "userId", unique = true)})
public class User extends DataEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 16)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
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
    private String myLikes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 166777215)
    private String myDislikes;

    public User() {
        super();
    }

    public User(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
