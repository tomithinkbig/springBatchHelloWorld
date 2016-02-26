package com.thinkbiganalytics.sandbox.springbatch.helloworld.util;

import org.apache.avro.Schema;
import org.apache.commons.collections.OrderedMap;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ts186040 on 2/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={TestConfig.class})
public class SimpleAvroFileParserTest {


    @Test
    public void parseSchemaInClassPath() throws IOException {

        LinkedHashMap<String,String> colToHiveTypeMap = new LinkedHashMap<String,String>();

        try (InputStream in =  new BufferedInputStream(this.getClass().getResourceAsStream("/_schema_hdd_3850_head.avsc")) ) {
            Schema.Parser parser = new Schema.Parser();
            Schema schema = parser.parse(in);
            System.out.println("schema: \n"+schema+"\n schema.getType() \n"+schema.getType());

            if (schema.getType() == Schema.Type.RECORD) {
                for (Schema.Field field : schema.getFields()) {
                    System.out.println("field: + "+ field);
                    String hiveType = null;
                    if (field.schema().getType() == Schema.Type.UNION) {
                        final List<Schema> fieldSchemas = field.schema().getTypes();
//                        System.out.println("  field union types: "+ fieldSchemas);
//                        for (Schema fieldSchema : fieldSchemas) {
//                            System.out.println("     fieldSchema: "+fieldSchema+"\n fieldSchema.getType() \n"+fieldSchema.getType());
//                        }
                        // expecting list with one or two elements, if there are two, one should be "null"

                        boolean isNullable = false;
                        Schema fieldSchema = null;

                        if ( fieldSchemas.size() == 1 && fieldSchemas.get(0).getType() != Schema.Type.NULL) {
                            isNullable = false;
                            fieldSchema = fieldSchemas.get(0);
                        } else if ( fieldSchemas.size() == 2) {
                            if ( fieldSchemas.get(0).getType() != Schema.Type.NULL) {
                                isNullable = true;
                                fieldSchema = fieldSchemas.get(0);
                            } else if ( fieldSchemas.get(1).getType() != Schema.Type.NULL) {
                                isNullable = true;
                                fieldSchema = fieldSchemas.get(1);
                            }
                        }

                        // Hive does not support NOT NULL constraint
                        if ( fieldSchema != null ) {
                            // TODO add complete mapping
                            if (fieldSchema.getType() == Schema.Type.STRING ) {
                                hiveType = "STRING";
                            } else if ( fieldSchema.getType() ==  Schema.Type.INT ) {
                                hiveType = "INT";
                            }
                        }
                    }
                    colToHiveTypeMap.put(field.name(),hiveType);
                }
            }
        }
        System.out.println("colToHiveTypeMap  :n"+colToHiveTypeMap);
    }
}
