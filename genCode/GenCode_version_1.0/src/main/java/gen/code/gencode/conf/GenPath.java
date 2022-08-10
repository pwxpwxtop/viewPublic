package gen.code.gencode.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



/**
 * @author pwx
 * @version 1.0
 * @createDate: 2022年08月01
 * @comment
 */
@Configuration
public class GenPath {

    @Value("${gen.path}")
    private String path;

}
