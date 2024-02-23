import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.math.BigDecimal;

public class MenuReader {
    private Scanner readMenu;
    private BufferedWriter orderLogger;
    private FileWriter orderLoggerStream;
    private int count;
    private Calendar calendar;
    private ArrayList<MenuItem> menuItems;
    private File orders;

    public MenuReader(File inputMenu) throws FileNotFoundException {
        menuItems = new ArrayList<MenuItem>();
        readMenu = new Scanner(inputMenu);
        calendar = new GregorianCalendar();
        count = 1;
        inputMenu.getName();
    }

    public void readInputFile() {
        while (readMenu.hasNextLine()) {

            String item = readMenu.nextLine();
            String itemCost = readMenu.nextLine();
            BigDecimal itemPrice = new BigDecimal(itemCost);

            MenuItem createItem = new MenuItem(item, itemPrice);
            menuItems.add(createItem);
        }
        readMenu.close();
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void logOrder(ArrayList<MenuItem> itemsOrdered, BigDecimal orderPrice) throws IOException {
        orderLoggerStream = new FileWriter(getFileInstance(),true);
		orderLogger = new BufferedWriter(orderLoggerStream);

        java.util.Date orderDate = new java.util.Date();
        if (count == 1) {

            orderLogger.write("Date: " + calendar.get(Calendar.DAY_OF_MONTH)
                    + " " + calendar.get((Calendar.MONTH + 1)) + " " + calendar.get(Calendar.YEAR) + " " + "\n \n");

        }

        orderLogger.write("Order number: " + count + " -> ");
        count++;
        orderLogger.write(new Timestamp(orderDate.getTime()) + "\n");

        for (MenuItem item : itemsOrdered) {
            orderLogger.write(item.itemToString() + "\n");
        }

        orderLogger.write("Total Price is $" + orderPrice + "\n\n");
        orderLogger.close();
    }

    private File getFileInstance() {
		if (orders == null) {
            String fileName = "ItemOrders.txt";
			orders = new File(fileName);
		}
		return orders;
	}
}
