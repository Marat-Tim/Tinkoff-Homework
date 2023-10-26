package ru.marat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Vector3dTest {
    static final double EPSILON = 0.0001;

    static Stream<Arguments> vectorsAndLengths() {
        return Stream.of(
                Arguments.of(new Vector3d(0, 0, 0), 0),
                Arguments.of(new Vector3d(3, 4, 0), 5),
                Arguments.of(new Vector3d(1, 1, 1), Math.sqrt(3)),
                Arguments.of(new Vector3d(-3, 0, -4), 5),
                Arguments.of(new Vector3d(100, 0, 0), 100),
                Arguments.of(new Vector3d(15, 10, 3), Math.sqrt(334))
        );
    }

    @DisplayName("Тест корректного вычисления длины вектора")
    @ParameterizedTest
    @MethodSource("vectorsAndLengths")
    void length(Vector3d vector3d, double expectedLength) {
        double actualLength = vector3d.length();

        assertEquals(expectedLength, actualLength, EPSILON);
    }

    static Stream<Arguments> vectorsAndAngles() {
        return Stream.of(
                Arguments.of(new Vector3d(1, 0, 0),
                        new Vector3d(0, 1, 0),
                        Math.PI / 2),
                Arguments.of(new Vector3d(1, 1, 0),
                        new Vector3d(1, 0, 0),
                        Math.PI / 4),
                Arguments.of(new Vector3d(0, 0, 0),
                        new Vector3d(15, 10, 23),
                        0),
                Arguments.of(new Vector3d(3, 4, 5),
                        new Vector3d(1, 5, 10),
                        0.403446105),
                Arguments.of(new Vector3d(12, 54, 23),
                        new Vector3d(12, 54, 23),
                        0),
                Arguments.of(new Vector3d(1, 0, 0),
                        new Vector3d(-1, 0, 0),
                        Math.PI)
        );
    }

    @DisplayName("Тест корректного вычисления угла между векторами")
    @ParameterizedTest
    @MethodSource("vectorsAndAngles")
    void minAngleWith(Vector3d vector1, Vector3d vector2, double expectedAngle) {
        double actualAngle = vector1.minAngleWith(vector2);

        assertEquals(expectedAngle, actualAngle, EPSILON);
    }

    static Stream<Arguments> vectorsAndDotProducts() {
        return Stream.of(
                Arguments.of(new Vector3d(3, 4, 5),
                        new Vector3d(1, 5, 10),
                        73),
                Arguments.of(new Vector3d(1, 2, 3),
                        new Vector3d(4, 5, 6),
                        32),
                Arguments.of(new Vector3d(0, 0, 0),
                        new Vector3d(1, 1, 1),
                        0)
        );
    }

    @DisplayName("Тест корректного вычисления скалярного произведения векторов")
    @ParameterizedTest
    @MethodSource("vectorsAndDotProducts")
    void dot(Vector3d vector1, Vector3d vector2, double expectedDotProduct) {
        double actualDotProduct = vector1.dot(vector2);

        assertEquals(expectedDotProduct, actualDotProduct, EPSILON);
    }

    static Stream<Arguments> vectorsAndCrossProducts() {
        return Stream.of(
                Arguments.of(new Vector3d(1, 2, 3),
                        new Vector3d(4, 5, 6),
                        new Vector3d(-3, 6, -3)),
                Arguments.of(new Vector3d(3, 4, 5),
                        new Vector3d(1, 5, 10),
                        new Vector3d(15, -25, 11)),
                Arguments.of(new Vector3d(0, 0, 0),
                        new Vector3d(1, 1, 1),
                        new Vector3d(0, 0, 0))
        );
    }

    @DisplayName("Тест корректного вычисления векторного произведения векторов")
    @ParameterizedTest
    @MethodSource("vectorsAndCrossProducts")
    void cross(Vector3d vector1, Vector3d vector2, Vector3d expectedCrossProduct) {
        Vector3d actualCrossProduct = vector1.cross(vector2);

        assertEquals(expectedCrossProduct, actualCrossProduct);
    }

    static Stream<Arguments> vectorToString() {
        return Stream.of(
                Arguments.of(new Vector3d(1.5, 2.5, 3.5),
                        "(1.5, 2.5, 3.5)"),
                Arguments.of(new Vector3d(-1, -2, -3),
                        "(-1.0, -2.0, -3.0)"),
                Arguments.of(new Vector3d(0, 0, 0),
                        "(0.0, 0.0, 0.0)")
        );
    }

    @DisplayName("Тест корректного преобразования в строку")
    @ParameterizedTest
    @MethodSource("vectorToString")
    void testToString(Vector3d vector, String expectedString) {
        String actualString = vector.toString();

        assertEquals(expectedString, actualString);
    }

    static Stream<Arguments> vectorParse() {
        return Stream.of(
                Arguments.of("(4.2, -3.1, 5.0)",
                        new Vector3d(4.2, -3.1, 5.0)),
                Arguments.of("(-2, 0, 0)",
                        new Vector3d(-2, 0, 0)),
                Arguments.of("(0, 0, 0)",
                        new Vector3d(0, 0, 0))
        );
    }

    @DisplayName("Тест корректного парсинга строки в вектор")
    @ParameterizedTest
    @MethodSource("vectorParse")
    void parseVector3d(String vectorString, Vector3d expectedVector) {
        Vector3d actualVector = Vector3d.parseVector3d(vectorString);

        assertEquals(expectedVector, actualVector);
    }

    @DisplayName("Тест преобразования некорректной строки в вектор")
    @ParameterizedTest
    @ValueSource(strings = {"1, 2, 3", "(1, 2)", "(1, 2, 3, 4)", "hello", "{3, 2, 1}", ""})
    void parseWrongString(String wrongString) {
        assertThrows(IllegalArgumentException.class, () -> Vector3d.parseVector3d(wrongString));
    }
}