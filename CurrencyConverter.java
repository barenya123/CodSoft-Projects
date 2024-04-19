import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Currency Converter!");
        System.out.println("Please select the base currency (e.g., USD, EUR, GBP,INR,DINAR,DIRHAM,YEN):");
        String baseCurrency = sc.nextLine().toUpperCase();

        System.out.println("Please select the target currency (e.g., USD, EUR, GBP,INR,DINAR,DIRHAM,YEN):");
        String targetCurrency = sc.nextLine().toUpperCase();

        System.out.println("Please enter the amount you want to convert:");
        double amount = sc.nextDouble();

        try {
            double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
            double convertedAmount = amount * exchangeRate;
            System.out.println(amount + " " + baseCurrency + " is equivalent to " +
                    convertedAmount + " " + targetCurrency);
        } catch (IOException e) {
            System.out.println("Error fetching exchange rate: " + e.getMessage());
        }
    }

    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException {
        String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        String jsonResponse = response.toString();
        double exchangeRate = Double.parseDouble(jsonResponse.substring(
                jsonResponse.indexOf(targetCurrency) + 5, jsonResponse.indexOf(",", jsonResponse.indexOf(targetCurrency))));

        connection.disconnect();
        return exchangeRate;
    }
}
