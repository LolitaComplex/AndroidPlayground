package com.doing.architectureencapsulation.listadapter.threetype.entity;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public class ThreeType {

    private static int index = 0;

    private String content;

    private String url;

    private boolean flag;

    public ThreeType(String content) {
        flag = (index % 2) == 0;
        index++;
        this.content = content;
    }

    public ThreeType(String content, String url) {
        this(content);
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getFlag() {
        return flag;
    }
}
