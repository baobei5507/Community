package life.keke.community.controller;


import life.keke.community.dto.NotificationDTO;
import life.keke.community.dto.PaginationDTO;
import life.keke.community.mapper.UserMapper;
import life.keke.community.model.Notification;
import life.keke.community.model.User;
import life.keke.community.service.NotificationService;
import life.keke.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}" )
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size
    ) {


        User user = (User) request.getSession().getAttribute("user");

        if(user==null){
            return "redirect:/";
        }

        if (action.equals("questions")) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            PaginationDTO paginationDTO = questionService.listByUserId(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }
        if (action.equals("replies")) {

            PaginationDTO paginationDTO=notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("sectionName", "最新回复");
        }


        return "profile";
    }
}
