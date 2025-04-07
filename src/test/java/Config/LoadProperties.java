package Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class LoadProperties {
    public static Properties propertyconfig;

    public static void LoadPropertiesFile() throws IOException
    {
        // Init
        propertyconfig = new Properties();
        // Filepath
        InputStream in = LoadProperties.class.getClassLoader().getResourceAsStream("Config.Properties");
        // Config file loaded in the properties object
        propertyconfig.load(in);
    }
}

