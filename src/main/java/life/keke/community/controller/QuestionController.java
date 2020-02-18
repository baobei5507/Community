package life.keke.community.controller;


import life.keke.community.dto.QuestionDTO;
import life.keke.community.model.User;
import life.keke.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    String question(@PathVariable("id") Integer id,
                    HttpServletRequest request,
                    Model model
    ){
        QuestionDTO questionDTO=questionService.findById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
