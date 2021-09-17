package peng.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create by peng on 2021/9/17.
 * 资源加载流
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
