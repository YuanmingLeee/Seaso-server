package com.seaso.seaso.modules.media.entity;

import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;

public class FastDFSFile implements Serializable {

    private String name;

    private String ext;

    private long length;

    private String author;

    private byte[] content;

    public FastDFSFile(String name, @NotNull byte[] content) {
        this.name = name;
        this.ext = name.substring(name.lastIndexOf('.') + 1);
        this.content = content;
        length = content.length;
        author = Long.toString(UserUtils.getCurrentUserId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        ext = name.substring(name.lastIndexOf('.') + 1);
    }

    public String getExt() {
        return ext;
    }

    public long getLength() {
        return length;
    }

    public String getAuthor() {
        return author;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
        this.length = content.length;
    }

    @Override
    public String toString() {
        return "FastDFSFile: [" + "name=" + name + ", author=" + author + "\n" +
                "content=" + Arrays.toString(content) + ", length=" + length + "]";
    }
}
