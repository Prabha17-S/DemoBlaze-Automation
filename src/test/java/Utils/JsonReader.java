package Utils;

import Models.CredsType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonReader {
    // Reads JSON file and converts it into a list of `CredsType` objects (Params: Filepath to the json)
    public static List<CredsType> main(String filepath) {
        ObjectMapper objectMapper = new ObjectMapper();
        //Creates an instance of ObjectMapper, which is used to read JSON files and convert them into Java objects.

        try {
            // Read the JSON file as a list of User objects
            List<CredsType> users = objectMapper.readValue(JsonReader.class.getClassLoader().getResourceAsStream(filepath), objectMapper.getTypeFactory().constructCollectionType(List.class, CredsType.class));
            return users; // Return the lists of user creds.
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
