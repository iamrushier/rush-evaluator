# RushEvaluator: High-Precision Java Mathematical Expression Evaluator

RushEvaluator is a robust, high-precision mathematical expression evaluator for Java. Designed specifically for calculator engines and scientific applications, it utilizes `BigDecimal` to ensure maximum accuracy while providing a rich set of operators and functions.

## ✨ Features

*   **💎 High Precision:** All internal calculations use `BigDecimal` with a standard fallback to `double` only when necessary.
*   **📐 Comprehensive Operator Set:**
    *   Basic Arithmetic: `+`, `-`, `*` (or `×`), `/` (or `÷`)
    *   Powers & Roots: Exponentiation (`^`), Square Root (`√`), and Cube Root (`∛`)
    *   Unary Operators: Support for leading signs (e.g., `-5`, `+π`) and parenthesized negatives.
*   **📈 Mathematical Functions:**
    *   Trigonometric: `sin`, `cos`, `tan`, `asin`, `acos`, `atan` (supports large angle normalization).
    *   Logarithmic: `log` (base 10) and `ln` (natural logarithm).
*   **🔢 Constants:** High-precision constants for `π` (Pi), `e` (Euler's number), and `∞` (Infinity).
*   **🤝 Smart Syntax:** 
    *   Implicit Multiplication (e.g., `2π`, `2(3+4)`, `sin(30)2`).
    *   Percentage support (e.g., `10% + 5` -> `10 * (1/100) + 5`).
*   **🛡️ Robust Parsing:** Built with a formal Lexer and Recursive Descent Parser to handle complex nested expressions and operator precedence.

## 🚀 How to Use

To evaluate an expression, call the static `evaluate` method:

```java
import io.github.iamrushier.evaluator.RushEvaluator;

public class Example {
    public static void main(String[] args) {
        // Simple expression
        String res1 = RushEvaluator.evaluate("sin(30) + 2^3"); // "8.5"
        
        // Complex expression with symbols and implicit multiplication
        String res2 = RushEvaluator.evaluate("2√9 + 4π"); // "12.2831853071..."
        
        // Nested roots
        String res3 = RushEvaluator.evaluate("√√16"); // "2"
    }
}
```

## ⚠️ Error Handling

RushEvaluator provides a specialized exception hierarchy for clearer feedback:

*   **`SyntaxException`:** Thrown for malformed expressions (e.g., mismatched parentheses `(1+2`, double operators `2++3`).
*   **`DomainException`:** Thrown for mathematical errors (e.g., division by zero `1/0`, square root of negative `√-1`).
*   **`EvaluationException`:** The base class for all library-specific exceptions.

## 🛠 Project Structure

*   `io.github.iamrushier.evaluator.parser`: Formal Lexer and Token-based parser.
*   `io.github.iamrushier.evaluator.util`: `CalculationEngine` for precision management and `Operand` abstraction.
*   `io.github.iamrushier.evaluator.function`: Modular implementations of mathematical functions.

## 📄 License
This project is open-source. See the repository for license details.
