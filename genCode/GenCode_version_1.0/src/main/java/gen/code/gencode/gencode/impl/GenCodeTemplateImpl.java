package gen.code.gencode.gencode.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import gen.code.gencode.gencode.GenCodeTemplate;
import org.springframework.stereotype.Service;
import top.generate.gens.analysis.factory.Generates;

/**
 * @author pwx
 * @version 1.0
 * @createDate: 2022年08月02
 * @comment
 */
@Service
public class GenCodeTemplateImpl implements GenCodeTemplate {


    @Override
    public void GenCodeJavaController(JSONObject jsonObject) {
        addProperties(jsonObject, "Controller.java");
        Generates generates = new Generates("controller", jsonObject);
        generates.generateFile();
    }

    @Override
    public void GenCodeJavaService(JSONObject jsonObject) {
        addProperties(jsonObject, "Service.java");
        Generates generates = new Generates("service", jsonObject);
        generates.generateFile();
    }

    @Override
    public void GenCodeJavaServiceImpl(JSONObject jsonObject) {
        addProperties(jsonObject, "ServiceImpl.java");
        Generates generates = new Generates("serviceImpl", jsonObject);
        generates.generateFile();
    }

    @Override
    public void GenCodeJavaMapper(JSONObject jsonObject) {
        addProperties(jsonObject, "Mapper.java");
        Generates generates = new Generates("mapper", jsonObject);
        generates.generateFile();
    }

    @Override
    public void GenCodeJavaModel(JSONObject jsonObject) {
        addProperties(jsonObject, ".java");
        JSONArray array = jsonObject.getJSONArray("field");//获取field字段
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String name = object.getString("name");
            sb.append("\n\t//").append(object.get("label"));
            JSONArray animation = object.getJSONArray("animation");//实体类添加注解
            String animationName = "";
            if (animation != null){
                for (int i1 = 0; i1 < animation.size(); i1++) {
                    animationName += animation.getString(i1);
                }
                switch (animationName) {
                    case "@TableIdUUID":
                        sb.append("\n\t@TableId(type = IdType.ASSIGN_UUID)");
                        break;
                    case "@TableIdSNOWFLAKES":
                        sb.append("\n\t@TableId(type = IdType.ASSIGN_ID)");
                        break;
                    case "@TableIdAUTO":
                        sb.append("\n\t@TableId(type = IdType.AUTO)");
                        break;
                    case "@TableIdINPUT":
                        sb.append("\n\t@TableId(type = IdType.INPUT)");
                        break;
                    case "@TableFieldINSERT":
                        sb.append("\n\t@TableField(fill = FieldFill.INSERT)");
                        break;
                    case "@TableFieldUPDATE":
                        sb.append("\n\t@TableField(fill = FieldFill.UPDATE)");
                        break;
                    case "@TableFieldINSERT_UPDATE":
                        sb.append("\n\t@TableField(fill = FieldFill.INSERT_UPDATE)");
                        break;
                    case "@TableLogic":
                        sb.append("\n\t@TableLogic");
                        break;
                }
            }

            String type = object.getString("type");//实体类添加注解
            if ("int".equals(type)){
                sb.append("\n\tprivate").append(" Integer ").append(name).append(";\n");
            }else if ("datetime".equals(type)){
                sb.append("\n\tprivate").append("  Date ").append(name).append(";\n");
            }else {
                sb.append("\n\tprivate").append(" String ").append(name).append(";\n");
            }


        }
        jsonObject.put("myModels", sb);
        Generates generates = new Generates("myModel", jsonObject);
        generates.generateFile();
    }

    @Override
    public void GenCodeJavaCreateTable(JSONObject jsonObject) {
        String name = jsonObject.getString("table");//获取table
        name = name.substring(0, 1).toUpperCase() + name.substring(1);//获取name
        jsonObject.put("MODEL", name);//model名称

        JSONArray array = jsonObject.getJSONArray("field");//获取field字段
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            sb.append("\t\t`").append(object.getString("name")).append("`");
            sb.append(" ").append(object.getString("type"));
            if (object.getString("null") != null){
                sb.append(" ").append(object.getString("null"));
            }

            if (object.getString("constraint") != null){//约束类型判断
                sb.append(" ").append(object.getString("constraint"));
            }

            if (i == array.size() - 1){//标签备注字段
                sb.append(" comment '").append(object.getString("label")).append("'");
            }else {
                sb.append(" comment '").append(object.getString("label")).append("' , \n");
            }

        }
        jsonObject.put("SQL", sb.toString());//将拼接好的sql进行封装
        jsonObject.put("NAME", "createTable.txt");
        //创建生成类
        Generates generates = new Generates("createTables", jsonObject);
        generates.generateFile();
    }


    /**
     * 需要添加的属性
     * @param jsonObject
     * @param next
     */
    private void addProperties(JSONObject jsonObject, String next){
        String name = jsonObject.getString("table");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        jsonObject.put("MODEL", name);//设置文件model名
        jsonObject.put("NAME", name + next);//设置文件名
    }





}
