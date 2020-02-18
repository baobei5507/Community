package life.keke.community.mapper;



import life.keke.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url,bio) values(#{name},#{account_id},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio})")
     void  saveUser(User user);

    @Select("Select * from user where token=#{token}")
    User  findBytoken(@Param("token") String token);

    @Select("Select * from user where id=#{id}")
    User findById(@Param("id") Integer id);



}
