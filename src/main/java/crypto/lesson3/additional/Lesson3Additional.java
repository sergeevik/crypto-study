package crypto.lesson3.additional;

import crypto.lesson3.additional.data.CommandLineArgs;
import crypto.lesson3.additional.service.CipherCommandLineArgProcessing;
import crypto.lesson3.additional.service.CipherService;
import crypto.lesson3.additional.service.HashService;
import crypto.lesson3.additional.service.KeyService;

import javax.crypto.Cipher;

/**
 * Допущение, что ключ находящийся в resources/lesson/key.AES есть у пользователей этого класса
 * <p>
 * Параметры запуска:
 * - первый аргумент - действие: зашифровать (флаг -e) / расшифровать (флаг -d)
 * - второй аргумент - данные для действия
 * - третий аргумент - хеш для проверки целостности данных
 *
 * Пример:
 * Зашифровать:
 *  -e Test_anything_else_так_просто
 *      В консоль выводится хеш и данные
 *
 * Дешифровать:
 *  -d mB8FGlF5nSzJ9Epv8m2dYQXS67RSWng8mSX7mRZbM6Ba+3vk8XT1vLGGJhLLIbCK NHysVcISErJNLsiRVJ9uhw==
 *      В консоль выведется расшифрованные данные
 *
 * Дешифровать с ошибкой контрольных сумм:
 *  -d mB8FGlF5nSzJ9Epv8m2dYQXS67RSWng8mSX7mRZbM6Ba+3vk8XT1vLGGJhLLIbCK NHysVcISErJNLsiRVJ1uhw==
 *      В консоль выведется ошибка "Контрольные суммы не совпадают"
 */
public class Lesson3Additional {
    public static void main(String[] args) throws Exception {
        CommandLineArgs commandLineArgs = CommandLineArgs.parse(args);
        getProcessing(commandLineArgs)
                .process(commandLineArgs)
                .then(System.out::println);
    }

    private static CipherCommandLineArgProcessing getProcessing(CommandLineArgs args) throws Exception {
        HashService hashService = new HashService();
        CipherService cipherService = new CipherService(new KeyService());
        Cipher cipher = cipherService.getCipher(args);
        return new CipherCommandLineArgProcessing(hashService, cipher);
    }
}
