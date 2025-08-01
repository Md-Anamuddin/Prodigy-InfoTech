import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ContactManager extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField nameField, phoneField, emailField;

    private final String FILE_NAME = "contacts.txt";

    public ContactManager() {
        setTitle("📇 Contact Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"Name", "Phone", "Email"}, 0);
        table = new JTable(model);

        loadContactsFromFile();

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        inputPanel.add(new JLabel("Name"));
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(new JLabel("Email"));
        inputPanel.add(new JLabel()); // empty

        inputPanel.add(nameField);
        inputPanel.add(phoneField);
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> addContact());
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveContactsToFile();
            }
        });

        setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "⚠️ Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.addRow(new String[]{name, phone, email});
        clearFields();
    }

    private void editContact() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "⚠️ Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            model.setValueAt(name, row, 0);
            model.setValueAt(phone, row, 1);
            model.setValueAt(email, row, 2);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a contact to edit", "ℹ️ Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteContact() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            model.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Select a contact to delete", "ℹ️ Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.println(model.getValueAt(i, 0) + "," +
                               model.getValueAt(i, 1) + "," +
                               model.getValueAt(i, 2));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving contacts!", "❌ Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadContactsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3)
                    model.addRow(parts);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading contacts!", "❌ Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactManager::new);
    }
}
