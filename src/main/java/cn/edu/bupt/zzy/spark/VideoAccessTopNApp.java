package cn.edu.bupt.zzy.spark;

import cn.edu.bupt.zzy.dao.VideoAccessTopNDAO;
import cn.edu.bupt.zzy.domain.VideoAccessTopN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @description: 最受欢迎的TopN课程
 * @author: zzy
 * @date: 2019/6/1
 **/
@RestController
public class VideoAccessTopNApp {

    @Autowired
    private VideoAccessTopNDAO videoAccessTopNDAO;

    @RequestMapping(value = "/video_access_topn_view", method = RequestMethod.GET)
    public ModelAndView videoAccessTopN() {
        return new ModelAndView("topn");
    }

    @RequestMapping(value = "/video_access_topn", method = RequestMethod.GET)
    @ResponseBody
    public List<VideoAccessTopN> videoAccessTopN(
            @RequestParam String day) {
        return videoAccessTopNDAO.query(day);
    }
}
