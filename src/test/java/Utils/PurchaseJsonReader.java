package Utils;

import Config.LoadProperties;
import Models.CredsType;
import Models.PurchaseFormType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PurchaseJsonReader {
    public static List<PurchaseFormType> main() {
        ObjectMapper objectMapper = new ObjectMapper();
        //Creates an instance of ObjectMapper, which is used to read JSON files and convert them into Java objects.

        try {
            // Read the JSON file as a list of User objects
            List<PurchaseFormType> orderForm = objectMapper.readValue(PurchaseJsonReader.class.getClassLoader().getResourceAsStream("PlaceOrder.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, PurchaseFormType.class));
            return orderForm;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
