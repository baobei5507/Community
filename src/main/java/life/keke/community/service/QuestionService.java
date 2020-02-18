package life.keke.community.service;


import life.keke.community.dto.PaginationDTO;
import life.keke.community.dto.QuestionDTO;
import life.keke.community.mapper.QuestionMapper;
import life.keke.community.mapper.UserMapper;
import life.keke.community.model.Question;
import life.keke.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {


        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();


        Integer offset=size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPaginationDTO(totalCount,page,size);

        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalCount=questionMapper.countByUserId(userId);

        Integer offset=size*(page-1);

        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOS=new ArrayList<>();

        for(Question question :questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOS);
        paginationDTO.setPaginationDTO(totalCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO findById(Integer id) {
        QuestionDTO questionDTO=new QuestionDTO();
        Question question= questionMapper.getById(id);
        questionDTO.setUser(userMapper.findById(question.getCreator()));
        BeanUtils.copyProperties(question,questionDTO);
        return  questionDTO;
    }
}
