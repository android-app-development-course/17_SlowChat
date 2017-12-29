package com.chat.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "chat", catalog = "")
public class UserEntity {
    private short id;
    private String email;
    private String pwd;
    private String signature;
    private int integral;
    private byte status;
    private String img;
    private String certificate;
    private Collection<FriendEntity> friendsById;
    private Collection<FriendEntity> friendsById_0;
    private Collection<FriendApplyEntity> friendAppliesById;
    private Collection<FriendApplyEntity> friendAppliesById_0;
    private Collection<MessageEntity> messagesById;
    private Collection<MessageEntity> messagesById_0;
    private Collection<UserTagEntity> userTagsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (integral != that.integral) return false;
        if (status != that.status) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (pwd != null ? !pwd.equals(that.pwd) : that.pwd != null) return false;
        if (signature != null ? !signature.equals(that.signature) : that.signature != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;
        if (certificate != null ? !certificate.equals(that.certificate) : that.certificate != null) return false;

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

    @OneToMany(mappedBy = "userBySourceId")
    public Collection<FriendEntity> getFriendsById() {
        return friendsById;
    }

    public void setFriendsById(Collection<FriendEntity> friendsById) {
        this.friendsById = friendsById;
    }

    @OneToMany(mappedBy = "userByTargetId")
    public Collection<FriendEntity> getFriendsById_0() {
        return friendsById_0;
    }

    public void setFriendsById_0(Collection<FriendEntity> friendsById_0) {
        this.friendsById_0 = friendsById_0;
    }

    @OneToMany(mappedBy = "userBySourceId")
    public Collection<FriendApplyEntity> getFriendAppliesById() {
        return friendAppliesById;
    }

    public void setFriendAppliesById(Collection<FriendApplyEntity> friendAppliesById) {
        this.friendAppliesById = friendAppliesById;
    }

    @OneToMany(mappedBy = "userByTargetId")
    public Collection<FriendApplyEntity> getFriendAppliesById_0() {
        return friendAppliesById_0;
    }

    public void setFriendAppliesById_0(Collection<FriendApplyEntity> friendAppliesById_0) {
        this.friendAppliesById_0 = friendAppliesById_0;
    }

    @OneToMany(mappedBy = "userBySourceId")
    public Collection<MessageEntity> getMessagesById() {
        return messagesById;
    }

    public void setMessagesById(Collection<MessageEntity> messagesById) {
        this.messagesById = messagesById;
    }

    @OneToMany(mappedBy = "userByTargetId")
    public Collection<MessageEntity> getMessagesById_0() {
        return messagesById_0;
    }

    public void setMessagesById_0(Collection<MessageEntity> messagesById_0) {
        this.messagesById_0 = messagesById_0;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserTagEntity> getUserTagsById() {
        return userTagsById;
    }

    public void setUserTagsById(Collection<UserTagEntity> userTagsById) {
        this.userTagsById = userTagsById;
    }
}
