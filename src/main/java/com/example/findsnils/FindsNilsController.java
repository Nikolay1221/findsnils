package com.example.findsnils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FindsNilsController {

    @GetMapping("/findsnils")
    public String showFindsNilsPage() {
        // Здесь можно добавить логику для отображения страницы или просто вернуть имя шаблона
        return "findsnils"; // Предположим, что у вас есть шаблон с именем "findsnils.html"
    }
}
