package ru.marat.command;

import ru.marat.exception.IncorrectArgSizeException;

class ArgsUtils {
    private ArgsUtils() {
    }

    static void checkArgsSize(String[] args, int expectedSize) throws IncorrectArgSizeException {
        if (args.length != expectedSize) {
            throw new IncorrectArgSizeException("Неправильное количество аргументов(%s, а должно быть %s)"
                    .formatted(args.length, expectedSize));
        }
    }
}
