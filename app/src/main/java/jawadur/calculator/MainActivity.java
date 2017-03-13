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
    private EditText inputNumber;
    private TextView displayOperation;

    // Variables to hold operands and type of calculations

    private Double operand1 = null;          //Null to indicate that the variables do not have a value yet
    private String pendingOperation = "=";    //Keep scope of variables as narrow as possible
    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Associating fields with reference to widgets with in layout through id

        result = (EditText) findViewById(R.id.result);
        inputNumber = (EditText) findViewById(R.id.inputNumber);
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
        Button buttonNegative = (Button) findViewById(R.id.buttonNeg);

        //Creating a specific onClickListener for numerical buttons (operands will have their own onClickListener)

        View.OnClickListener listener = new View.OnClickListener() {   //"listener" holds reference to a new OnClick instance
            @Override
            //OnClick method will read caption of button and then append it to the inputNumber Edit Text widget
            public void onClick(View v) {                 //When button is tapped and the Android framework calls the onClick method, it passes in a reference to a button that was tapped
                Button b = (Button) v;             //Any widget (view) that is tapped can be passed as a parameter of OnClick as an instance of its base class, view.
                inputNumber.append(b.getText().toString());  //Not all views can have texts, thus before the getText method can be called we must cast it to a widget that can use the getText method (Button)
            }
        };


        /*Storing the button fields into an array and using a loop to iterate through the loop and set the OnClickListener for each button

        Button[] buttonList = {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonDot};

        for (int i = 0; i < buttonList.length ; i++)

            buttonList[i].setOnClickListener(listener);
        }*/


        //Creating onClickListener for operands

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


        //Functionality of calculator; The arithmetic keys will cause previous operations to be performed and then become the next operation to be performed, and this is done by storing the operation with in the pending operation

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;   //Casts view to a button
                String op = b.getText().toString(); //Obtains text read from button clicked and converts it into a string (stored in String op)
                String value = inputNumber.getText().toString(); // Reads number from the Edit Text portion and stores it into the String variable called value

                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);    // Even if there is an exception, code still sets pending operation otherwise the user will not be able to change the type of operation without entering some number and performing the current operation
                } catch (NumberFormatException e) {    //Error found when inputting a single decimal point as input
                    inputNumber.setText("");             // Code to be excecuted it there is an exception
                }


                /*if (value.length() != 0) {      //There may be a scenario where user clicks on operation button before a number, therefore we must make a condition for when the length of the value string is not zero
                    performOperation(value, op);
                }*/

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

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = inputNumber.getText().toString();
                if (value.length() == 0) {          //If text in widget is empty, set to "-"
                    inputNumber.setText("-");
                } else {                             //Negate any number that is typed into widget, polarity updated by multiply by -1
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        inputNumber.setText(doubleValue.toString()); //Widgets are updated into new number
                    } catch (NumberFormatException e) {        //inputNumber is "-" or "." so clear it
                        inputNumber.setText("");           // Invalid input cought by catch block; clear widget
                    }
                }

            }
        });
    }

    //When a number is inputted into the editText combined with an operation amd the orientation changes, the number will remain present during orientation change however operation will disappear
    //onSaveInstanceState is called if needed when Android is about to destroy the activity, such as when orientation changes

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation); //Restoring variables by reading through bundle and also updating the display to see which operation is pending
        if (operand1 != null) {      //Operand1 must not be null otherwise program will crash when orientation changes without any numbers inputted
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);      //Saves this state for user
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation) {

        if (null == operand1) {    // The valueOf method returns the relevant Number Object holding the value of the argument passed.
            operand1 = value;   //Lone decimal point input crashes app because string is unable to be converted to a double; must change data type of first parameter
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation) {

                case "=":
                    operand1 = value;
                    break;

                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;

                case "*":
                    operand1 *= value;
                    break;

                case "+":
                    operand1 += value;
                    break;

                case "-":
                    operand1 -= value;
                    break;
            }
        }
        result.setText(operand1.toString());
        inputNumber.setText("");


    }

}



