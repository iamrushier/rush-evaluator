# RushEvaluator

[![Maven Central](https://img.shields.io/maven-central/v/io.github.iamrushier/rush-evaluator.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.iamrushier/rush-evaluator)
[![CI](https://github.com/iamrushier/rush-evaluator/actions/workflows/ci.yml/badge.svg)](https://github.com/iamrushier/rush-evaluator/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-8%2B-orange.svg)]()
[![Javadoc](https://img.shields.io/badge/Javadoc-API_Docs-blue.svg)](https://iamrushier.github.io/rush-evaluator/)
[![GitHub Release](https://img.shields.io/github/v/release/iamrushier/rush-evaluator)](https://github.com/iamrushier/rush-evaluator/releases)

**RushEvaluator** is a high-precision, compiled mathematical expression evaluator library for Java. Built with a formal Lexer and Abstract Syntax Tree (AST) engine with automatic constant folding, it evaluates complex mathematical strings with internal `BigDecimal` precision, automatic double fallback, dynamic variables, multi-argument math functions, and scope-safe custom functions.

---

## 📦 Installation

Add RushEvaluator to your project using your preferred build tool:

### Gradle (Groovy DSL)
```groovy
implementation 'io.github.iamrushier:rush-evaluator:2.2.0'
```

### Gradle (Kotlin DSL)
```kotlin
implementation("io.github.iamrushier:rush-evaluator:2.2.0")
```

### Apache Maven
```xml
<dependency>
    <groupId>io.github.iamrushier</groupId>
    <artifactId>rush-evaluator</artifactId>
    <version>2.2.0</version>
</dependency>
```

---

## ✨ Key Features

- **⚡ Compiled AST & Constant Folding:** Parse once and evaluate in high-speed loops with `RushEvaluator.compile()`. Automatically precomputes static subtrees (e.g. `2 * π`) at compile time.
- **💎 High Precision:** Calculations default to `BigDecimal` with `DECIMAL128` precision, seamlessly falling back to `double` only when necessary.
- **🏷️ Variable Evaluation & Introspection:** Dynamic algebraic variables (`x + y`, `2x`, `x(y + 1)`) with introspection (`expr.getVariableNames()`, `expr.isConstant()`, `expr.validateVariables()`).
- **📐 Complete Arithmetic & Operators:**
  - Standard arithmetic: `+`, `-`, `*` (or `×`), `/` (or `÷`)
  - Exponents and Roots: Exponentiation (`^`), Square Root (`√`), and Cube Root (`∛`)
  - Unary support: Leading signs (e.g., `-5`, `+π`) and parenthesized negatives `(-3)`
- **📈 Comprehensive Function Library:**
  - Multi-argument utilities: `min(a, b, ...)`, `max(a, b, ...)`, `round(x, decimals)`
  - Standard utilities: `abs(x)`, `floor(x)`, `ceil(x)`
  - Trigonometric: `sin`, `cos`, `tan`, `asin`, `acos`, `atan` (configurable: Degrees or Radians)
  - Logarithmic: `log` (base 10) and `ln` (natural logarithm)
- **🧩 Scope-Safe Custom Functions:** Register user-defined lambdas thread-safely on `EvaluationConfig`.
- **🔢 Constants:** High-precision constants for `π` (Pi), `e` (Euler's number), and `∞` (Infinity).
- **🤝 Smart Syntax & Preprocessing:**
  - **Implicit Multiplication:** Handles `2π`, `2(3+4)`, `(2)(3)`, `2x`, `x(y + 1)`
  - **Percentage handling:** Automatically converts `10%` to `10 * (1/100)`
- **🛡️ Custom Exception Hierarchy:** Clear distinction between syntax errors and mathematical domain errors.

---

## 🚀 Quick Start

### 1. Basic Evaluation & Functions
```java
import io.github.iamrushier.evaluator.RushEvaluator;
import io.github.iamrushier.evaluator.config.AngleMode;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // Basic arithmetic & precedence
        String res1 = RushEvaluator.evaluate("2 + 3 * 4"); // "14"

        // Multi-argument functions & utilities
        String minVal = RushEvaluator.evaluate("min(10, 20, 5, 8)"); // "5"
        String rounded = RushEvaluator.evaluate("round(3.14159, 2)"); // "3.14"
        String absVal = RushEvaluator.evaluate("abs(-42) + floor(3.9)"); // "45"

        // Trigonometry: Degrees (default) and Radians
        String deg = RushEvaluator.evaluate("sin(30)"); // "0.5"
        String rad = RushEvaluator.evaluate("sin(π / 6)", AngleMode.RADIANS); // "0.5"

        // Direct numeric output (no string re-parsing needed)
        BigDecimal val = RushEvaluator.evaluateToBigDecimal("10 / 4 + 2.5"); // 5.0
        double dVal = RushEvaluator.evaluateToDouble("2^10"); // 1024.0
    }
}
```

### 2. High-Performance Compiled Expressions
```java
import io.github.iamrushier.evaluator.RushEvaluator;
import io.github.iamrushier.evaluator.expression.CompiledExpression;
import java.util.Map;
import java.util.HashMap;

public class CompiledExample {
    public static void main(String[] args) {
        // Compile once with automatic constant folding
        CompiledExpression expr = RushEvaluator.compile("2 * π * radius + min(offset, 10)");

        // Introspect required variables
        System.out.println(expr.getVariableNames()); // ["radius", "offset"]

        // Evaluate in loops with varying inputs at compile speed
        Map<String, Object> vars = new HashMap<>();
        vars.put("offset", 5);
        for (int r = 1; r <= 1000; r++) {
            vars.put("radius", r);
            double perimeter = expr.evaluateToDouble(vars);
        }
    }
}
```

### 3. Custom User-Defined Functions
```java
import io.github.iamrushier.evaluator.RushEvaluator;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.util.Operand;

public class CustomFunctionExample {
    public static void main(String[] args) {
        EvaluationConfig config = EvaluationConfig.degrees()
            .withFunction("hypot", (args, cfg) -> {
                double a = args.get(0).getAsDouble();
                double b = args.get(1).getAsDouble();
                return new Operand(Math.hypot(a, b));
            });

        String result = RushEvaluator.evaluate("hypot(3, 4)", config); // "5"
    }
}
```

---

## ⚠️ Exception Handling

RushEvaluator provides explicit exceptions under the `io.github.iamrushier.evaluator.exception` package:

| Exception | Cause / Example |
| :--- | :--- |
| **`SyntaxException`** | Malformed expression syntax, mismatched parentheses (`(2+3`), or undefined variables. |
| **`DomainException`** | Mathematical domain errors such as division by zero (`1/0`), square root of negative numbers (`√-1`), or undefined forms (`0/0`, `0^0`). |
| **`EvaluationException`** | Common unchecked base exception for all library-specific errors. |

---

## 🛠️ Building & Testing Locally

```bash
# Clone the repository
git clone https://github.com/iamrushier/rush-evaluator.git
cd rush-evaluator

# Run the test suite with coverage
./gradlew test jacocoTestReport

# Build the JAR artifact
./gradlew build
```

---

## 🗺️ Roadmap & Standardization

- [x] Configurable **AngleMode** (Degrees vs. Radians).
- [x] Direct numeric return type overloads (`evaluateToBigDecimal` and `evaluateToDouble`).
- [x] Multi-platform CI matrix testing on Java 17 and 21 across Ubuntu and Windows.
- [x] Variables and algebraic evaluation with implicit multiplication.
- [x] Compiled Expression AST engine with Constant Folding.
- [x] Multi-argument functions (`min`, `max`, `round`) and standard utilities (`abs`, `floor`, `ceil`).
- [x] Scope-safe custom user-defined functions via `EvaluationConfig`.
- [x] Automated Javadoc hosting on GitHub Pages.

---

## 📄 License

This project is open-source and licensed under the [Apache License 2.0](LICENSE).
