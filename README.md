# RushEvaluator: A Java Mathematical Expression Evaluator

RushEvaluator is a powerful and flexible mathematical expression evaluator for Java. It can parse and evaluate complex mathematical expressions, including those with variables, custom functions, and a wide range of operators.

## Features

*   **High Precision:** Uses `BigDecimal` for all calculations to ensure maximum precision.
*   **Rich Operator Set:** Supports standard arithmetic operators (`+`, `-`, `*`, `/`), exponentiation (`^`), and more.
*   **Trigonometric Functions:** Includes `sin`, `cos`, `tan`, `asin`, `acos`, and `atan`.
*   **Logarithmic Functions:** Supports `log` (base 10) and `ln` (natural logarithm).
*   **Constants:** Pre-defined constants for `π` (pi) and `e` (Euler's number).
*   **Implicit Multiplication:** Automatically handles implicit multiplication (e.g., `2(3)` is treated as `2*3`).

## How to Use

To evaluate an expression, simply call the static `evaluate` method in the `RushEvaluator` class:

```java
import in.iamrushier.evaluator.RushEvaluator;

public class Example {
    public static void main(String[] args) {
        String expression = "sin(30) + 2^3";
        String result = RushEvaluator.evaluate(expression);
        System.out.println("Result: " + result); // Output: Result: 8.5
    }
}
```

## Error Handling

The evaluator will throw exceptions for invalid expressions, such as `NumberFormatException` for syntax errors and `IllegalArgumentException` for domain errors in functions (e.g., `log(-1)`).
