package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox)findViewById(R.id.whipped_cream);
        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate);
        EditText nameBox = (EditText) findViewById(R.id.name);
        String name =  nameBox.getText().toString();
        boolean addWhippedCream = whippedCream.isChecked();
        boolean addChocolate = chocolate.isChecked();
        int price = calculatePrice(addWhippedCream,addChocolate);
     //   Log.i("customer name", "Name" + customerName);
        String priceMessage = createOrderSummary(price,addWhippedCream,addChocolate,name);
        String subject = getString(R.string.order_summary_email_subject) + " "  + name;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * creates order summary
     * @param name is the name of the customer
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether the user wants chocolate
     * @ text summary
     */
    private String createOrderSummary (int price, boolean addWhippedCream, boolean addChocolate, String name){
        String priceMessage =  getResources().getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_total, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * Calculates the price of the order.
     * return total price
     * @param whippedCream is whether the customer selects whipped cream or not
     * @param chocolate is whether the customer wants chocolate or not
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate ) {
        int basePrice = 5;

        if (whippedCream){
            basePrice += 1;
        }
        if (chocolate){
            basePrice += 2;
        }
        return quantity * basePrice;

    }

    /**
     * method to increment quantity
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * method to decrement quantity
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(this, "You cannot order less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }


    }