package life.keke.community.service;


import life.keke.community.dto.CommentDTO;
import life.keke.community.dto.PaginationDTO;
import life.keke.community.dto.QuestionDTO;
import life.keke.community.enums.CommentTypeEnum;
import life.keke.community.exception.CustomizeErrorCode;
import life.keke.community.exception.CustomizeException;
import life.keke.community.mapper.CommentMapper;
import life.keke.community.mapper.QuestionExtMapper;
import life.keke.community.mapper.QuestionMapper;
import life.keke.community.mapper.UserMapper;
import life.keke.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentMapper commentMapper;

    public PaginationDTO list(Integer page, Integer size) {


        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount =(int) questionMapper.countByExample(new QuestionExample());


        Integer offset=size*(page-1);

        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPaginationDTO(totalCount,page,size);

        return paginationDTO;
    }

    public PaginationDTO listByUserId(Long userId, Integer page, Integer size) {

        PaginationDTO paginationDTO=new PaginationDTO();
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount =(int) questionMapper.countByExample(example);


        Integer offset=size*(page-1);

        QuestionExample example1 = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example1, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOS=new ArrayList<>();

        for(Question question :questions){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOS);
        paginationDTO.setPaginationDTO(totalCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO findById(Long id) {
        QuestionDTO questionDTO=new QuestionDTO();
        Question question= questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.Question_NOT_FOUND);
        }
        questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
        BeanUtils.copyProperties(question,questionDTO);
        return  questionDTO;
    }

    public void createOrUpdate(Question question) {

        if( question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            Question updateQuestion = question;
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated !=1 ){
                throw new CustomizeException(CustomizeErrorCode.Question_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {

        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incView(record);
    }


    public List<CommentDTO> listByQuestionId(Long id) {
        CommentExample example = new CommentExample();
        example.createCriteria().
                andParentIdEqualTo(id).
                andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(example);

        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人  Set为去重
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> usersIds=new ArrayList<>();
        usersIds.addAll(commentators);

        //获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(usersIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为CommentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
