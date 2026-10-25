package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.expression.CompiledExpression;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Thread-safe LRU cache for {@link CompiledExpression} instances.
 */
public class ExpressionCache {
    private final int capacity;
    private final Map<String, CompiledExpression> cache;

    /**
     * Constructs an {@code ExpressionCache} with the specified maximum capacity.
     *
     * @param capacity the maximum number of compiled expressions to cache
     */
    public ExpressionCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<String, CompiledExpression>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CompiledExpression> eldest) {
                return size() > ExpressionCache.this.capacity;
            }
        };
    }

    /**
     * Generates a cache key for an expression and configuration.
     *
     * @param expression the raw expression string
     * @param config the evaluation configuration
     * @return the cache key string, or null if the expression should not be cached (e.g. custom functions present)
     */
    public static String computeKey(String expression, EvaluationConfig config) {
        if (config != null && !config.getCustomFunctions().isEmpty()) {
            return null; // Do not cache expressions with ephemeral custom functions
        }
        String mode = (config != null && config.getAngleMode() != null) ? config.getAngleMode().name() : "DEGREES";
        return expression + "@" + mode;
    }

    /**
     * Retrieves a cached compiled expression.
     *
     * @param key the cache key
     * @return the cached {@link CompiledExpression}, or null if not found
     */
    public synchronized CompiledExpression get(String key) {
        if (key == null) return null;
        return cache.get(key);
    }

    /**
     * Caches a compiled expression.
     *
     * @param key the cache key
     * @param expr the compiled expression to cache
     */
    public synchronized void put(String key, CompiledExpression expr) {
        if (key != null && expr != null) {
            cache.put(key, expr);
        }
    }

    /**
     * Clears all cached expressions.
     */
    public synchronized void clear() {
        cache.clear();
    }

    /**
     * Returns the current number of cached expressions.
     *
     * @return cache size
     */
    public synchronized int size() {
        return cache.size();
    }
}
