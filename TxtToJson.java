import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TxtToJson {
    public static void main(String[] args) {
        String inputFilePath = "pokemon_data.txt";
        String outputFilePath = "data.json";
        
        List<String> jsonLines = new ArrayList<>();
        
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] values = line.split("\t");
                if (values.length < 18) continue;
                
                String jsonObject = String.format(
                    "{\"No\":\"%s\", \"Pokemon\":\"%s\", \"Entry\":\"%s\", \"Bucket\":\"%s\", " +
                    "\"Weight\":\"%s\", \"LvMin\":\"%s\", \"LvMax\":\"%s\", \"Biomes\":\"%s\", " +
                    "\"ExcludedBiomes\":\"%s\", \"Time\":\"%s\", \"Weather\":\"%s\", \"Multipliers\":\"%s\", " +
                    "\"Context\":\"%s\", \"Presets\":\"%s\", \"Conditions\":\"%s\", \"Anticonditions\":\"%s\", " +
                    "\"skyLightMin\":\"%s\", \"skyLightMax\":\"%s\"}",
                    values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7],
                    values[8], values[9], values[10], values[11], values[12], values[13], values[14],
                    values[15], values[16], values[17]
                );
                
                jsonLines.add(jsonObject);
            }
            
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write("[\n" + String.join(",\n", jsonLines) + "\n]");
            }
            
            System.out.println("Archivo JSON creado exitosamente: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}