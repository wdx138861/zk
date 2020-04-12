package cow.wdx.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 一定要记得实现Serializable接口，否则无法进行网络传输
 */
@Data
public class Invocation implements Serializable {

    private String className;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] paramValues;

}
