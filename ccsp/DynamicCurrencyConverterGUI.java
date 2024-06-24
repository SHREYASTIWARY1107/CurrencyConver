package ccsp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DynamicCurrencyConverterGUI extends JFrame {
    private Map<String, Double> exchangeRates;
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;
    private JButton convertButton;
    private JButton swapButton;

    public DynamicCurrencyConverterGUI() {
        initializeExchangeRates();
        setupUI();
    }

    private void initializeExchangeRates() {
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.93);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("JPY", 157.00);
        exchangeRates.put("AUD", 1.51);
        exchangeRates.put("CAD", 1.35);
        exchangeRates.put("CHF", 0.88);
        exchangeRates.put("CNY", 7.26);
        exchangeRates.put("INR", 84.00);
    }

    private void setupUI() {
        setTitle("Dynamic Currency Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel titleLabel = new JLabel("Currency Converter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, gbc);

        // From Currency
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("From:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        fromCurrency = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        mainPanel.add(fromCurrency, gbc);

        // To Currency
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("To:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        toCurrency = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        mainPanel.add(toCurrency, gbc);

        // Swap Button
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        swapButton = new JButton("⇅");
        swapButton.addActionListener(e -> swapCurrencies());
        mainPanel.add(swapButton, gbc);

        // Amount
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        mainPanel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        amountField = new JTextField(10);
        mainPanel.add(amountField, gbc);

        // Convert Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> convertCurrency());
        mainPanel.add(convertButton, gbc);

        // Result Label
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        resultLabel = new JLabel("Converted Amount: ");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(resultLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Style components
        styleComponents();
    }

    private void styleComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font comboBoxFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(labelFont);
            } else if (comp instanceof JComboBox) {
                ((JComboBox<?>) comp).setFont(comboBoxFont);
            } else if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setFont(buttonFont);
                button.setBackground(new Color(66, 134, 244));
                button.setForeground(Color.WHITE);
                button.setFocusPainted(false);
            } else if (comp instanceof JTextField) {
                ((JTextField) comp).setFont(comboBoxFont);
            }
        }

        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void convertCurrency() {
        try {
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());

            if (from.equals(to)) {
                resultLabel.setText("Please select different currencies.");
                return;
            }

            double rateFrom = exchangeRates.get(from);
            double rateTo = exchangeRates.get(to);
            double convertedAmount = (amount / rateFrom) * rateTo;

            DecimalFormat df = new DecimalFormat("#,##0.00");
            resultLabel.setText(String.format("%s %s = %s %s", 
                df.format(amount), from, df.format(convertedAmount), to));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid amount entered.");
        }
    }

    private void swapCurrencies() {
        int fromIndex = fromCurrency.getSelectedIndex();
        int toIndex = toCurrency.getSelectedIndex();
        fromCurrency.setSelectedIndex(toIndex);
        toCurrency.setSelectedIndex(fromIndex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DynamicCurrencyConverterGUI().setVisible(true);
        });
    }
}