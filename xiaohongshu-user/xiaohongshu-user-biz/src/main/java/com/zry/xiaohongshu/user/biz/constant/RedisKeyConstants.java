package com.zry.xiaohongshu.user.biz.constant;

public class RedisKeyConstants {
    /**
     * 自增key
     */
    public static final String XIAOHONGSHU_ID_GENERATOR_KEY = "xiaohongshu.id.generator";

    /**
     * 用户角色数据 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    public static String buildUserRoleKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

    /**
     * 角色对应权限集合
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";
    public static String buildRolePermissionsKey(String roleId) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleId;
    }
}
