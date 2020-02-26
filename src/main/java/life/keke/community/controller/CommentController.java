package life.keke.community.controller;

import life.keke.community.dto.CommentCreateDTO;
import life.keke.community.dto.ResultDTO;
import life.keke.community.mapper.CommentMapper;
import life.keke.community.model.Comment;
import life.keke.community.model.User;
import life.keke.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static life.keke.community.exception.CustomizeErrorCode.NOT_LOGIN;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){


        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(NOT_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
