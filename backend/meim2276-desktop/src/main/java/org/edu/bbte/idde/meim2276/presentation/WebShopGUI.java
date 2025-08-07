package org.edu.bbte.idde.meim2276.presentation;

import edu.bbte.idde.meim2276.business.interfaces.OrderServiceInterface;
import edu.bbte.idde.meim2276.dao.datatypes.Order;
import edu.bbte.idde.meim2276.dao.factories.OrderServiceFactory;
import edu.bbte.idde.meim2276.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WebShopGUI extends JFrame {

    private final transient OrderServiceInterface orderService;
    private transient JTable orderTable;
    private final transient String[] columnNames;
    private final transient JPanel tablePanel;
    private transient JButton deleteButton;
    private static final Logger log = LoggerFactory.getLogger(WebShopGUI.class);

    public WebShopGUI() {
        super();
        columnNames = new String[]{"ID", "BuyerId", "DateOfOrder", "DateOfDelivery", "Status", "Total"};

        orderService = new OrderServiceFactory().createOrderService();
        orderTable = new JTable();
        tablePanel = new JPanel(new BorderLayout());
        try {
            loadTable(orderService.getAllOrders(), columnNames);
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            log.error(String.valueOf(e));
        }
        init();
    }


    private void init() {
        setTitle("Custom WebShop");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(orderTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        deleteButton = new JButton("Delete Order");
        deleteButton.setEnabled(false);

        inputPanel.add(new JLabel("Buyer ID:"));
        JTextField buyerIdField = new JTextField();
        inputPanel.add(buyerIdField);
        inputPanel.add(new JLabel("Date of Order:"));
        JTextField dateOfOrderField = new JTextField();
        inputPanel.add(dateOfOrderField);
        inputPanel.add(new JLabel("Date Of Delivery:"));
        JTextField dateOfDeliveryField = new JTextField();
        inputPanel.add(dateOfDeliveryField);
        inputPanel.add(new JLabel("Status:"));
        JTextField statusField = new JTextField();
        inputPanel.add(statusField);
        inputPanel.add(new JLabel("Total:"));
        JTextField totalField = new JTextField();
        inputPanel.add(totalField);
        JButton addButton = new JButton("Add Order");
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int buyerId = Integer.parseInt(buyerIdField.getText());
                    String dateOfOrder = dateOfOrderField.getText();
                    String dateOfDelivery = dateOfDeliveryField.getText();
                    String status = statusField.getText();
                    double total = Double.parseDouble(totalField.getText());
                    orderService.addOrder(new Order(0, buyerId, dateOfOrder, dateOfDelivery, status, total));

                    updateTable(orderService.getAllOrders(), columnNames);
                } catch (NumberFormatException | BadArgumentException | DatabaseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    log.error(String.valueOf(ex));
                }

            }
        });

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            deleteButton.setEnabled(true);
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] selectedRows = orderTable.getSelectedRows();
                    for (int selectedRow : selectedRows) {
                        int id = (int) orderTable.getValueAt(selectedRow, 0);
                        orderService.deleteOrder(id);
                    }
                    updateTable(orderService.getAllOrders(), columnNames);
                    deleteButton.setEnabled(false);
                } catch (BadArgumentException | OrderNotFoundException | DatabaseException
                         | BadConnectionException ex) {
                    JOptionPane.showMessageDialog(WebShopGUI.this, ex.getMessage());
                    log.error(String.valueOf(ex));
                }
            }
        });

        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadTable(List<Order> data, String... columnNames) {
        Object[][] tableData = new Object[data.size()][columnNames.length];
        int i = 0;
        for (Object[] row : tableData) {
            row[0] = data.get(i).getId();
            row[1] = data.get(i).getBuyerId();
            row[2] = data.get(i).getDateOfOrder();
            row[3] = data.get(i).getDateOfDelivery();
            row[4] = data.get(i).getStatus();
            row[5] = data.get(i).getTotal();
            i++;
        }
        orderTable = new NonEditableIdTable(tableData, columnNames);

        orderTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column != 0) {
                    log.info("Row: " + row + " Column: " + column);
                    try {
                        Order newOrder = buildOrderFromSelectedRow(row);
                        orderService.updateOrder(newOrder);
                        updateTable(orderService.getAllOrders(), columnNames);
                    } catch (BadArgumentException | OrderNotFoundException | DatabaseException
                             | BadConnectionException ex) {
                        JOptionPane.showMessageDialog(WebShopGUI.this, ex.getMessage());
                        log.error(String.valueOf(ex));
                    }
                }
            }
        });
    }

    private static class NonEditableIdTable extends JTable {
        public NonEditableIdTable(Object[][] tableData, String... columnNames) {
            super(tableData, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0;
        }
    }

    private void updateTable(List<Order> data, String... columnNames) {
        loadTable(data, columnNames);
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            deleteButton.setEnabled(true);
        });

        tablePanel.removeAll();
        tablePanel.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private Order buildOrderFromSelectedRow(int selectedRow) {
        int id = Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString());
        int buyerId = Integer.parseInt(orderTable.getValueAt(selectedRow, 1).toString());
        String dateOfOrder = orderTable.getValueAt(selectedRow, 2).toString();
        String dateOfDelivery = orderTable.getValueAt(selectedRow, 3).toString();
        String status = orderTable.getValueAt(selectedRow, 4).toString();
        double total = Double.parseDouble(orderTable.getValueAt(selectedRow, 5).toString());

        return new Order(id, buyerId, dateOfOrder, dateOfDelivery, status, total);
    }

}
