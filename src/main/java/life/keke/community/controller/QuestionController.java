package life.keke.community.controller;


import life.keke.community.dto.CommentDTO;
import life.keke.community.dto.QuestionDTO;
import life.keke.community.enums.CommentTypeEnum;
import life.keke.community.service.CommentService;
import life.keke.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    String question(@PathVariable("id") Long id,
                    HttpServletRequest request,
                    Model model
    ){

        QuestionDTO questionDTO=questionService.findById(id);
        List<CommentDTO> commentDTOS=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOS);
        return "question";
    }
}
