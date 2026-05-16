# RushEvaluator Project Context

## Project Overview
`RushEvaluator` is a Java library designed for parsing and evaluating complex mathematical expressions. It aims for high precision by utilizing `BigDecimal` for its internal calculations.

### Key Features
- **Arithmetic Operators:** Supports `+`, `-`, `*`, `/`, and `^`.
- **Trigonometric Functions:** `sin`, `cos`, `tan`, `asin`, `acos`, `atan`.
- **Logarithmic Functions:** `log` (base 10) and `ln` (natural logarithm).
- **Constants:** Pre-defined constants for π (pi), e (Euler's number), and ∞ (infinity).
- **Implicit Multiplication:** Handles expressions like `2(3)` or `2π`.
- **High Precision:** Uses `BigDecimal` with fallback to `double` for operations exceeding `BigDecimal` limits.

### Architecture
- **Entry Point:** `io.github.iamrushier.evaluator.RushEvaluator` provides the static `evaluate(String)` method.
- **Parser:** `io.github.iamrushier.evaluator.parser.ExpressionParser` implements a recursive descent parser to handle operator precedence and nested expressions.
- **Validator:** `io.github.iamrushier.evaluator.util.ExpressionValidator` preprocesses the input string using regex to handle whitespace, special characters (×, ÷, %), and implicit multiplication.
- **Handlers:** Specialized classes in `io.github.iamrushier.evaluator.operator` and `io.github.iamrushier.evaluator.function` manage specific mathematical operations.

## Building and Running

### Prerequisites
- Java JDK 8 or higher.
- Gradle (provided via `gradlew` wrapper).

### Common Commands
- **Build the project:**
  ```bash
  ./gradlew build
  ```
- **Run tests:**
  ```bash
  ./gradlew test
  ```
- **Clean the build directory:**
  ```bash
  ./gradlew clean
  ```
- **Generate Javadoc:**
  ```bash
  ./gradlew javadoc
  ```
- **Publish to Maven/GitHub Packages:**
  ```bash
  ./gradlew publish
  ```

## Development Conventions

### Coding Style
- **Java Compatibility:** Targets Java 1.8.
- **Precision:** Prefer `BigDecimal` for calculations to maintain precision.
- **Documentation:** Use Javadoc for all public classes and methods.
- **Encodings:** All files should be saved in UTF-8.

### Testing Practices
- **Framework:** Uses JUnit 5 (`jupiter-api`).
- **Coverage:** Tests are located in `src/test/java`.
- **Patterns:** Each major component has a corresponding test class (e.g., `RushEvaluatorTest`, `ExpressionParserTest`).
- **Special Cases:** Ensure tests cover edge cases like division by zero, very large numbers, and invalid expression syntax.

### Symbols
- The codebase and tests use Unicode characters for mathematical constants:
  - `π` (U+03C0) for Pi.
  - `∞` (U+221E) for Infinity.
