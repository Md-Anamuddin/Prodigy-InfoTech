import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemperatureConverter extends JFrame implements ActionListener {
    private JTextField inputField;
    private JComboBox<String> unitComboBox;
    private JTextArea resultArea;
    private JButton convertButton;

    public TemperatureConverter() {
        setTitle("Temperature Converter");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(30, 30, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 10, 10);

        JLabel title = new JLabel("Temperature Converter");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

        JLabel inputLabel = new JLabel("Enter Temperature:");
        inputLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(inputLabel, gbc);

        inputField = new JTextField(10);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        add(inputField, gbc);

        JLabel unitLabel = new JLabel("Select Unit:");
        unitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        unitLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(unitLabel, gbc);

        String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
        unitComboBox = new JComboBox<>(units);
        unitComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        add(unitComboBox, gbc);

        convertButton = new JButton("Convert");
        convertButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        convertButton.setBackground(new Color(70, 130, 180));
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(false);
        convertButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        convertButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(convertButton, gbc);

        resultArea = new JTextArea(5, 25);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(245, 245, 245));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputText = inputField.getText();
        try {
            double temp = Double.parseDouble(inputText);
            String unit = (String) unitComboBox.getSelectedItem();
            resultArea.setText("");

            switch (unit) {
                case "Celsius":
                    resultArea.append("Fahrenheit: " + String.format("%.2f", (temp * 9 / 5) + 32) + "°F\n");
                    resultArea.append("Kelvin    : " + String.format("%.2f", temp + 273.15) + "K");
                    break;
                case "Fahrenheit":
                    resultArea.append("Celsius   : " + String.format("%.2f", (temp - 32) * 5 / 9) + "°C\n");
                    resultArea.append("Kelvin    : " + String.format("%.2f", (temp - 32) * 5 / 9 + 273.15) + "K");
                    break;
                case "Kelvin":
                    resultArea.append("Celsius   : " + String.format("%.2f", temp - 273.15) + "°C\n");
                    resultArea.append("Fahrenheit: " + String.format("%.2f", (temp - 273.15) * 9 / 5 + 32) + "°F");
                    break;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❗ Please enter a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Use Nimbus Look and Feel if available for modern UI
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(TemperatureConverter::new);
    }
}
