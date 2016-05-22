/*
 * Student Name: Hamzah Shafeeq.
 * Assignment: Final project.

 * About the software:
 This is a software that allowed the users to login with their name name and ids to rigister and make and order
 pizza.Also you can add some side orders and drinks to the cart before you print your recipt.
 */
package pizzaproject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author Hamzah Shafeeq
 */
public class FXMLDocumentController implements Initializable {

    //-----------stages------------//
    @FXML
    private void showStage1() {
        PizzaProject.showStage1();
    }

    @FXML
    private void showStage2() {
        PizzaProject.showStage2();
    }

    @FXML
    private void showStage3() {
        PizzaProject.showStage3();
    }

    //------------login-------------//
    @FXML
    private TextField name, id;
    @FXML
    private Label lblMessage;
    private static String emplpyee = "";

    //create a file and add some user names and ids to it.
    public File createFile() {
        File file = new File("logins.dat");
        try {
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter br = new BufferedWriter(fw);
                br.write("1111 Jonathan");
                br.newLine();
                br.write("2222 Hamza");
                br.close();
            }
        } catch (IOException e) {
        }
        return file;
    }

    //read ffrom a file and check wether it matches the username and if text feilds.
    @FXML
    public boolean checkUser(String id, String name) {
        File file = createFile();
        HashMap<String, String> log = new HashMap();
        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null) {
                String[] sp = s.split(" ");
                log.put(sp[0], sp[1]);
            }
            br.close();
        } catch (IOException e) {
        }

        boolean access = false;
        if (log.containsKey(id)) {
            String user = log.get(id);
            if (user.equals(name)) {
                emplpyee = user;
                access = true;
            }
        }
        return access;
    }

    //checks if everything is okay, hide the first stage and show the second one.
    @FXML
    public void btnLogin(ActionEvent event) {
        boolean successfulLognin = checkUser(id.getText(), name.getText());
        if (successfulLognin) {
            showStage2();
        } else {
            lblMessage.setText("unsuccessful login");
        }
    }

    //------------customer info------------//
    @FXML
    private TextField txtFirstName, txtLastName, txtPhoneNum, txtAddress, txtEmail;
    @FXML
    private RadioButton optPickup, optDelivery;
    @FXML
    private Label lblErrorFristName, lblErrorLastName, lblErrorAddress, lblErrorPhoneNum, lblErrorCustomerInfo;

    private static String sFirstName = "";
    private static String sLastName = "";
    private static String sPhoneNum = "";
    private static String sAddress = "";
    private static String sEmail = "";

    //method checks each field in the cusomer omformation.
    @FXML
    public boolean checking(RadioButton optPickup, RadioButton optDelivery, String firstName,
            String lastName, String address, String phoneNum, String email) {
        boolean move = true;
        if (optPickup.isSelected() || optDelivery.isSelected()) {
            if (firstName.isEmpty()) {
                move = false;
                lblErrorFristName.setText("first name can not be empty");
            } else {
                sFirstName = firstName;
            }
            if (lastName.isEmpty()) {
                move = false;
                lblErrorLastName.setText("last name can not be empty");
            } else {
                sLastName = lastName;
            }
            if (optDelivery.isSelected()) {
                if (address.isEmpty()) {
                    move = false;
                    lblErrorAddress.setText("address can not be empty");
                } else {
                    sAddress = address;
                }
            }
            if (!email.isEmpty()) {
                sEmail = email;
            }
        }
        Pattern pattern = Pattern.compile("\\d{3}-? ?\\d{3}-? ?\\d{4}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            move = false;
            lblErrorPhoneNum.setText("wronge format");
        } else {
            sPhoneNum = phoneNum;
        }
        return move;
    }

    //checks which feilds are rquired.
    public void rquired(ActionEvent e) {
        if (optPickup.isSelected()) {
            lblErrorFristName.setText("*");
            lblErrorLastName.setText("*");
            lblErrorPhoneNum.setText("*");
            lblErrorAddress.setText("");
        } else {
            lblErrorFristName.setText("*");
            lblErrorLastName.setText("*");
            lblErrorPhoneNum.setText("*");
            lblErrorAddress.setText("*");
        }
    }

    //checks if everything is okay, hides the second stage and show the third one.
    @FXML
    public void submit(ActionEvent event) {
        if (optPickup.isSelected() || optDelivery.isSelected()) {
            lblErrorCustomerInfo.setText("");
            if (!(txtFirstName.getText()).isEmpty()) {
                lblErrorFristName.setText("");
            }
            if (!(txtLastName.getText()).isEmpty()) {
                lblErrorLastName.setText("");
            }
            if (!(txtAddress.getText()).isEmpty()) {
                lblErrorAddress.setText("");
            }
            Pattern pattern = Pattern.compile("\\d{3}-? ?\\d{3}-? ?\\d{4}");
            Matcher matcher = pattern.matcher(txtPhoneNum.getText());
            if (matcher.matches()) {
                lblErrorPhoneNum.setText("");
            }
            if (checking(optPickup, optDelivery, txtFirstName.getText(), txtLastName.getText(),
                    txtAddress.getText(), txtPhoneNum.getText(), txtEmail.getText()) && (optPickup.isSelected() || optDelivery.isSelected())) {
                //System.out.println("yess");
                lblErrorFristName.setText("");
                lblErrorLastName.setText(null);
                lblErrorAddress.setText(null);
                lblErrorPhoneNum.setText(null);

                showStage3();
            }
        } else {
            lblErrorCustomerInfo.setText("<-- Please select one");
        }

    }

    //-----------common methods-------//
    //pass it a text field and returns a integer number.
    public int getInt(String txtField) {
        boolean aNumber = false;
        int numberOfPizza = 0;
        if (!txtField.isEmpty()) {
            for (int i = 0; i < txtField.length(); i++) {
                if (!Character.isDigit(txtField.charAt(i))) {
                    aNumber = false;
                } else {
                    aNumber = true;
                }
            }
        }
        if (aNumber) {
            numberOfPizza = Integer.parseInt(txtField);
        }
        return numberOfPizza;
    }

    //checks weather a text field is empty and checks if it has something other than a digit.
    public boolean valdation(String txtField) {
        boolean aNumber = false;
        if (!txtField.isEmpty()) {
            for (int i = 0; i < txtField.length(); i++) {
                if (Character.isDigit(txtField.charAt(i))) {
                    aNumber = true;
                }
            }
        }
        return aNumber;
    }

    //checks weather a text field is empty or not.
    public boolean empty(TextField txt) {
        String check = txt.getText();
        boolean empty = true;
        if (!check.isEmpty()) {
            empty = false;
        }
        return empty;
    }
    //-----------pizza-tab------------//
    @FXML
    private RadioButton optSmall, optMedium, optLarge, optXLarge;
    @FXML
    private RadioButton optPlain, optWholeWheat, optStuffedCrust;
    @FXML
    private CheckBox chkRight, chkLeft, chkCenter;
    @FXML
    private CheckBox chkPepperoni, chkMushroom, chkBacon, chkOnion, chkTomatoes,
            chkPineapple, chkGreenPepper, chkExtraCheese, chkOlives, chkArugula;
    @FXML
    private Label lblErrorPizza, lblPizzaOrdered, lblSideNotice;
    @FXML
    private Label lblRightSide, lblLeftSide, lblCenter;

    //pizza size prices:
    private final int SMALL_COST = 8;
    private final int MED_COST = 10;
    private final int LARGE_COST = 12;
    private final int X_LARGE_COST = 14;
    //toppings price:
    private final int ONE_TOPPING = 1;

    private ArrayList pizzaList = new ArrayList();

    //returns the cost of the selected pizza.
    public int intPizzaOPT() {
        int sizePrice = 0;
        if (optSmall.isSelected()) {
            sizePrice = SMALL_COST;
        } else if (optMedium.isSelected()) {
            sizePrice = MED_COST;
        } else if (optLarge.isSelected()) {
            sizePrice = LARGE_COST;
        } else if (optXLarge.isSelected()) {
            sizePrice = X_LARGE_COST;
        }
        return sizePrice;
    }

    //returns the size of the selected pizza.
    public String SPizzaOPT() {
        String sizeSize = "";
        if (optSmall.isSelected()) {
            sizeSize = "small";
        } else if (optMedium.isSelected()) {
            sizeSize = "medium";
        } else if (optLarge.isSelected()) {
            sizeSize = "large";
        } else if (optXLarge.isSelected()) {
            sizeSize = "x-large";
        }
        return sizeSize;
    }

    //return the crust type.
    public String crustOPT() {
        String crustType = "";
        if (optPlain.isSelected()) {
            crustType = "plain";
        } else if (optWholeWheat.isSelected()) {
            crustType = "whole-wheat";
        } else if (optStuffedCrust.isSelected()) {
            crustType = "Stuffed crust";
        }
        return crustType;
    }

    //checks what are the selected toppings.
    public ArrayList toppingsCHK() {
        ArrayList<String> toppings = new ArrayList();

        if (chkPepperoni.isSelected()) {
            toppings.add("pepperoni");
        }
        if (chkMushroom.isSelected()) {
            toppings.add("Mushroom");
        }
        if (chkBacon.isSelected()) {
            toppings.add("Bacon");
        }
        if (chkOnion.isSelected()) {
            toppings.add("Onion");
        }
        if (chkTomatoes.isSelected()) {
            toppings.add("Tomatoes");
        }
        if (chkPineapple.isSelected()) {
            toppings.add("Pineapple");
        }
        if (chkGreenPepper.isSelected()) {
            toppings.add("Green Pepper");
        }
        if (chkExtraCheese.isSelected()) {
            toppings.add("ExtraCheese");
        }
        if (chkOlives.isSelected()) {
            toppings.add("Olives");
        }
        if (chkArugula.isSelected()) {
            toppings.add("Arugula");
        }

        return toppings;
    }

    //checks what is on each side.
    public String[] whatOnSides() {
        String[] sides = new String[3];
        if (!lblRightSide.getText().isEmpty()) {
            sides[0] = lblRightSide.getText();
        } else {
            sides[0] = "-";
        }
        if (!lblLeftSide.getText().isEmpty()) {
            sides[1] = lblLeftSide.getText();
        } else {
            sides[1] = "-";
        }
        if (!lblCenter.getText().isEmpty()) {
            sides[2] = lblCenter.getText();
        } else {
            sides[2] = "-";
        }
        return sides;
    }

    public void sideNotice(ActionEvent event) {
        lblSideNotice.setText("Please select the side after you finish");
    }

    //moves the seleceted toppings to the right side.
    public void rightSide(ActionEvent event) {
        lblRightSide.setText("");
        lblErrorPizza.setText("");
        lblSideNotice.setText("");
        ArrayList right = toppingsCHK();
        String sRight = "";
        for (int i = 0; i < right.size(); i++) {
            sRight += right.get(i);
            sRight += " ";
        }
        lblRightSide.setText(sRight);
        resetToppings();

    }

    //moves the seleceted toppings to the left side.
    public void leftSide(ActionEvent event) {
        lblErrorPizza.setText("");
        lblLeftSide.setText("");
        lblSideNotice.setText("");
        ArrayList left = toppingsCHK();
        String sLeft = "";
        for (int i = 0; i < left.size(); i++) {
            sLeft += left.get(i);
            sLeft += " ";
        }
        lblLeftSide.setText(sLeft);
        resetToppings();
    }

    //checks the toppings on the center.
    public void center(ActionEvent event) {
        lblErrorPizza.setText("");
        lblCenter.setText("");
        lblSideNotice.setText("");
        ArrayList center = toppingsCHK();
        String sCenter = "";
        for (int i = 0; i < center.size(); i++) {
            sCenter += center.get(i);
            sCenter += " ";
        }
        lblCenter.setText(sCenter);
    }

    //resets all the check boxes of the toppings section.
    public void resetToppings() {
        chkPepperoni.setSelected(false);
        chkMushroom.setSelected(false);
        chkBacon.setSelected(false);
        chkOnion.setSelected(false);
        chkTomatoes.setSelected(false);
        chkPineapple.setSelected(false);
        chkGreenPepper.setSelected(false);
        chkExtraCheese.setSelected(false);
        chkOlives.setSelected(false);
        chkArugula.setSelected(false);
    }

    //resets all the check boxes and labels of the toppings section.
    public void reset(ActionEvent e) {
        chkLeft.setSelected(false);
        chkRight.setSelected(false);
        chkCenter.setSelected(false);
        lblRightSide.setText("");
        lblLeftSide.setText("");
        lblCenter.setText("");
        lblSideNotice.setText("");
        resetToppings();
    }

    //resets everything in the pizza tap and remove all the added pizza.
    public void refresh(ActionEvent e) {
        reset(e);
        pizzaList.clear();
        lblPizzaOrdered.setText("");
        lblErrorPizza.setText("");
        optSmall.setSelected(true);
        optPlain.setSelected(true);
    }

    private int pizzaCost = 0;
    private int toppingsCost = 0;

    //allows multiple orders.
    public void addPizza(ActionEvent e) {
        lblErrorPizza.setText("");
        if (!lblRightSide.getText().isEmpty() && !lblLeftSide.getText().isEmpty() || !lblCenter.getText().isEmpty()) {
            String[] sides = whatOnSides();
            String pizza = String.format("PIZZA SIZA:  %s%nPIZZA COST $%d%nCRUST:  %s%nTOPPINGS ON THE RIGHT SIDE:  %s.%nTOPPINGS ON THE LEFT SIDE:  %s.%nTOPPINGS ON THE CENTER:  %s.", SPizzaOPT(), intPizzaOPT(), crustOPT(), sides[0], sides[1], sides[2]);
            pizzaList.add(pizza);
            lblPizzaOrdered.setText(pizzaList.size() + " pizza added");
            pizzaCost += intPizzaOPT();
            toppingsCost += (toppingsCHK().size()) * ONE_TOPPING;
            reset(e);

        } else {
            if (chkLeft.isSelected() && !chkRight.isSelected()) {
                lblErrorPizza.setText("Please select the right side first");
            } else if (!chkLeft.isSelected() && chkRight.isSelected()) {
                lblErrorPizza.setText("Please select the left side first");
            } else {
                lblErrorPizza.setText("Please select toppings and side/sides");
            }

        }

    }

    //checks if everything is okay, it prints the recipt.
    @FXML
    public void finish(ActionEvent event) {
        lblErrorPizza.setText("");
        if (pizzaList.size() != 0) {
            lblErrorPizza.setText("");
            Recipt();
            lblErrorPizza.setText("Printing recipt...");
        } else {
            lblErrorPizza.setText("Please make your plizza/s and add it first");
        }
    }

    //-------------side-orders-tab--------------//
    @FXML
    private Label lblErrorSideOrder;
    @FXML
    private TextField txtWings, txtGarlic, txtBroccoli, txtFries, txtPotato;

    private final int WING_COST = 3;
    private final int garlic_COST = 1;
    private final int BROCCOLI_COST = 1;
    private final int FRIES_COST = 2;
    private final int POTATO_COST = 4;

    public int wings() {
        int wings = getInt(txtWings.getText());
        int wingsPrice = wings * WING_COST;
        return wingsPrice;
    }

    public int garlic() {
        int garlic = getInt(txtGarlic.getText());
        int garlicPrice = garlic * garlic_COST;
        return garlicPrice;
    }

    public int broccoli() {
        int broccoli = getInt(txtBroccoli.getText());
        int broccoliPrice = broccoli * BROCCOLI_COST;
        return broccoliPrice;
    }

    public int fries() {
        int fries = getInt(txtFries.getText());
        int friesPrice = fries * FRIES_COST;
        return friesPrice;
    }

    public int potato() {
        int potato = getInt(txtPotato.getText());
        int potatoPrice = potato * POTATO_COST;
        return potatoPrice;
    }

    @FXML
    public void finish2(ActionEvent event) {
        if (empty(txtWings) && empty(txtGarlic) && empty(txtBroccoli) && empty(txtFries) && empty(txtPotato)) {
            finish(event);
        } else {
            if (valdation(txtWings.getText()) || valdation(txtGarlic.getText())
                    || valdation(txtBroccoli.getText()) || valdation(txtFries.getText())
                    || valdation(txtPotato.getText())) {
                lblErrorSideOrder.setText("Printing recipt...");
                finish(event);
            } else {
                lblErrorSideOrder.setText("Please enter numbers only");
            }
        }
    }

    //-----------Drinks-----------//
    @FXML
    private TextField txtPepsi, txtCoca, txtCrush, txtMilkshake;
    @FXML
    private RadioButton btnPepsiCan, btnCocaCan, btnCrushCan;
    @FXML
    private Label lblErrorDrinks;

    private final int PEPSI_COCA_CRUSH_CAN_COST = 2;
    private final int PEPSI_COCA_CRUSH_BOTTEL_COST = 3;
    private final int MILKSHAKE_COST = 5;

    public int drinkCost(TextField txt, RadioButton btn) {
        int price = getInt(txt.getText());
        if (btn.isSelected()) {
            price = price * PEPSI_COCA_CRUSH_CAN_COST;
        } else {
            price = price * PEPSI_COCA_CRUSH_BOTTEL_COST;
        }
        return price;
    }

    public int milkshakeCost() {
        int price = getInt(txtMilkshake.getText());
        price = price * MILKSHAKE_COST;
        return price;
    }

    @FXML
    public void finish3(ActionEvent event) {
        if (empty(txtPepsi) && empty(txtCoca) && empty(txtCrush) && empty(txtMilkshake)) {
            finish2(event);
        } else {
            if (valdation(txtPepsi.getText()) || valdation(txtCoca.getText())
                    || valdation(txtCrush.getText()) || valdation(txtMilkshake.getText())) {
                lblErrorDrinks.setText("Printing recipt");
                finish2(event);
            } else {
                lblErrorDrinks.setText("Please enter numbers only");
            }
        }
    }

    //---------recipt----------//
    private int num = 0;

    public void Recipt() {
        try {
            File dir = new File("Receipts");
            if (!dir.exists()) {
                dir.mkdir();
            }
            num++;
            File file;
            String sNum;
            boolean again = false;
            do {
                sNum = String.format("%03d", num);
                file = new File(dir.getName() + "\\Order" + sNum + ".txt");
                if (file.exists()) {
                    num++;
                    again = true;
                } else {
                    again = false;
                }
            } while (again);
            file.createNewFile();
            if (num == 999) {
                num = 0;
            }

            FileWriter fr = new FileWriter(file.getAbsoluteFile());
            BufferedWriter br = new BufferedWriter(fr);
            br.write("WELCOME TO HAMZA'S PIZZA");
            br.newLine();
            br.newLine();
            br.newLine();
            br.write("ORDER NUMBER:  " + sNum);
            br.newLine();
            br.write("EMPLOYEE:  " + emplpyee);
            br.newLine();
            br.newLine();
            br.write("**CUSTOMER'S INFORMATION**");
            br.newLine();
            br.write("FIRST NAME:  " + sFirstName);
            br.newLine();
            br.write("LAST NAME:  " + sLastName);
            br.newLine();
            br.write("PHONE NUMBER:  " + sPhoneNum);
            br.newLine();

            if (!sAddress.isEmpty()) {
                br.write("ADDRESS:  " + sAddress);
                br.newLine();
            }
            if (!sEmail.isEmpty()) {
                br.write("EMAIL ADDRESS:  " + sEmail);
                br.newLine();
            }
            br.newLine();
            br.write("**PIZZA INFORMATION**");
            br.newLine();
            for (int i = 0; i < pizzaList.size(); i++) {
                br.write("PIZZA #" + (i + 1));
                br.newLine();
                br.write(pizzaList.get(i).toString());
                br.newLine();
                br.newLine();
                br.newLine();
            }
            //prices:
            int wings = 0;
            int garlic = 0;
            int broccoli = 0;
            int fries = 0;
            int potato = 0;
            if (valdation(txtWings.getText()) || valdation(txtGarlic.getText()) || valdation(txtBroccoli.getText())
                    || valdation(txtFries.getText()) || valdation(txtPotato.getText())) {
                br.write("**SIDE ORDER INFORMATION**");
                br.newLine();
            }

            if (valdation(txtWings.getText())) {
                wings = wings();
                br.write("Wings #" + txtWings.getText());
                br.newLine();
                br.write("wings $" + wings);
                br.newLine();
            }
            if (valdation(txtGarlic.getText())) {
                garlic = garlic();
                br.write("Garlic #" + txtGarlic.getText());
                br.newLine();
                br.write("Garlic $" + garlic);
                br.newLine();
            }
            if (valdation(txtBroccoli.getText())) {
                broccoli = broccoli();
                br.write("Broccoli #" + txtBroccoli.getText());
                br.newLine();
                br.write("Broccoli $" + broccoli);
                br.newLine();
            }
            if (valdation(txtFries.getText())) {
                fries = fries();
                br.write("Fries #" + txtFries.getText());
                br.newLine();
                br.write("Fries $" + fries);
                br.newLine();
            }
            if (valdation(txtPotato.getText())) {
                potato = potato();
                br.write("Potato #" + txtPotato.getText());
                br.newLine();
                br.write("Potato $" + potato);
                br.newLine();
            }

            //prices:
            int pepsi = 0;
            int coca = 0;
            int crush = 0;
            int milkshake = 0;
            if (valdation(txtPepsi.getText()) || valdation(txtCoca.getText())
                    || valdation(txtCrush.getText()) || valdation(txtMilkshake.getText())) {
                br.newLine();
                br.write("**DRINKS INFORMATION**");
                br.newLine();
            }

            if (valdation(txtPepsi.getText())) {
                pepsi = drinkCost(txtPepsi, btnPepsiCan);
                br.write("Pepesi #" + txtPepsi.getText());
                br.newLine();
                br.write("Pepesi $" + pepsi);
                br.newLine();
            }
            if (valdation(txtCoca.getText())) {
                coca = drinkCost(txtCoca, btnCocaCan);
                br.write("Coca #" + txtCoca.getText());
                br.newLine();
                br.write("Coca $" + coca);
                br.newLine();
            }
            if (valdation(txtCrush.getText())) {
                crush = drinkCost(txtCrush, btnCrushCan);
                br.write("Crush #" + txtCrush.getText());
                br.newLine();
                br.write("Crush $" + crush);
                br.newLine();
            }
            if (valdation(txtMilkshake.getText())) {
                milkshake = milkshakeCost();
                br.write("milkshake #" + txtMilkshake.getText());
                br.newLine();
                br.write("milkshake $" + milkshake);
                br.newLine();
            }

            int total = pizzaCost + toppingsCost + wings + garlic + broccoli
                    + fries + potato + pepsi + coca + crush + milkshake;
            br.newLine();
            br.newLine();
            br.write("**TOTAL INFORMATION**");
            br.newLine();
            br.write("Total before tax is $" + total);
            br.newLine();
            br.write("Tax is 13%");
            br.newLine();
            double tax = 0.13 * total;
            double totalWithTax = tax + total;
            String sTotalWithTax = String.format("%.2f", totalWithTax);
            br.write("Total after tax is $" + sTotalWithTax);
            br.newLine();
            br.newLine();
            Date date = new Date();
            br.write("Order was placed at: " + date.getHours() + ":" + date.getMinutes());
            br.newLine();
            date.setMinutes(date.getMinutes() + 30);
            br.write("Order will be ready at: " + date.getHours() + ":" + date.getMinutes());
            br.newLine();
            br.newLine();
            br.newLine();
            br.newLine();
            br.write("THANK YOU FOR BUYING FORM OUR STORE");
            br.newLine();
            br.write("OUR ADDRESS IS: 1035 Barclay Mississauga, ON, Canada O1K 5W3");
            br.newLine();
            br.write("OUT PHONE NUMBER IS: 555-555-5555");
            br.close();
        } catch (IOException e) {
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
