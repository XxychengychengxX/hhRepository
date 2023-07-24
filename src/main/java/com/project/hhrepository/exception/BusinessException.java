/**
 * @author Valar Morghulis
 * @Date 2023/7/23
 */
package com.project.hhrepository.exception;

//自定的运行时异常
public class BusinessException extends RuntimeException {

    //只是创建异常对象
    public BusinessException() {
        super();
    }


    public BusinessException(String message) {
        super(message);
    }
}
