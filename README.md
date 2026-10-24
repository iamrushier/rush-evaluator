# RushEvaluator

[![Maven Central](https://img.shields.io/maven-central/v/io.github.iamrushier/rush-evaluator.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.iamrushier/rush-evaluator)
[![CI](https://github.com/iamrushier/rush-evaluator/actions/workflows/ci.yml/badge.svg)](https://github.com/iamrushier/rush-evaluator/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-8%2B-orange.svg)]()
[![GitHub Release](https://img.shields.io/github/v/release/iamrushier/rush-evaluator)](https://github.com/iamrushier/rush-evaluator/releases)

**RushEvaluator** is a high-precision mathematical expression evaluator library for Java. Built with a formal Lexer and Recursive Descent Parser, it evaluates complex mathematical strings with internal `BigDecimal` calculations for precision, automatic double fallback, implicit multiplication, and support for unicode math symbols.

---

## 📦 Installation

Add RushEvaluator to your project using your preferred build tool:

### Gradle (Groovy DSL)
```groovy
implementation 'io.github.iamrushier:rush-evaluator:2.0.6'
```

### Gradle (Kotlin DSL)
```kotlin
implementation("io.github.iamrushier:rush-evaluator:2.0.6")
```

### Apache Maven
```xml
<dependency>
    <groupId>io.github.iamrushier</groupId>
    <artifactId>rush-evaluator</artifactId>
    <version>2.0.6</version>
</dependency>
```

---

## ✨ Key Features

- **💎 High Precision:** Calculations default to `BigDecimal` with `DECIMAL128` precision, seamlessly falling back to `double` only when necessary.
- **📐 Complete Arithmetic & Operators:**
  - Standard arithmetic: `+`, `-`, `*` (or `×`), `/` (or `÷`)
  - Exponents and Roots: Exponentiation (`^`), Square Root (`√`), and Cube Root (`∛`)
  - Unary support: Leading signs (e.g., `-5`, `+π`) and parenthesized negatives `(-3)`
- **📈 Mathematical Functions:**
  - Trigonometric: `sin`, `cos`, `tan`, `asin`, `acos`, `atan` (calculated in degrees)
  - Logarithmic: `log` (base 10) and `ln` (natural logarithm)
- **🔢 Constants:** High-precision constants for `π` (Pi), `e` (Euler's number), and `∞` (Infinity).
- **🤝 Smart Syntax & Preprocessing:**
  - **Implicit Multiplication:** Handles `2π`, `2(3+4)`, `(2)(3)`, `sin(30)2`
  - **Percentage handling:** Automatically converts `10%` to `10 * (1/100)`
- **🛡️ Custom Exception Hierarchy:** Clear distinction between syntax errors and mathematical domain errors.

---

## 🚀 Quick Start

```java
import io.github.iamrushier.evaluator.RushEvaluator;

public class Main {
    public static void main(String[] args) {
        // Basic arithmetic & precedence
        String res1 = RushEvaluator.evaluate("2 + 3 * 4"); // "14"

        // Powers and nested roots
        String res2 = RushEvaluator.evaluate("√√16 + 2^3"); // "10"

        // Trigonometric function (degrees) and constants
        String res3 = RushEvaluator.evaluate("sin(30) + 2π"); // "6.7831853071..."

        // Implicit multiplication and percentage
        String res4 = RushEvaluator.evaluate("50% * 200 + 2(3 + 4)"); // "114"
    }
}
```

> [!NOTE]
> **Trigonometric Angle Mode:** Trigonometric functions (`sin`, `cos`, `tan`, etc.) currently evaluate angles in **Degrees** (e.g., `sin(30)` = `0.5`). Support for configurable angle modes (Radians / Degrees) is planned for an upcoming release.

---

## ⚠️ Exception Handling

RushEvaluator provides explicit exceptions under the `io.github.iamrushier.evaluator.exception` package:

| Exception | Cause / Example |
| :--- | :--- |
| **`SyntaxException`** | Malformed expression syntax, mismatched parentheses (`(2+3`), or invalid consecutive operators (`2++3`). |
| **`DomainException`** | Mathematical domain errors such as division by zero (`1/0`), square root of negative numbers (`√-1`), or undefined forms (`0/0`, `0^0`). |
| **`EvaluationException`** | Common unchecked base exception for all library-specific errors. |

### Example

```java
import io.github.iamrushier.evaluator.RushEvaluator;
import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.exception.SyntaxException;

try {
    String result = RushEvaluator.evaluate("1 / 0");
} catch (DomainException e) {
    System.err.println("Math error: " + e.getMessage());
} catch (SyntaxException e) {
    System.err.println("Syntax error: " + e.getMessage());
}
```

---

## 🛠️ Building & Testing Locally

### Prerequisites
- Java JDK 8 or higher
- Git

### Build Commands
```bash
# Clone the repository
git clone https://github.com/iamrushier/rush-evaluator.git
cd rush-evaluator

# Run the test suite
./gradlew test

# Build the JAR artifact
./gradlew build
```

---

## 🗺️ Roadmap & Standardization

This library is undergoing continuous improvement to make it more standardized and developer-friendly:
- [ ] Configurable **AngleMode** (Degrees vs. Radians).
- [ ] Direct numeric return type overloads (`evaluateToBigDecimal(String)`).
- [ ] Support for variables and custom function definitions.
- [ ] Multi-platform CI matrix testing on Java 8, 11, 17, and 21.

---

## 📄 License

This project is open-source and licensed under the [Apache License 2.0](LICENSE).
