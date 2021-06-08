package commonUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader implements IConst{
	public static Properties prop;
	//public static void PropertyReader() {
	static {
		try {
			prop = new Properties();
			FileInputStream fileInput = new FileInputStream(System.getProperty("user.dir") + CONFIG_PATH);
			prop.load(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String  getProperty(String key) throws Exception {
		if(prop.getProperty(key)==null) {
			throw new Exception("Property"+key+"not found in env.properties");
			
		}else
		return prop.getProperty(key);
		
	}
	

}
