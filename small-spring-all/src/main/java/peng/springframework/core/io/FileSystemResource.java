package peng.springframework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Create by peng on 2021/09/17.
 * 从文件中读取
 */
public class FileSystemResource implements Resource {

    private final File file;

    private final String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        //给路径就创建新对象，保证安全
        this.file = new File(path);
        this.path = path;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        // 返回创建的新对象，保证安全
        return new FileInputStream(this.file);
    }

    public final String getPath() {
        return this.path;
    }
}
