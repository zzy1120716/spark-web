package cn.edu.bupt.zzy.dao;

import cn.edu.bupt.zzy.domain.VideoAccessTopN;
import cn.edu.bupt.zzy.utils.MySQLUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 面向接口编程
 * @author: zzy
 * @date: 2019/6/1
 **/
@Component
public class VideoAccessTopNDAO {

    static Map<String, String> courses = new HashMap<String, String>();
    static {
        courses.put("4000", "MySQL优化");
        courses.put("4500", "Crontab");
        courses.put("4600", "Swift");
        courses.put("14540", "SpringData");
        courses.put("14704", "R");
        courses.put("14390", "机器学习");
        courses.put("14322", "redis");
        courses.put("14390", "神经网络");
        courses.put("14623", "Docker");
    }

    /**
     * 根据课程编号查询课程名称
     */
    public String getCourseName(String id) {
        return courses.get(id);
    }

    /**
     * 根据day查询当天的最受欢迎的Top5课程
     */
    public List<VideoAccessTopN> query(String day) {

        List<VideoAccessTopN> list = new ArrayList<VideoAccessTopN>();

        Connection connection = null;
        PreparedStatement ptsmt = null;
        ResultSet rs = null;

        try {
            connection = MySQLUtils.getConnection();
            String sql = "select cms_id, times from day_video_access_topn_stat where day=? order by times desc limit 5";
            ptsmt = connection.prepareStatement(sql);
            ptsmt.setString(1, day);

            rs = ptsmt.executeQuery();

            VideoAccessTopN domain = null;
            while (rs.next()) {
                domain = new VideoAccessTopN();
                // TODO... 在页面上应该是显示客户名称，而此时拿到的是课程编号
                // 如何根据课程编号去获取课程名称呢？
                // 编号和名称是有一个对应关系的，一般是存放在关系型数据库
                domain.setName(getCourseName(rs.getLong("cms_id") + ""));
                domain.setValue(rs.getLong("times"));

                list.add(domain);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MySQLUtils.release(connection, ptsmt, rs);
        }
        return list;
    }

    public static void main(String[] args) {
        VideoAccessTopNDAO dao = new VideoAccessTopNDAO();
        List<VideoAccessTopN> list = dao.query("20170511");
        for (VideoAccessTopN result : list) {
            System.out.println(result.getName() + ", " + result.getValue());
        }
    }
}
