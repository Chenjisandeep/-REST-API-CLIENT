import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        scanner.close();

        String apiKey = "8635ae43a06146938fc91559251805";  // Replace with your WeatherAPI key
        String urlString = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;

        try {
            // Use URI to avoid deprecated URL constructor
            URI uri = new URI(urlString);
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            connection.disconnect();

            // Parse JSON using Gson
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject location = jsonObject.getAsJsonObject("location");
            JsonObject current = jsonObject.getAsJsonObject("current");
            JsonObject condition = current.getAsJsonObject("condition");

            String cityName = location.get("name").getAsString();
            double temperature = current.get("temp_c").getAsDouble();
            int humidity = current.get("humidity").getAsInt();
            double windKph = current.get("wind_kph").getAsDouble();
            String overcast = condition.get("text").getAsString();

            // Display weather info
            System.out.println("\nWeather Information for " + cityName + ":");
            System.out.println("Temperature: " + temperature + " °C");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Wind Speed: " + windKph + " kph");
            System.out.println("Condition: " + overcast);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
