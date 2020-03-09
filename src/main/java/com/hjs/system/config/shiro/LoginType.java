package com.hjs.system.config.shiro;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/9 13:57
 * @Modified By:
 */
public enum LoginType {
    STUDENT("Student"), ADMIN("Admin"), TEACHER("Teacher");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }}
