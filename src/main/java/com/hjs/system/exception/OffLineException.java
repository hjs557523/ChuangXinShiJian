package com.hjs.system.exception;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/5 20:32
 * @Modified By:
 */
public class OffLineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OffLineException() {
        super("较长时间未进行操作，需要重新进入");
    }
}
