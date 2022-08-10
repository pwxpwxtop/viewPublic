package gen.code.gencode.gencode;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author pwx
 * @version 1.0
 * @createDate: 2022年08月01
 * @comment
 */
@Controller
@ResponseBody
@RequestMapping("/GenCodeController")
public class GenCodeController {

    @Autowired
    private GenCodeService service;

    @RequestMapping("/genCode")
    public void genCode(String table){
        service.genCode(table);
    }

}
