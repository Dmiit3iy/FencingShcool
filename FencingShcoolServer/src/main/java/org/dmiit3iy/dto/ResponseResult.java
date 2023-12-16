package org.dmiit3iy.dto;

import lombok.Data;

@Data
public class ResponseResult<T>{
    private String message;
    private T data;

}
