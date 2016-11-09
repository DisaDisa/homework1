package ru.ifmo.android_2016.calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class CalculatorActivity extends Activity {

    private String token = "";
    private View lastButton = null;
    private Double last = null;
    private String operation = "calc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        final View[] DIGITS, OPERATIONS;
        DIGITS = new View[]{findViewById(R.id.d0), findViewById(R.id.d1), findViewById(R.id.d2), findViewById(R.id.d3), findViewById(R.id.d4),
                findViewById(R.id.d5), findViewById(R.id.d6), findViewById(R.id.d7), findViewById(R.id.d8),
                findViewById(R.id.d9), findViewById(R.id.d0)
        };
        OPERATIONS = new View[]{findViewById(R.id.add), findViewById(R.id.sub),
                findViewById(R.id.mul), findViewById(R.id.div), findViewById(R.id.eqv)
        };
        for (final View button : DIGITS) {
            if (button == null) {
                continue;
            }
            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Arrays.asList(DIGITS).contains(lastButton)) {
                                if (!token.equals(""))
                                    last = Double.valueOf(token);
                                token = "";
                            }
                            token = token + ((Button) button).getText().toString();
                            ((TextView) findViewById(R.id.result)).setText(token);
                            lastButton = button;
                        }
                    }
            );
        }
        for (final View button : OPERATIONS) {
            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Arrays.asList(DIGITS).contains(lastButton) && !token.equals("")) {
                                Double result = null;
                                switch (operation) {
                                    case "add":
                                        result = last + Double.valueOf(token);
                                        break;
                                    case "sub":
                                        result = last - Double.valueOf(token);
                                        break;
                                    case "mul":
                                        result = last * Double.valueOf(token);
                                        break;
                                    case "div":
                                        result = last / Double.valueOf(token);
                                        break;
                                }
                                if (result != null) {
                                    if (result.longValue() == result) {
                                        token = Long.toString(result.longValue());
                                    } else {
                                        token = Double.toString(result);
                                    }
                                }
                                ((TextView) findViewById(R.id.result)).setText(token);
                            }
                            operation = getResources().getResourceEntryName(v.getId());
                            lastButton = button;
                        }
                    }
            );
        }
        findViewById(R.id.clear).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        token = "";
                        lastButton = null;
                        last = null;
                        operation = "calc";
                        ((TextView) findViewById(R.id.result)).setText(token);
                    }
                }
        );
        if (savedInstanceState != null) {
            token = savedInstanceState.getString("token");
            ((TextView) findViewById(R.id.result)).setText(token);
            if (savedInstanceState.getInt("lastButtonID") != 0) {
                lastButton = findViewById(savedInstanceState.getInt("lastButtonID"));
            }
            last = savedInstanceState.getDouble("last");
            operation = savedInstanceState.getString("operation");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("token", token);
        if (lastButton != null)
            outState.putInt("lastButtonID", lastButton.getId());
        if (last != null) {
            outState.putDouble("last", last);
        }
        outState.putString("operation", operation);
    }
}