package in.vruvesh.userManagement.repository;

import in.vruvesh.userManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT user.privilege FROM User user WHERE user.email= :email")
    String getPrivilegeByEmail(String email);
}
