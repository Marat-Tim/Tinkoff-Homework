package ru.marat;

/**
 * Трехмерный неизменяемый вектор
 * @param x Первая координата
 * @param y Вторая координата
 * @param z Третья координата
 */
public record Vector3d(double x, double y, double z) {
    /**
     * Длина вектора
     * @return Длина вектора
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Возвращает минимальный угол между двумя векторами по формуле<br>
     * angle = acos((v1 * v2) / (|v1| * |v2|)), * - скалярное произведение
     * @param other Вектор с которым нужно найти минимальный угол
     * @return Угол между двумя векторами
     */
    public double minAngleWith(Vector3d other) {
        return Math.acos(this.dot(other) / (this.length() * other.length()));
    }

    /**
     * Возвращает скалярное произведение двух векторов по формуле<br>
     * result = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
     * @param other Вектор на который нужно умножить
     * @return Скалярное произведение двух векторов
     */
    public double dot(Vector3d other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Возвращает векторное произведение двух векторов по формуле<br>
     * result.x = v1.y * v2.z - v1.z * v2.y<br>
     * result.y = v1.z * v2.x - v1.x * v2.z<br>
     * result.z = v1.x * v2.y - v1.y * v2.x
     * @param other Вектор на который нужно умножить
     * @return Векторное произведение двух векторов
     */
    public Vector3d cross(Vector3d other) {
        return new Vector3d(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x);
    }

    @Override
    public String toString() {
        return "(%s, %s, %s)".formatted(x, y, z);
    }

    public static Vector3d parseVector3d(String text) throws IllegalArgumentException {
        var xyz = text.substring(1, text.length() - 1).split(", ");
        return new Vector3d(
                Double.parseDouble(xyz[0]),
                Double.parseDouble(xyz[1]),
                Double.parseDouble(xyz[2]));
    }
}
