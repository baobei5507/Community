package life.keke.community.mapper;

import life.keke.community.model.Comment;
import life.keke.community.model.CommentExample;
import life.keke.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}