package util;

import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class yamlReader {

    String yamlString;

    public yamlReader(String filePath) {
        try {
            yamlString = IOUtils.toString(Files.newInputStream(new File(filePath).toPath()));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("File not found");
        }
    }

    public yamlReader(InputStream is) {
        try{
            yamlString = IOUtils.toString(is);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("File not found");
        }

    }


    public String get(String nodePath){
        try {
            Object value = mapOfYamlString();
            for (String node : nodePath.split("\\.")) {
                value = ((Map) value).get(node);
            }
            return value.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getMap(String nodePath){
        try {
            Object value = mapOfYamlString();
            for (String node : nodePath.split("\\.")) {
                value = ((Map) value).get(node);
            }
            return (HashMap) value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, Object> mapOfYamlString(){
        return (Map<String,Object>)(new Yaml()).load(yamlString);
    }
}