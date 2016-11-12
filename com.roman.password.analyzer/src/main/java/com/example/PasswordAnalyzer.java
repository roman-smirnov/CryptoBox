package com.example;

public class PasswordAnalyzer {

    private int mPasswordStrengthScore;
    private String mPasswordStrengthDescription;

    public PasswordAnalyzer() {
        mPasswordStrengthScore = 0;
        mPasswordStrengthDescription = "";
    }

    public void calcPasswordStregth(String password) {
        if (password.length() >= 16) {
            mPasswordStrengthScore = 10;
            mPasswordStrengthDescription = "excellent";
        } else if (password.length() >= 12) {
            mPasswordStrengthScore = 8;
            mPasswordStrengthDescription = "good";
        } else if (password.length() >= 8) {
            mPasswordStrengthScore = 6;
            mPasswordStrengthDescription = "medicore";
        } else if (password.length() >= 6) {
            mPasswordStrengthScore = 4;
            mPasswordStrengthDescription = "weak";
        } else if (password.length() >= 4) {
            mPasswordStrengthScore = 2;
            mPasswordStrengthDescription = "very weak";
        } else {
            mPasswordStrengthScore = 0;
            mPasswordStrengthDescription = "horrible";
        }
    }

    public int getPasswordStrengthScore() {
        return mPasswordStrengthScore;
    }

    public String getPasswordStrengthDescription() {
        return mPasswordStrengthDescription;
    }
}
