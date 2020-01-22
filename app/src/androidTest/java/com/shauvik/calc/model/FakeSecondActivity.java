package com.shauvik.calc.model;

public class FakeSecondActivity {
    private static FakeSecondActivity instance;

    public static FakeSecondActivity get() {
        if (instance == null) {
            instance = new FakeSecondActivity();
        }

        return instance;
    }

    private FakeSecondActivity() {
    }

    public FakeSecondActivity doSomethingLocal() {
        return this;
    }

    public Calculator jumpBackToCalculator() {
        return Calculator.get();
    }
}