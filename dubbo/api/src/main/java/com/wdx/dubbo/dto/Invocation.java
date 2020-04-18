package com.wdx.dubbo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class Invocation implements Serializable {

    private String clazzName;

    private String methodName;

    private Class<?>[] parameterList;

    private Object[] values;

    private String prefix;

}
