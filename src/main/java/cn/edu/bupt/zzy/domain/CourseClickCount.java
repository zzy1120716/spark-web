package cn.edu.bupt.zzy.domain;

import org.springframework.stereotype.Component;

/**
 * @ClassName: CourseClickCount
 * @description: 课程访问数量实体类
 * @author: zzy
 * @date: 2019-05-07 11:01
 * @version: V1.0
 **/
@Component
public class CourseClickCount {

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
