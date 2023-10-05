package ru.marat.repository;

public record Named<T>(String name, T object) {
}
