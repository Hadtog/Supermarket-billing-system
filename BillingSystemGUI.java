package PROJECT;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BillingSystemGUI {

    private JFrame frame;
    private JTextArea cartArea;
    private JLabel totalLabel;
    private Cart cart;
    private ArrayList<Product> productList;
    private final String accountFileName = "accounts.txt";

    // Panels for product categories
    private JPanel foodBeveragesPanel, snacksPanel, fruitsVegetablesPanel, householdItemsPanel,
            frozenGoodsPanel, dairyProductsPanel, beveragesPanel, spicesCondimentsPanel;

    public BillingSystemGUI() {
        cart = new Cart();
        productList = new ArrayList<>();
        populateProductList();
    }

    private void populateProductList() {
        productList.add(new Product("Rice (1 kg)", 50.00));
        productList.add(new Product("Rice (1 Sack)", 1050.00));
        productList.add(new Product("Bread (loaf)", 60.00));
        productList.add(new Product("Eggs (1 dozen)", 100.00));
        productList.add(new Product("Chicken (1 kg)", 180.00));
        productList.add(new Product("Pork (1 kg)", 250.00));
        productList.add(new Product("Cooking Oil (1 liter)", 150.00));
        productList.add(new Product("Milk (1 liter)", 90.00));
        productList.add(new Product("Coffee (100 g)", 120.00));
        productList.add(new Product("Sugar (1 kg)", 55.00));
        productList.add(new Product("Soft Drinks (1.5 liters)", 70.00));
        productList.add(new Product("Potato Chips (Large Pack)", 65.00));
        productList.add(new Product("Biscuits (Pack)", 50.00));
        productList.add(new Product("Chocolate Bar (1 piece)", 80.00));
        productList.add(new Product("Instant Noodles (Pack)", 20.00));
        productList.add(new Product("Bananas (1 kg)", 60.00));
        productList.add(new Product("Apples (1 kg)", 150.00));
        productList.add(new Product("Carrots (1 kg)", 80.00));
        productList.add(new Product("Tomatoes (1 kg)", 70.00));
        productList.add(new Product("Onions (1 kg)", 120.00));
        productList.add(new Product("Garlic (1 kg)", 180.00));
        productList.add(new Product("Laundry Detergent (1 kg)", 180.00));
        productList.add(new Product("Dishwashing Liquid (500 ml)", 90.00));
        productList.add(new Product("Toilet Paper (4 rolls)", 100.00));
        productList.add(new Product("Shampoo (200 ml)", 120.00));
        productList.add(new Product("Bath Soap (Pack of 3)", 75.00));
        productList.add(new Product("Hotdogs (1 kg)", 160.00));
        productList.add(new Product("Frozen Fish (1 kg)", 200.00));
        productList.add(new Product("Ice Cream (1 liter)", 250.00));
        productList.add(new Product("Cheese (200 g)", 110.00));
        productList.add(new Product("Yogurt (100 g)", 40.00));
        productList.add(new Product("Bottled Water (1 liter)", 20.00));
        productList.add(new Product("Juice (1 liter)", 95.00));
        productList.add(new Product("Beer (Can)", 60.00));
        productList.add(new Product("Salt (1 kg)", 25.00));
        productList.add(new Product("Soy Sauce (500 ml)", 40.00));
        productList.add(new Product("Vinegar (500 ml)", 45.00));
        productList.add(new Product("Black Pepper (50 g)", 35.00));
    }

    public void createAndShowGUI() {
        frame = new JFrame("Supermarket Billing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        categorizeProducts();
        JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        addCategoryPanels(categoryPanel);

        JScrollPane categoryScrollPane = new JScrollPane(categoryPanel);
        categoryScrollPane.setPreferredSize(new Dimension(400, 500));
        categoryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        cartArea = new JTextArea();
        cartArea.setEditable(false);
        cartArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(cartArea);

        totalLabel = new JLabel("Total: ₱0.0", JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Arial", Font.BOLD, 16));
        buyButton.setBackground(new Color(40, 167, 69));
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.setBorder(BorderFactory.createLineBorder(new Color(40, 167, 69), 1));
        buyButton.addActionListener(new BuyButtonListener());

        mainPanel.add(new JLabel("Select Product:"), BorderLayout.NORTH);
        mainPanel.add(categoryScrollPane, BorderLayout.WEST);
        frame.getContentPane().add(mainPanel, BorderLayout.WEST);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(totalLabel, BorderLayout.NORTH);
        southPanel.add(buyButton, BorderLayout.SOUTH);
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void categorizeProducts() {
        foodBeveragesPanel = createCategoryPanel("Food & Beverages");
        snacksPanel = createCategoryPanel("Snacks");
        fruitsVegetablesPanel = createCategoryPanel("Fruits & Vegetables");
        householdItemsPanel = createCategoryPanel("Household Items");
        frozenGoodsPanel = createCategoryPanel("Frozen Goods");
        dairyProductsPanel = createCategoryPanel("Dairy Products");
        beveragesPanel = createCategoryPanel("Beverages");
        spicesCondimentsPanel = createCategoryPanel("Spices & Condiments");

        for (Product product : productList) {
            JButton productButton = new JButton(product.getName() + " - ₱" + product.getPrice());
            styleButton(productButton);
            productButton.addActionListener(new ProductButtonListener(product));

            if (product.getName().matches("Rice.*|Bread.*|Eggs.*|Chicken.*|Pork.*|Cooking Oil.*|Milk.*|Coffee.*|Sugar.*|Soft Drinks.*")) {
                foodBeveragesPanel.add(productButton);
            } else if (product.getName().matches("Potato Chips.*|Biscuits.*|Chocolate Bar.*|Instant Noodles.*")) {
                snacksPanel.add(productButton);
            } else if (product.getName().matches("Bananas.*|Apples.*|Carrots.*|Tomatoes.*|Onions.*|Garlic.*")) {
                fruitsVegetablesPanel.add(productButton);
            } else if (product.getName().matches("Laundry Detergent.*|Dishwashing Liquid.*|Toilet Paper.*|Shampoo.*|Bath Soap.*")) {
                householdItemsPanel.add(productButton);
            } else if (product.getName().matches("Hotdogs.*|Frozen Fish.*|Ice Cream.*")) {
                frozenGoodsPanel.add(productButton);
            } else if (product.getName().matches("Cheese.*|Yogurt.*")) {
                dairyProductsPanel.add(productButton);
            } else if (product.getName().matches("Bottled Water.*|Juice.*|Beer.*")) {
                beveragesPanel.add(productButton);
            } else if (product.getName().matches("Salt.*|Soy Sauce.*|Vinegar.*|Black Pepper.*")) {
                spicesCondimentsPanel.add(productButton);
            }
        }
    }

    private JPanel createCategoryPanel(String title) {
        JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        categoryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title,
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
        return categoryPanel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 123, 255));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 1));
    }
 
    private void addCategoryPanels(JPanel categoryPanel) {
        categoryPanel.add(foodBeveragesPanel);
        categoryPanel.add(snacksPanel);
        categoryPanel.add(fruitsVegetablesPanel);
        categoryPanel.add(householdItemsPanel);
        categoryPanel.add(frozenGoodsPanel);
        categoryPanel.add(dairyProductsPanel);
        categoryPanel.add(beveragesPanel);
        categoryPanel.add(spicesCondimentsPanel);
    }

    private class ProductButtonListener implements ActionListener {
        private final Product product;

        public ProductButtonListener(Product product) {
            this.product = product;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cart.addProduct(product);
            updateCartArea();
        }
    }

    private class BuyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.getTotal() == 0) {
                JOptionPane.showMessageDialog(frame, "Cart is empty.");
                return;
            }

            PaymentDialog paymentDialog = new PaymentDialog(frame);
            paymentDialog.setVisible(true);

            if (paymentDialog.isConfirmed()) {
                generateReceipt();
                cart.clearCart();
                updateCartArea();
            }
        }
    }

    private void updateCartArea() {
        cartArea.setText(cart.getCartDetails());
        totalLabel.setText("Total: ₱" + String.format("%.2f", cart.getTotal()));
    }
    
    private void generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date()); // Added date and time for better accuracy and legitimacy

        //Format too short if taas na ang mga products mag overlap
        receipt.append("════════════════════════════════════════\n");
        receipt.append("          CC20 SUPERMARKET         \n");
        receipt.append("   Faber Bldng, Xavier University  \n");
        receipt.append("         Contact: 123-456-7890      \n");
        receipt.append("════════════════════════════════════════\n");

        receipt.append("   Date & Time: ").append(dateTime).append("\n");
        receipt.append("════════════════════════════════════════\n");

        receipt.append(String.format("%-20s %10s\n", "Item Name", "Price"));
        receipt.append("-------------------------------------\n");
        receipt.append("\n");
        
        for (Map.Entry<Product, Integer> entry : cart.getProductMap().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double totalPrice = product.getPrice() * quantity;

            receipt.append(String.format("%-20s %10s\n",
                 "[" +  quantity + "x] " + product.getName(), //change format ga overlap sa resibo.txt
                    "₱" + String.format("%.2f", totalPrice)));
        }
        receipt.append("\n");
        receipt.append("-------------------------------------\n");
        receipt.append(String.format("%-20s %10s\n", "Total Amount:", "₱" + cart.getTotal()));
        receipt.append("════════════════════════════════════════\n");

        receipt.append("   THANK YOU FOR SHOPPING WITH US!   \n");
        receipt.append("          Please Visit Again         \n");
        receipt.append("-------------------------------------\n");
        receipt.append(" Software by: Cltchmens      \n");
        receipt.append(" Contact: Hey@my.xu.edu.com    \n");
        receipt.append("════════════════════════════════════════\n");

        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setMargin(new Insets(10, 10, 10, 10));
        receiptArea.setBackground(Color.WHITE);
        receiptArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JDialog receiptDialog = new JDialog(frame, "Receipt", true);
        receiptDialog.setLayout(new BorderLayout());
        receiptDialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        closeButton.setFocusPainted(false);
        closeButton.setBackground(new Color(230, 230, 250));
        closeButton.addActionListener(e -> receiptDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        receiptDialog.add(buttonPanel, BorderLayout.SOUTH);

        receiptDialog.pack();
        receiptDialog.setLocationRelativeTo(frame);
        receiptDialog.setVisible(true);

        // Write receipt to file
        writeReceiptToFile(receipt.toString());
    }
