package CodeAlpha_ProjectName;
import java.util.*;

class Stock {
    String symbol;
    String name;
    double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): ₹%.2f", name, symbol, price);
    }
}

class Transaction {
    String type; // "BUY" or "SELL"
    Stock stock;
    int quantity;
    double totalAmount;
    Date date;

    public Transaction(String type, Stock stock, int quantity, double totalAmount) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return String.format("%s %d of %s at ₹%.2f on %s", 
                             type, quantity, stock.symbol, totalAmount, date.toString());
    }
}

class User {
    String name;
    double balance;
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void buyStock(Stock stock, int qty) {
        double cost = stock.price * qty;
        if (balance >= cost) {
            balance -= cost;
            holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + qty);
            transactions.add(new Transaction("BUY", stock, qty, cost));
            System.out.println("✅ Buy successful!");
        } else {
            System.out.println("❌ Not enough balance.");
        }
    }

    public void sellStock(Stock stock, int qty) {
        int owned = holdings.getOrDefault(stock.symbol, 0);
        if (owned >= qty) {
            double proceeds = stock.price * qty;
            balance += proceeds;
            holdings.put(stock.symbol, owned - qty);
            transactions.add(new Transaction("SELL", stock, qty, proceeds));
            System.out.println("✅ Sell successful!");
        } else {
            System.out.println("❌ Not enough shares.");
        }
    }

    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- 📊 Portfolio Summary ---");
        System.out.printf("💰 Balance: ₹%.2f\n", balance);
        double totalValue = balance;
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            Stock s = market.get(symbol);
            double value = s.price * qty;
            totalValue += value;
            System.out.printf("%s: %d shares @ ₹%.2f = ₹%.2f\n", symbol, qty, s.price, value);
        }
        System.out.printf("📈 Total Portfolio Value: ₹%.2f\n", totalValue);
    }

    public void viewTransactions() {
        System.out.println("\n--- 🧾 Transaction History ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}

public class StockTreakingPlatform {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, Stock> market = new HashMap<>();
    static User user;

    public static void main(String[] args) {
        setupMarket();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        user = new User(name, 100000); // ₹1,00,000 starting balance

        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> displayMarket();
                case 2 -> buyStock();
                case 3 -> sellStock();
                case 4 -> user.viewPortfolio(market);
                case 5 -> user.viewTransactions();
                case 6 -> System.out.println("🚪 Exiting...");
                default -> System.out.println("❌ Invalid choice!");
            }

        } while (choice != 6);
    }

    static void setupMarket() {
        market.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3900.00));
        market.put("INFY", new Stock("INFY", "Infosys", 1450.00));
        market.put("RELI", new Stock("RELI", "Reliance Industries", 2800.00));
        market.put("HDFC", new Stock("HDFC", "HDFC Bank", 1680.00));
        market.put("WIPRO", new Stock("WIPRO", "Wipro", 420.00));
    }

    static void showMenu() {
        System.out.println("\n--- 📈 Stock Trading Simulator ---");
        System.out.println("1. View Market Data");
        System.out.println("2. Buy Stocks");
        System.out.println("3. Sell Stocks");
        System.out.println("4. View Portfolio");
        System.out.println("5. View Transaction History");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    static void displayMarket() {
        System.out.println("\n--- 💹 Market Data ---");
        for (Stock s : market.values()) {
            randomizePrice(s);
            System.out.println(s);
        }
    }

    static void buyStock() {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.nextLine().toUpperCase();
        Stock stock = market.get(symbol);
        if (stock != null) {
            System.out.print("Enter quantity to buy: ");
            int qty = scanner.nextInt();
            user.buyStock(stock, qty);
        } else {
            System.out.println("❌ Invalid symbol.");
        }
    }

    static void sellStock() {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.nextLine().toUpperCase();
        Stock stock = market.get(symbol);
        if (stock != null) {
            System.out.print("Enter quantity to sell: ");
            int qty = scanner.nextInt();
            user.sellStock(stock, qty);
        } else {
            System.out.println("❌ Invalid symbol.");
        }
    }

    static void randomizePrice(Stock stock) {
        double change = (Math.random() - 0.5) * 50; // ±₹25 change
        stock.updatePrice(Math.max(10, stock.price + change));
    }
}
