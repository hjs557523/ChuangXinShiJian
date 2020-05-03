package com.hjs.system.vo;

import java.io.Serializable;

/**
 * @author 黄继升 16041321
 * @Description: 消息内容模板
 * @date Created in 2020/5/2 23:31
 * @Modified By:
 */
public class TemplateData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String value;

    public TemplateData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TemplateData{" +
                "value='" + value + '\'' +
                '}';
    }
}