//File handling (Back Up copy for every recipt)
    private void writeReceiptToFile(String receipt) {
        String userHome = System.getProperty("user.home");
        File downloadDir = new File(userHome, "Downloads");
        File receiptFile = new File(downloadDir, "Resibo.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(receiptFile, true))) {
            writer.write(receipt);
            writer.newLine();
            writer.newLine(); // Add space between receipts

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing to Resibo.txt file: " + e.getMessage());// pwede ra ni walaon soon
        }
    }

//progress 3 finishing touches and finalization {ADDING BANK ACCOUNTS}
private class PaymentDialog extends JDialog {
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryDateField;
    private JTextField cvvField;
    private boolean confirmed = false;

    public PaymentDialog(Frame owner) {
        super(owner, "Make Payment", true);
        setLayout(new GridLayout(0, 2, 5, 5));
        setSize(350, 250);
        setLocationRelativeTo(owner);

        add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        add(cardNumberField);

        add(new JLabel("Card Holder Name:"));
        cardHolderField = new JTextField();
        add(cardHolderField);

        add(new JLabel("Expiry Date (MM/YYYY):"));
        expiryDateField = new JTextField();
        add(expiryDateField);

        add(new JLabel("CVV:"));
        cvvField = new JTextField();
        add(cvvField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            String cardNumber = cardNumberField.getText().trim();
            String cardHolder = cardHolderField.getText().trim();
            String expiryDate = expiryDateField.getText().trim();
            String cvv = cvvField.getText().trim();

            // Ensure all fields are filled
            if (cardNumber.isEmpty() || cardHolder.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all the blanks.");
                return;
            }

            // Ensure card number is 16 digits
            if (!cardNumber.matches("\\d{16}")) {
                JOptionPane.showMessageDialog(this, "Invalid card number.");
                return;
            }

            // Validate expiry date format (MM/YYYY)
            if (!expiryDate.matches("(0[1-9]|1[0-2])/20\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Invalid expiry date.");
                return;
            }

            // Validate CVV format (3 digits)
            if (!cvv.matches("\\d{3}")) {
                JOptionPane.showMessageDialog(this, "Invalid CVV, Please enter a valid one.");
                return;
            }
            // Ensure CVV is between 900 and 999
            int cvvValue = Integer.parseInt(cvv); // Convert CVV to an integer
            if (cvvValue < 900 || cvvValue > 999) {
                JOptionPane.showMessageDialog(this, "Invalid CVV, Please enter a valid one.");
                 return;
            }

            // Parse total amount from the cart
            double totalAmount = cart.getTotal();

            // Validate payment details and balance
            if (validatePaymentDetails(cardNumber, cardHolder, expiryDate, cvv, totalAmount)) {
                JOptionPane.showMessageDialog(this, "Payment Approved. Now proceeding to checkout.");
                confirmed = true;
                dispose();
            } else {
                // Error messages are shown in `validatePaymentDetails` method
                return;
            }
        });
        add(confirmButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));
        add(cancelButton);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}

