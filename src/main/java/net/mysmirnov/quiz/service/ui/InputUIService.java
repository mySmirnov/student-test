package net.mysmirnov.quiz.service.ui;

import java.util.Optional;

public interface InputUIService {
    /**
     * Возвращает непустой Optional если пользователь ввел непустую строку
     * Иначе возвращает Optional.empty
     */
    Optional<String> read();

    /**
     * Проверяет не завершил ли пользователь ввод, попытавшись выйти из приложения
     */
    boolean hasNextLine();
}
