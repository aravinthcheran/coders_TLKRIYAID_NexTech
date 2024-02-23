import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.math.BigDecimal;

public class MainFrame extends JFrame {
    private BigDecimal totalCost;
    private MenuReader menuRead;
    private JPanel receipt;
    private JPanel centerPanel;
    private JTextField orderPrice;
    private ArrayList<MenuItem> itemsOrdered;
    private JTextPane orderItems;
    private String itemInformation;

    public MainFrame(File givenMenu) throws FileNotFoundException {
        totalCost = new BigDecimal(0);
        itemInformation = "";

        itemsOrdered = new ArrayList<MenuItem>();
        menuRead = new MenuReader(givenMenu);
        menuRead.readInputFile();
        create();

        setSize(1500, 1500);
        setTitle("Canteen Management System");
        setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void create() {
        JPanel mainPanel = (JPanel) getContentPane();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getItemButtons(), getReceipt());

        splitPane.setDividerLocation(780);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(splitPane, BorderLayout.CENTER);
    }

    private JScrollPane getItemButtons() {
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(0, 2));

        ArrayList<MenuItem> itemButtons = menuRead.getMenuItems();
        for (final MenuItem itemButton : itemButtons) {

            final JButton createButton = new JButton(itemButton.getName());
            createButton.setToolTipText(itemButton.getName());

            createButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    refreshPanel(itemButton);
                }
            });
            pan.add(createButton);
            createButton.setPreferredSize(new Dimension(30, 60));

        }
        JScrollPane scroller = new JScrollPane(pan);
        Border etchedBorder = BorderFactory.createEtchedBorder();
        Border border = BorderFactory.createTitledBorder(etchedBorder, "Items", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, new Font("Lucida", Font.BOLD, 20), Color.BLACK);
        pan.setBorder(border);
        return scroller;

    }

    private JPanel getReceipt() {
        receipt = new JPanel();
        JLabel label = new JLabel("Customer Order:");
        receipt.setLayout(new BorderLayout());

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());

        receipt.add(lowerPanel, BorderLayout.SOUTH);
        receipt.add(label, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1));

        orderItems = new JTextPane();
        centerPanel.add(orderItems);

        orderItems.setEditable(false);

        JScrollPane centerPanelScroller = new JScrollPane(centerPanel);
        receipt.add(centerPanelScroller, BorderLayout.CENTER);

        orderPrice = new JTextField(20);
        orderPrice.setText("Total Cost = $0.00");
        orderPrice.setEditable(false);

        JButton placeOrder = new JButton("Place Order");
        JButton clearOrder = new JButton("Clear Order");

        placeOrder.setPreferredSize(new Dimension(30, 50));
        clearOrder.setPreferredSize(new Dimension(30, 50));

        centerPanel.setBackground(Color.LIGHT_GRAY);
        placeOrder.setForeground(Color.BLACK);
        clearOrder.setForeground(Color.BLACK);

        placeOrder.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        clearOrder.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        clearOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                delete();

            }

        });

        placeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!orderPrice.getText().equals("Total Cost = $0.00")) {
                        menuRead.logOrder(itemsOrdered, totalCost);
                        JOptionPane.showMessageDialog(getContentPane(), "Order has been sent to kitchen",
                                "Order has been logged", JOptionPane.INFORMATION_MESSAGE);
                        delete();
                    } else {
                        JOptionPane.showMessageDialog(null, "No items ordered", "Place order",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException g) {

                    JOptionPane.showMessageDialog(null, "Error! Program terminated", " Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        lowerPanel.add(orderPrice, BorderLayout.NORTH);
        lowerPanel.add(placeOrder, BorderLayout.CENTER);
        lowerPanel.add(clearOrder, BorderLayout.SOUTH);
        lowerPanel.setBackground(Color.LIGHT_GRAY);
        receipt.setBackground(Color.WHITE);
        return receipt;
    }

    private void delete() {
        orderPrice.setText("Total Cost = $0.00");
        totalCost = new BigDecimal(0);
        itemsOrdered.clear();
        itemInformation = "";
        orderItems.setText(null);

    }

    private void refreshPanel(final MenuItem itemButton) {
        String item = itemButton.getName();
        BigDecimal itemPrice = itemButton.getCost();
        itemInformation +=  item + ": $" + itemPrice + "\n";
        orderItems.setText(itemInformation);
        itemsOrdered.add(itemButton);

        totalCost = totalCost.add(itemPrice);
        orderPrice.setText("Total cost = $" + totalCost);
    }
}
