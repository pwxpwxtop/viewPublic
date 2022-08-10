package gen.code.gencode.gencode.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import gen.code.gencode.gencode.GenCodeService;
import gen.code.gencode.gencode.GenCodeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pwx
 * @version 1.0
 * @createDate: 2022年08月01
 * @comment
 */
@Service
public class GenCodeServiceImpl implements GenCodeService {

    @Autowired
    private GenCodeTemplate genCodeTemplate;

    @Override
    public void genCode(String table) {
        JSONObject jsonObject = JSON.parseObject(table);
        genCodeTemplate.GenCodeJavaController(jsonObject);
        genCodeTemplate.GenCodeJavaService(jsonObject);
        genCodeTemplate.GenCodeJavaServiceImpl(jsonObject);
        genCodeTemplate.GenCodeJavaMapper(jsonObject);
        genCodeTemplate.GenCodeJavaModel(jsonObject);
        genCodeTemplate.GenCodeJavaCreateTable(jsonObject);
        System.out.println(jsonObject);
    }
}
