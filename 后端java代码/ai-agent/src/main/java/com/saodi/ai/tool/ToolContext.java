package com.saodi.ai.tool;

/**
 * <p>
 *  工具执行时的服务端上下文。userId 由 controller 从登录态写进来，
 *  模型既看不到也改不了——它只能决定"查什么"，决定不了"以谁的身份查"。
 * </p>
 *
 * @author saodi
 */
public class ToolContext {

    private final Integer userId;

    public ToolContext(Integer userId) {
        this.userId = userId;
    }

    /** 没有登录态（比如离线探针）时走这个，需要身份的工具会拒绝签发凭证 */
    public static ToolContext anonymous() {
        return new ToolContext(null);
    }

    public Integer userId() {
        return userId;
    }

    public boolean signedIn() {
        return userId != null;
    }
}
