# Contributing to RushEvaluator

Thank you for your interest in contributing to **RushEvaluator**! Whether you are fixing a bug, improving documentation, suggesting new mathematical operators, or optimizing performance, all contributions are welcome.

---

## 🛠️ Getting Started

### Prerequisites
- **Java JDK 8 or higher** (JDK 17 recommended for local development).
- **Git** installed on your system.

### Fork & Clone
1. Fork the repository on GitHub.
2. Clone your fork locally:
   ```bash
   git clone https://github.com/<your-username>/rush-evaluator.git
   cd rush-evaluator
   ```
3. Create a feature branch:
   ```bash
   git checkout -b feature/my-new-feature
   ```

---

## 🏗️ Building and Testing

RushEvaluator uses the Gradle wrapper (`gradlew`).

- **Run all unit tests:**
  ```bash
  ./gradlew test
  ```
- **Build the project and JAR:**
  ```bash
  ./gradlew build
  ```
- **Generate Javadocs:**
  ```bash
  ./gradlew javadoc
  ```

---

## 🧪 Testing Guidelines

- We use **JUnit 5** for all tests (`src/test/java`).
- Every new feature, operator, or function must include corresponding unit tests.
- When fixing a bug, please add a regression test that fails without your fix and passes with it.
- Ensure edge cases (division by zero, negative numbers, missing parentheses, large numbers) are covered.

---

## 📝 Coding Conventions

- **Java Compatibility:** The library targets **Java 1.8**. Avoid language features or standard library APIs introduced in Java 9 or later in `src/main`.
- **Mathematical Precision:** Prefer `BigDecimal` whenever possible to preserve high numerical precision.
- **Documentation:** Provide clear Javadoc for any public class, interface, and method.
- **File Encoding:** UTF-8.

---

## 🚀 Submitting a Pull Request

1. Ensure all tests pass: `./gradlew test`.
2. Commit your changes with clear, descriptive commit messages (following conventional commits if possible, e.g., `feat:`, `fix:`, `docs:`).
3. Push to your fork:
   ```bash
   git push origin feature/my-new-feature
   ```
4. Open a Pull Request against the `master` branch. Fill out the PR template with relevant details and tests performed.

Thank you for helping make RushEvaluator better!
