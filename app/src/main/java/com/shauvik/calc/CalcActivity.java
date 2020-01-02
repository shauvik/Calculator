package com.shauvik.calc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CalcActivity extends AppCompatActivity {
    String operand = "";
    int operator = 0;
    boolean doReset=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    
    public void handleClick(View view) {
        TextView tv = (TextView) findViewById(R.id.textView);
        System.out.println(tv.getText());
        int value = 0;
        try {
            value = Integer.parseInt(tv.getText().toString());
        } catch (NumberFormatException nfe) {
            // Pass: Not an integer
        }

        Button btn = (Button) view;
        
        StringBuffer dbgStr = new StringBuffer(value + ", " + btn.getText());
        
        switch(view.getId()) {
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
                StringBuffer sb = new StringBuffer();
                if(value != 0 && !doReset) {
                    sb.append(tv.getText());
                }
                sb.append(btn.getText());
                tv.setText(sb.toString());
                doReset = false;
                break;
            case R.id.add:
            case R.id.subtract:
            case R.id.multiply:
            case R.id.divide:
                String tvStr = tv.getText().toString();
                if(isNumeric(tvStr)) {
                    operand = tvStr;
                }
                operator = view.getId();
                doReset = true;
                tv.setText(btn.getText());
                break;
            case R.id.equals:
                String result = calculateResult(operator, operand, tv.getText().toString());
                tv.setText(result);
                operand = "";
                operator = 0;
                doReset = true;
        }

        dbgStr.append(", "+operand + ", "+ operator+", reset="+doReset);
        System.out.println(dbgStr);
        
    }

    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+");  //match a number with optional '-' and decimal.
    }
    
    String calculateResult(int op, String a, String b) {
        System.out.println("Calculating "+ op + ", "+ a +", "+ b);
        int x = Integer.parseInt(a);
        int y = Integer.parseInt(b);
        System.out.println("=>"+x+","+y);
        int result = 0;
        switch(op) {
            case R.id.add:
                result = x+y; break;
            case R.id.subtract:
                result = x-y; break;
            case R.id.multiply:
                result = x*y; break;
            case R.id.divide:
                try {
                    result = x / y;
                }catch(ArithmeticException ae) {
                    return "ERROR";
                }
                break;
            default:
                return b;
        }
        System.out.println("Result="+result);
        return Integer.toString(result);
    }
}
