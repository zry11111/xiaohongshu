package com.zry.xiaohongshu.auth.constant;

public class RedisKeyConstants {
    /**
     * 验证码 KEY 前缀
     */
    private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code:";
    /**
     * 自增key
     */
    public static final String XIAOHONGSHU_ID_GENERATOR_KEY = "xiaohongshu.id.generator";

    /**
     * 构建验证码 KEY
     * @param phone
     * @return
     */
    public static String buildVerificationCodeKey(String phone) {
        return VERIFICATION_CODE_KEY_PREFIX + phone;
    }
    /**
     * 用户角色数据 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    public static String buildUserRoleKey(String phone) {
        return USER_ROLES_KEY_PREFIX + phone;
    }

    /**
     * 角色对应权限集合
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";
    public static String buildRolePermissionsKey(Long roleId) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleId;
    }
}
