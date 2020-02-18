package life.keke.community.controller;


import life.keke.community.mapper.QuestionMapper;
import life.keke.community.mapper.UserMapper;
import life.keke.community.model.Question;
import life.keke.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class PublishController {

    @GetMapping("/publish")
    String publish(){
        return "publish";
    }


    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;



    @PostMapping("/publish")
    String dopublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);



        User user = (User) request.getSession().getAttribute("user");

            if(user == null) {
                model.addAttribute("error", "当前用户失效，请重新登录");
                return "publish";
            }


        if(user == null){
            model.addAttribute("error","当前用户失效，请重新登录");
            return "publish";
        }


        if(title==null || title==""){
            model.addAttribute("error","请输入标题");
            return "publish";
        }

        if(description==null || description==""){
            model.addAttribute("error","请输入主要内容");
            return "publish";
        }

        if(tag==null || tag=="" ){
            model.addAttribute("error","请输入标签");
            return "publish";
        }





        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);

        return "redirect:/";
    }
}
