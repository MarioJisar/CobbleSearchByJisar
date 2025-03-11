import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CsvToJson {
    public static void main(String[] args) {
        String inputFile = "pokemon_data.txt";  // Archivo de entrada con los datos
        String outputFile = "pokemon_data.json"; // Archivo de salida JSON

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));
            if (lines.isEmpty()) {
                System.out.println("El archivo está vacío.");
                return;
            }
            
            String[] headers = lines.get(0).split("\t"); // Suponiendo que las columnas están separadas por tabulaciones
            JSONArray jsonArray = new JSONArray();
            
            for (int i = 1; i < lines.size(); i++) {
                String[] values = lines.get(i).split("\t");
                JSONObject obj = new JSONObject();
                
                for (int j = 0; j < headers.length && j < values.length; j++) {
                    obj.put(headers[j], values[j]);
                }
                
                jsonArray.put(obj);
            }
            
            Files.write(Paths.get(outputFile), ((String) jsonArray.toString(4)).getBytes());
            System.out.println("Archivo JSON generado exitosamente: " + outputFile);
            
        } catch (IOException e) {
            System.out.println("Error al leer/escribir archivos: " + e.getMessage());
        }
    }

    private static class JSONArray {

        public JSONArray() {
        }

        private Object toString(int i) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void put(JSONObject obj) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private static class JSONObject {

        public JSONObject() {
        }

        private void put(String header, String value) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
