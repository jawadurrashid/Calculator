package jawadur.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static jawadur.calculator.R.id.operation;

public class MainActivity extends AppCompatActivity {

    // Initializing widgets

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // Variables to hold operands and type of calculations

    private Double operand1 = null;          //Null to indicate that the variables do not have a value yet
    private Double operand2 = null;
    private String pendingOperation = "=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Associating fields with reference to widgets with in layout through id

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(operation);

        // Initializing and associating calculator buttons with reference to layout

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);


        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);

        //Creating a specific onClickListener for numerical buttons (operands will have their own onClickListener)

        View.OnClickListener listener = new View.OnClickListener() {   //"listener" holds reference to a new OnClick instance
            @Override
            //OnClick method will read caption of button and then append it to the newNumber Edit Text widget
            public void onClick(View v) {                 //When button is tapped and the Android framework calls the onClick method, it passes in a reference to a button that was tapped
                Button b = (Button) v;             //Any widget (view) that is tapped can be passed as a parameter of OnClick as an instance of its base class, view.
                newNumber.append(b.getText().toString());  //Not all views can have texts, thus before the getText method can be called we must cast it to a widget that can use the getText method (Button)
            }
        };


        /*Storing the button fields into an array and using a loop to iterate through the loop and set the OnClickListener for each button

        Button[] buttonList = {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonDot};

        for (int i = 0; i < buttonList.length ; i++)

            buttonList[i].setOnClickListener(listener);
        }*/

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        //Creating onClickListener for operands

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;   //Casts view to a button
                String op = b.getText().toString(); //Obtains text read from button clicked and converts it into a string (stored in String op)
                String value = newNumber.getText().toString(); // Reads number from the Edit Text portion and stores it into the String variable called value

                if (value.length() != 0) {      //There may be a scenario where user clicks on operation button before a number, therefore we must make a condition for when the length of the value string is not zero
                    performOperation(value, op);
                }

                pendingOperation = op;                //Storing text from "op" into a variable
                displayOperation.setText(pendingOperation); //Setting the text into the "operations" widgets for display


            }
        };

        /*Button[] operandButtonList = {buttonEquals, buttonDivide, buttonPlus, buttonMinus, buttonMultiply};

        for (int x = 0; x < operandButtonList.length; x++){
            operandButtonList[x].setOnClickListener(opListener);
        }*/

        buttonMultiply.setOnClickListener(opListener);
        buttonEquals.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);


    }

    private void performOperation(String value, String operation) {
        if (null == operand1) {    // The valueOf method returns the relevant Number Object holding the value of the argument passed.
            operand1 = Double.valueOf(value);
        } else {
            operand2 = Double.valueOf(value);

        if (pendingOperation.equals("=")) {
            pendingOperation = operation;
        }

        switch (pendingOperation) {

            case "=":
                operand1 = operand2;
                break;

            case "/":
                if (operand2 == 0) {
                    operand1 = 0.0;
                } else {
                    operand1 /= operand2;
                }
                break;

            case "*":
                operand1 *= operand2;
                break;

            case "+":
                operand1 += operand2;
                break;

            case "-":
                operand1 -= operand2;
                break;
        }
    }
    result.setText(operand1.toString());
        newNumber.setText("");

 }
}

