package com.example.caculatoruidesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    View.OnClickListener ButtononClickListener;
    android.widget.Button btndu, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btndel, btnsub, btnadd, btndiv, btnmul, btnresult, reset;
    EditText text1;
    EditText text2;
    EditText operator;
    String currentValue;
    // String lastValue;
    float lastValue1;
    float currentValue1;


    public static String format(float f) {
        if (f - (int) f == 0) {
            String temp = String.valueOf(f);
            temp = temp.substring(0, temp.indexOf('.'));
            return temp;
        } else {
            return String.valueOf(f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
    }

    private void addControl() {
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnmul = findViewById(R.id.btnmul);
        btndel = findViewById(R.id.btndel);
        btnsub = findViewById(R.id.btnsub);
        btnadd = findViewById(R.id.btnadd);
        btndiv = findViewById(R.id.btndiv);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        btnresult = findViewById(R.id.btnresult);
        operator = findViewById(R.id.operator);
        reset = findViewById(R.id.reset);
        btndu = findViewById(R.id.btndu);
        btndel = findViewById(R.id.btndel);

    }

    private void addEvent() {
        ButtononClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                android.widget.Button btn = (android.widget.Button) view;
                switch (btn.getId()) {
                    case R.id.btn0:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn1:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn2:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn3:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn4:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn5:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn6:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn7:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn8:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btn9:
                        currentValue = setCurrentValue(btn);
                        text2.setText(currentValue);
                        break;
                    case R.id.btndel:
                        delString();
                        break;
                    case R.id.btnsub:
                        operator("-");
                        //Toast.makeText(MainActivity.this, "Số " + btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btnadd:
                        operator("+");
                        break;
                    case R.id.btnmul:
                        operator("*");
                        // Toast.makeText(MainActivity.this, "Số " + btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btndiv:
                        // Toast.makeText(MainActivity.this, "Số " + btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        operator("/");
                        break;
                    case R.id.btnresult:
                        //Toast.makeText(MainActivity.this, "Số " + btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        result();
                        break;
                    case R.id.reset:
                        //Toast.makeText(MainActivity.this, "Số " + btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        reset();
                        break;
                    case R.id.btndu:
                        //Toast.makeText(MainActivity.this, "Số " + btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        operator("%");
                        break;

                    default:
                        break;

                }
            }
        };
        btn0.setOnClickListener(ButtononClickListener);
        btn1.setOnClickListener(ButtononClickListener);
        btn2.setOnClickListener(ButtononClickListener);
        btn3.setOnClickListener(ButtononClickListener);
        btn4.setOnClickListener(ButtononClickListener);
        btn5.setOnClickListener(ButtononClickListener);
        btn6.setOnClickListener(ButtononClickListener);
        btn7.setOnClickListener(ButtononClickListener);
        btn8.setOnClickListener(ButtononClickListener);
        btn9.setOnClickListener(ButtononClickListener);
        btndel.setOnClickListener(ButtononClickListener);
        btnsub.setOnClickListener(ButtononClickListener);
        btndiv.setOnClickListener(ButtononClickListener);
        btnadd.setOnClickListener(ButtononClickListener);
        btnmul.setOnClickListener(ButtononClickListener);
        btnresult.setOnClickListener(ButtononClickListener);
        reset.setOnClickListener(ButtononClickListener);
        btndu.setOnClickListener(ButtononClickListener);
        btndel.setOnClickListener(ButtononClickListener);
        btndel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                text2.setText("");
                return true;
            }
        });
    }

    public String setCurrentValue(Button btn) {
        String temp = text2.getText().toString();
        temp += btn.getText().toString();
        return temp;
    }

    public float getCurrentValue(EditText e) {
        return Float.parseFloat(e.getText().toString());
    }

    public float getLastValue(EditText e) {
        return Float.parseFloat(e.getText().toString());
    }

    public void operator(String s) {
        try {
            if (text2.getText().toString().isEmpty()) {
                return;
            }
            if (text2.getText().toString() != null && text1.getText().toString() != null) {
                String temp = text2.getText().toString();
                text1.setText(temp);
                operator.setText(s);
            }
            if (operator.getText().toString().equals(s)) {
                currentValue1 = getCurrentValue(text2);

                text1.setText(format(currentValue1));
                text2.setText("");
                //  Toast.makeText(MainActivity.this, "Log th1", Toast.LENGTH_SHORT).show();
            } else {
                operator.setText(s);
                currentValue1 = getCurrentValue(text2);
                if (!text1.getText().toString().isEmpty()) {
                    lastValue1 = getLastValue(text1);
                }
                text1.setText(format(currentValue1));
                text2.setText("");
                //   Toast.makeText(MainActivity.this, "Log th2", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void result() {
        try {
            currentValue1 = Float.parseFloat(text2.getText().toString());
            String temp = text1.getText().toString();
            int index = getIndexOfOperation(temp);
            if (index >= 0) {
                lastValue1 = Float.parseFloat(temp.substring(index + 1, temp.length()));

            } else {
                lastValue1 = Float.parseFloat(text1.getText().toString());
                //Toast.makeText(MainActivity.this, "" + lastValue1, Toast.LENGTH_SHORT).show();
            }

            float c;
            switch (operator.getText().toString()) {
                case "+":

                    c = lastValue1 + currentValue1;
                    text2.setText("" + format(c));
                    break;
                case "-":
                    c = lastValue1 - currentValue1;
                    text2.setText("" + format(c));
                    break;
                case "*":
                    c = lastValue1 * currentValue1;
                    text2.setText("" + format(c));
                    break;
                case "/":
                    c = lastValue1 / currentValue1;
                    text2.setText("" + format(c));
                    break;
                case "%":
                    c = lastValue1 % currentValue1;
                    text2.setText("" + format(c));
                    break;
                default:
                    break;
            }
            text1.setText(format(lastValue1) + operator.getText().toString() + format(currentValue1));
            operator.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        text1.setText(null);
        text2.setText(null);
        operator.setText(null);
    }

    public void delString() {
        String temp = text2.getText().toString();
        if (!temp.isEmpty()) {
            temp = temp.substring(0, temp.length() - 1);
            text2.setText(temp);
        }
    }

    public int getIndexOfOperation(String s) {
        String[] op = {"+", "-", "*", "/", "%"};
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < op.length; j++) {
                if (Character.toString(s.charAt(i)).equals(op[j])) {
                    return i;
                }
            }
        }
        return -1;
    }

}