package life.keke.community.mapper;



import life.keke.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{account_id},#{token},#{gmtCreate},#{gmtModified})")
     void  saveUser(User user);
}
