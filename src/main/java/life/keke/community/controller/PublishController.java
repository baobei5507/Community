package life.keke.community.controller;


import life.keke.community.cache.TagCache;
import life.keke.community.dto.QuestionDTO;
import life.keke.community.mapper.QuestionMapper;
import life.keke.community.mapper.UserMapper;
import life.keke.community.model.Question;
import life.keke.community.model.User;
import life.keke.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,
                   Model model
                   ){
        QuestionDTO question=questionService.findById(id);


        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        model.addAttribute("tags", TagCache.get());

        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model
    ){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @PostMapping("/publish")
    public String dopublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Long id,
            HttpServletRequest request,
            Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        model.addAttribute("tags", TagCache.get());

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


        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签："+invalid);
            return "publish";
        }


        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
