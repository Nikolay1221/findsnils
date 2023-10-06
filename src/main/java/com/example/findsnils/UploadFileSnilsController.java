package com.example.findsnils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/findsnils")
public class UploadFileSnilsController {

    @Autowired
    private SnilsRepository snilsRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Выберите файл для загрузки.");
        }

        try (InputStream inputStream = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<String> snilsList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                snilsList.add(line.trim()); // Добавляем СНИЛС в список, удаляя лишние пробелы
            }

            // Проходим по каждому СНИЛСу из списка и выполняем проверку контрольной суммы и количества символов
            StringBuilder result = new StringBuilder();
            for (String snils : snilsList) {
                if (isValidSnils(snils)) {
                    Optional<SnilsEntity> snilsEntity = snilsRepository.findBySnils(snils);
                    if (snilsEntity.isPresent()) {
                        result.append("<li>СНИЛС: ").append(snils).append(" - найден в базе данных.</li>\n");
                    } else {
                        result.append("<li>СНИЛС: ").append(snils).append(" - не найден в базе данных.</li>\n");
                    }
                } else {
                    result.append("<li>СНИЛС: ").append(snils).append(" - невалидный СНИЛС.</li>\n");
                }
            }

            return ResponseEntity.ok(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при обработке файла.");
        }
    }

    private boolean isValidSnils(String snils) {
        // Удаление всех символов, кроме цифр
        snils = snils.replaceAll("[^0-9]", "");

        // Проверка длины СНИЛСа (должен быть ровно 11 символов)
        if (snils.length() != 11) {
            return false;
        }

        // Вычисление контрольной суммы
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(snils.charAt(i));
            sum += digit * (9 - i);
        }

        int expectedCheckDigits = Integer.parseInt(snils.substring(9));
        int calculatedCheckDigits = sum < 100 ? sum : sum % 101;

        // Проверка контрольных цифр
        return expectedCheckDigits == calculatedCheckDigits;
    }

}

