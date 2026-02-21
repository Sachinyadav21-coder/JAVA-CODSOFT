import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CurrencyConverterPro {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> history = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   CURRENCY CONVERTER (FREE API)");
        System.out.println("=================================");

        while (true) {

            System.out.println("\n1. Convert Currency");
            System.out.println("2. View Supported Currencies");
            System.out.println("3. View Conversion History");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    convertCurrency();
                    break;
                case "2":
                    listCurrencies();
                    break;
                case "3":
                    showHistory();
                    break;
                case "4":
                    System.out.println("Thank you for using the converter!");
                    return;
                default:
                    System.out.println("Invalid choice. Enter 1-4 only.");
            }
        }
    }

    private static void convertCurrency() {
        try {
            System.out.print("Enter Base Currency (e.g., USD): ");
            String base = scanner.nextLine().toUpperCase().trim();

            System.out.print("Enter Target Currency (e.g., INR): ");
            String target = scanner.nextLine().toUpperCase().trim();

            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            Map<String, Double> rates = fetchRates(base);

            if (!rates.containsKey(target)) {
                System.out.println("Invalid target currency.");
                return;
            }

            double rate = rates.get(target);
            double converted = amount * rate;

            Currency currency = Currency.getInstance(target);
            String symbol = currency.getSymbol();

            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);

            String result = formatter.format(amount) + " " + base +
                    " = " + symbol + formatter.format(converted) + " " + target;

            System.out.println("\n--- Conversion Result ---");
            System.out.println(result);
            System.out.println("Exchange Rate: 1 " + base + " = " + rate + " " + target);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            history.add("[" + timestamp + "] " + result);

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Enter numbers only.");
        } catch (Exception e) {
            System.out.println("Error fetching data. Check internet connection.");
        }
    }

    private static void listCurrencies() {
        try {
            System.out.print("Enter Base Currency (e.g., USD): ");
            String base = scanner.nextLine().toUpperCase().trim();

            Map<String, Double> rates = fetchRates(base);

            System.out.println("\n--- Supported Currencies ---");
            rates.keySet().stream().sorted().forEach(code -> System.out.print(code + "  "));
            System.out.println();

        } catch (Exception e) {
            System.out.println("Unable to fetch currencies.");
        }
    }

    private static void showHistory() {

        if (history.isEmpty()) {
            System.out.println("No conversions yet.");
            return;
        }

        System.out.println("\n--- Conversion History ---");
        history.forEach(System.out::println);
    }

    private static Map<String, Double> fetchRates(String base)
            throws IOException, InterruptedException {

        String url = "https://open.er-api.com/v6/latest/" + base;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        if (!body.contains("\"result\":\"success\"")) {
            throw new RuntimeException("Invalid base currency.");
        }

        Map<String, Double> rates = new HashMap<>();

        String ratesSection = body.split("\"rates\":\\{")[1];
        ratesSection = ratesSection.substring(0, ratesSection.indexOf("}"));

        String[] pairs = ratesSection.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.replace("\"", "").split(":");
            rates.put(keyValue[0], Double.parseDouble(keyValue[1]));
        }

        return rates;
    }
}
