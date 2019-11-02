package com.group0565.tsu.enums;

public enum Grade {
    SS(100_000_000, "SS", 5, 255, 215, 0),
    S(50_000_000, "S", 4, 255, 215, 0),
    A(1_000_000, "A", 3, 255, 0, 255),
    B(500_000, "B", 2, 0, 255, 255),
    C(100_000, "C", 1, 0, 255, 0),
    F(0, "F", 0, 255, 0, 0);

    int minScore;
    String string;
    int value;
    int r, g, b;

    Grade(int minScore, String string, int value, int r, int g, int b) {
        this.minScore = minScore;
        this.string = string;
        this.value = value;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static Grade str2Grade(String str) {
        switch (str) {
            case "SS":
                return SS;
            case "S":
                return S;
            case "A":
                return A;
            case "B":
                return B;
            case "C":
                return C;
            default:
                return F;
        }
    }

    public static Grade num2Grade(int num) {
        switch (num) {
            case 5:
                return SS;
            case 4:
                return S;
            case 3:
                return A;
            case 2:
                return B;
            case 1:
                return C;
            default:
                return F;
        }
    }

    public static Grade score2Grade(int score) {
        if (score >= SS.minScore) {
            return SS;
        } else if (score >= S.minScore) {
            return S;
        } else if (score >= A.minScore) {
            return A;
        } else if (score >= B.minScore) {
            return B;
        } else if (score >= C.minScore) {
            return C;
        } else return F;
    }

    public int getMinScore() {
        return minScore;
    }

    public String getString() {
        return string;
    }

    public int getValue() {
        return value;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
}
