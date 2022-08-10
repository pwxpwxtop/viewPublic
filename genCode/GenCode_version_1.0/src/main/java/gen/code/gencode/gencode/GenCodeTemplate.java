package gen.code.gencode.gencode;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author pwx
 * @version 1.0
 * @createDate: 2022年08月02
 * @comment
 */
public interface GenCodeTemplate {

    /**
     * 生成java controller 代码
     * @param jsonObject
     */
    void GenCodeJavaController(JSONObject jsonObject);

    /**
     * 生成java service 代码
     * @param jsonObject
     */
    void GenCodeJavaService(JSONObject jsonObject);

    /**
     * 生成java ServiceImpl 代码
     * @param jsonObject
     */
    void GenCodeJavaServiceImpl(JSONObject jsonObject);

    /**
     * 生成java mapper 代码
     * @param jsonObject
     */
    void GenCodeJavaMapper(JSONObject jsonObject);

    /**
     * 生成Model 实体类代码
     * @param jsonObject
     */
    void GenCodeJavaModel(JSONObject jsonObject);

    /**
     * 创建数据库表格 实体类代码
     * @param jsonObject
     */
    void GenCodeJavaCreateTable(JSONObject jsonObject);
}

