package cn.edu.bupt.zzy.domain;

/**
 * @description: Echarts课程访问TopN实体类
 * @author: zzy
 * @date: 2019/6/1
 **/
public class VideoAccessTopN {

    private String name;
    private long value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
