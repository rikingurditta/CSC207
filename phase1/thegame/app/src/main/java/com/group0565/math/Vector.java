package com.group0565.math;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.sqrt;

/**
 * A Class to represent a two dimensional Vector.
 *
 * <p>Contains relevant functions to do vector computation.
 *
 * <p>Vectors are immutable. All methods return a new Vector Object.
 */
public class Vector {
    private float x, y;

    /**
     * Create a new 2d vector using parameters x and y.
     *
     * @param x The x Coordinate of the vector.
     * @param y The y Coordinate of the vector.
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a new 2d vector of (0, 0)
     */
    public Vector() {
        this(0F, 0F);
    }

    /**
     * Create a new random vector with x and y between 0 and 1.
     *
     * <p>If r is null, Math.random is used.
     *
     * @param r The random number generator to use. Can be null.
     */
    public Vector(Random r) {
        this.x = r == null ? (float) Math.random() : r.nextFloat();
        this.y = r == null ? (float) Math.random() : r.nextFloat();
    }

    /**
     * Add this vector and other together and return a new vector as the result
     *
     * @param other The other vector to add
     * @return A new vector containing the result
     */
    public Vector add(final Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtract other vector from this vector and return a new vector as the result
     *
     * @param other The other vector to Subtract
     * @return A new vector containing the result
     */
    public Vector subtract(final Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    /**
     * Dot this vector and other and return the result
     *
     * @param other The other vector to add
     * @return The dot product of the two vectors
     */
    public float dot(final Vector other) {
        return (this.x * other.x + this.y * other.y);
    }

    /**
     * Multiply this vector by a constant and return a new Vector containing the result
     *
     * @param c The constant
     * @return A new vector containing the result
     */
    public Vector multiply(final float c) {
        return new Vector(c * this.x, c * this.y);
    }

    /**
     * Multiply this vector and another Vector element wise.
     *
     * @param other The vector to multiply
     * @return A new vector containing the result
     */
    public Vector elementMultiply(final Vector other) {
        return new Vector(this.x * other.x, this.y * other.y);
    }

    /**
     * Compute the Euclidean norm of this vector
     *
     * @return The norm of this vector
     */
    public float norm() {
        return (float) sqrt(dot(this));
    }

    /**
     * Getter for the x coordinate
     *
     * @return The x coordinate of this vector
     */
    public float getX() {
        return x;
    }

    /**
     * Getter for the y coordinate
     *
     * @return The y coordinate of this vector
     */
    public float getY() {
        return y;
    }

    @NonNull
    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Float.compare(vector.x, x) == 0 && Float.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
