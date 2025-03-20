import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TxtToJson {
    public static void main(String[] args) {
        String inputFilePath = "data.txt";
        String outputFilePath = "data2.json";
        
        List<String> jsonLines = new ArrayList<>();
        
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] values = line.split("\t", -1); // El -1 conserva los campos vacíos al final
                if (values.length < 19) {
                    System.out.println("Línea ignorada por falta de columnas: " + line);
                    continue;
                }
                
                // Convertimos biomas a formato de array JSON si hay comas
                String biomes = values[7].contains(",") ? "[\"" + values[7].replace(", ", "\", \"") + "\"]" : "\"" + values[7] + "\"";
                
                String jsonObject = String.format(
                    "{\"No\":\"%s\", \"Pokemon\":\"%s\", \"Entry\":\"%s\", \"Bucket\":\"%s\", " +
                    "\"Weight\":\"%s\", \"LvMin\":\"%s\", \"LvMax\":\"%s\", \"Biomes\":%s, " +
                    "\"ExcludedBiomes\":\"%s\", \"Time\":\"%s\", \"Weather\":\"%s\", \"Multipliers\":\"%s\", " +
                    "\"Context\":\"%s\", \"Presets\":\"%s\", \"Conditions\":\"%s\", \"Anticonditions\":\"%s\", " +
                    "\"skyLightMin\":\"%s\", \"skyLightMax\":\"%s\", \"canSeeSky\":\"%s\", \"Implemented\":\"yes\"}",
                    values[0], values[1], values[2], values[3], values[4], values[5], values[6], biomes,
                    values[8], values[9], values[10], values[11], values[12], values[13], values[14],
                    values[15], values[16], values[17], values[18]
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
