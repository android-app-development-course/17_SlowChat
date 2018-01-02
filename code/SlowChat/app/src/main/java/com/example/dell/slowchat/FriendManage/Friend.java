package com.example.dell.slowchat.FriendManage;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 皓宸 on 2017/12/29.
 */

public class Friend implements Parcelable {
    private String uid;
    private String userName;
    private Bitmap headIcon;
    private String signature;
    private int relationLevel;
    public String sex;
    public String addr;
    public String tag;
    public String birthday;
    public int age;

    public String getUid(){
        return uid;
    }
    public String getUserName(){
        return userName;
    }
    public String getSignature(){
        return signature;
    }
    public Bitmap getHeadIcon(){
        return headIcon;
    }
    public int getRelationLevel(){return relationLevel;}
    public String getSex(){return sex;}
    public String getAddr(){return addr;}
    public String getTag(){return tag;}
    public String getBirthday(){return  birthday;}
    public int getAge(){return age;}

    public void setUid(String uid){this.uid=uid;}
    public void setUserName(String userName){this.userName=userName;}
    public void setSignature(String signature){this.signature=signature;}
    public void setRelationLevel(int digit){relationLevel=digit;}
    public void setHeadIcon(Bitmap bm){headIcon=bm;}
    public void setSex(String sex){this.sex=sex;}
    public void setAddr(String addr){this.addr=addr;}
    public void setTag(String tag){this.tag=tag;}
    public void setBirthday(String birthday){this.birthday=birthday;}
    public void setAge(int age){this.age=age;}


    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(uid);
        dest.writeString(userName);
        dest.writeString(signature);
        dest.writeString(sex);
        dest.writeString(addr);
        dest.writeString(tag);
        dest.writeString(birthday);
        dest.writeInt(relationLevel);
        dest.writeInt(age);
     //   headIcon.writeToParcel(dest,0);

    }

    public static final Parcelable.Creator<Friend> CREATOR=new Parcelable.Creator<Friend>() {
        public Friend[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Friend[size];
        }

        public Friend createFromParcel(Parcel source){
            Friend f=new Friend();
            f.uid=source.readString();
            f.userName=source.readString();
            f.signature=source.readString();
            f.sex=source.readString();
            f.addr=source.readString();
            f.tag=source.readString();
            f.birthday=source.readString();
            f.relationLevel=source.readInt();
            f.age=source.readInt();
      //      f.headIcon=Bitmap.CREATOR.createFromParcel(source);

            return f;
        }
    };

}
