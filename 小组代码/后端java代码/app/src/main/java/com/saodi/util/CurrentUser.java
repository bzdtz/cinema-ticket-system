package com.saodi.util;

import javax.servlet.http.HttpServletRequest;

/**
 * app 侧当前登录用户。登录时下发的 token 里 loginAccount 存的是 user.id。
 */
public final class CurrentUser {

    private static final String REQUEST_ATTR = "currentUser.id";

    private CurrentUser() {
    }

    /**
     * @return token 持有者的 user.id；token 缺失、签名不符或非数字时返回 null
     */
    public static Integer id(HttpServletRequest request) {
        Object cached = request.getAttribute(REQUEST_ATTR);
        if (cached instanceof Integer) {
            return (Integer) cached;
        }

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            return null;
        }

        // decode 内部会先做签名与过期校验，失败时返回 null
        String account = TokenUtil.decode(token);
        if (account == null) {
            return null;
        }

        Integer id;
        try {
            id = Integer.valueOf(account);
        } catch (NumberFormatException e) {
            return null;
        }

        request.setAttribute(REQUEST_ATTR, id);
        return id;
    }
}
