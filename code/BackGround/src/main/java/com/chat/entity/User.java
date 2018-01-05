package com.chat.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    private short id;
    private String email;
    private String pwd;
    private String signature;
    private int integral;
    private byte status;
    private String img;
    private String certificate;
    private String username;
    private Set<User> friends;
    private Set<User> acceptFriends;
    private Set<Tag> tags;
    private Set<Message> messages;

    @Id
    @Column(name = "id", nullable = false)
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 60)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "pwd", nullable = false, length = 60)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "signature", nullable = true, length = 60)
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Basic
    @Column(name = "integral", nullable = false)
    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "img", nullable = true, length = 120)
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "certificate", nullable = true, length = 60)
    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    @Basic
    @Column(name = "username",nullable = false,length = 60)
    public String getUsername() {
        return username;
    }

    @OneToMany
    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @ManyToMany
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToMany
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @ManyToMany
    public Set<User> getAcceptFriends() {
        return acceptFriends;
    }

    public void setAcceptFriends(Set<User> acceptFriends) {
        this.acceptFriends = acceptFriends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (integral != user.integral) return false;
        if (status != user.status) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (pwd != null ? !pwd.equals(user.pwd) : user.pwd != null) return false;
        if (signature != null ? !signature.equals(user.signature) : user.signature != null) return false;
        if (img != null ? !img.equals(user.img) : user.img != null) return false;
        if (certificate != null ? !certificate.equals(user.certificate) : user.certificate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        result = 31 * result + integral;
        result = 31 * result + (int) status;
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        return result;
    }
}