private boolean validatePaymentDetails(String cardNumber, String cardHolder, String expiryDate, String cvv, double totalAmount) {
    String userHome = System.getProperty("user.home");
    File downloadDir = new File(userHome, "Downloads");
    File bankAccountsFile = new File(downloadDir, accountFileName);

    try (BufferedReader reader = new BufferedReader(new FileReader(bankAccountsFile))) {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] accountDetails = line.split(",");
            if (accountDetails.length < 5) {
                continue; // Skip malformed lines
            }

            String fileCardNumber = accountDetails[0].trim();
            String fileCardHolder = accountDetails[1].trim();
            String fileExpiryDate = accountDetails[2].trim();
            String fileCvv = accountDetails[3].trim();
            double fileBalance = Double.parseDouble(accountDetails[4].trim());

            // Validate the card details
            if (fileCardNumber.equals(cardNumber) &&
                fileCardHolder.equalsIgnoreCase(cardHolder) &&
                fileExpiryDate.equals(expiryDate) &&
                fileCvv.equals(cvv)) {

                // Check if balance is sufficient
                if (fileBalance >= totalAmount) {
                    updateBalance(bankAccountsFile, cardNumber, fileBalance - totalAmount);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                    return false;
                }
            }
        }
        JOptionPane.showMessageDialog(frame, "Account not found.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(frame, "Error reading bank accounts file: " + e.getMessage());
    }
    return false;
}

private void updateBalance(File bankAccountsFile, String cardNumber, double newBalance) {
    List<String> lines = new ArrayList<>();
    boolean updated = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(bankAccountsFile))) {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] accountDetails = line.split(",");
            if (accountDetails.length < 5) {
                lines.add(line); 
                continue;
            }

            if (accountDetails[0].trim().equals(cardNumber)) {
                accountDetails[4] = String.format("%.2f", newBalance); 
                line = String.join(", ", accountDetails); 
                updated = true;
            }
            lines.add(line);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(frame, "Error reading bank accounts file: " + e.getMessage());
        return;
    }

    
    if (updated) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bankAccountsFile))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error updating balance in file: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Account not found for card number: " + cardNumber);
    }
}
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BillingSystemGUI gui = new BillingSystemGUI();
            gui.createAndShowGUI();
        });
    }
}
