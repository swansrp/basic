package com.srct.service.dao.entity;

import java.util.Date;

public class AccountInfo {

    /**
     *
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column account_info.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column account_info.account
     *
     * @mbg.generated
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column account_info.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column account_info.create_At
     *
     * @mbg.generated
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column account_info.role
     *
     * @mbg.generated
     */
    private Integer role;

    /**
     *
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column account_info.valid
     *
     * @mbg.generated
     */
    private Integer valid;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column account_info.id
     *
     * @return the value of account_info.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column account_info.id
     *
     * @param id
     *            the value for account_info.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column account_info.account
     *
     * @return the value of account_info.account
     *
     * @mbg.generated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column account_info.account
     *
     * @param account
     *            the value for account_info.account
     *
     * @mbg.generated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column account_info.password
     *
     * @return the value of account_info.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column account_info.password
     *
     * @param password
     *            the value for account_info.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column account_info.create_At
     *
     * @return the value of account_info.create_At
     *
     * @mbg.generated
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column account_info.create_At
     *
     * @param createAt
     *            the value for account_info.create_At
     *
     * @mbg.generated
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column account_info.role
     *
     * @return the value of account_info.role
     *
     * @mbg.generated
     */
    public Integer getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column account_info.role
     *
     * @param role
     *            the value for account_info.role
     *
     * @mbg.generated
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column account_info.valid
     *
     * @return the value of account_info.valid
     *
     * @mbg.generated
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column account_info.valid
     *
     * @param valid
     *            the value for account_info.valid
     *
     * @mbg.generated
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
