package com.chat.service.login;

public interface RegisterService {

    /**
     * 检测邮箱格式是否符合格式，并且查看数据库中的邮箱是否已经存在
     * @param email 邮箱地址
     * @return 如果符合格式则返回true,否则返回false
     */
    public boolean isEmailFit(String email);

    /**
     * 检测邮箱是否尚未注册
     * @param eamil 邮箱地址
     * @return 如果尚未注册则返回ture否则返回false
     */
    public boolean isEmailUnregistered(String eamil);

    /**
     * 检测密码是否符合格式
     * @param pwd 用户密码
     * @return 如果符合格式则返回true，否则返回false
     */
    public boolean isPwdFit(String pwd);

    /**
     * 注册一个新的用户，并把他写入到数据库中
     * @param email
     * @param pwd
     */
    public void addUser(String email,String pwd);
}
