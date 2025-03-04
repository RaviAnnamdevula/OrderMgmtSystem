
import com.jocata.oms.data.um.dao.UserDao;
import com.jocata.oms.data.um.dao.dapImpl.UserDaoImpl;
import com.jocata.oms.datamodel.um.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {

    private UserDao userDao;

    @BeforeAll
    void setup() {
        userDao = new UserDaoImpl();
    }

    @Test
    void testSaveUser() {
        UserEntity user = UserEntity.builder()
                .fullName("Suresh Sanem")
                .email("suresh.sanem@gmail.com")
                .passwordHash("hashed_password")
                .phone("7386782589")
                .profilePicture("9845nw8rr2n3rr2")
                .otpSecret("9878")
                .isActive(true)
                .smsEnabled(false)
                .build();

        userDao.saveUser(user);

        UserEntity retrievedUser = userDao.getUserById(user.getUserId());
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("suresh.sanem@gmail.com", retrievedUser.getEmail());
    }

    @Test
    void testGetAllUsers() {
        List<UserEntity> users = userDao.getAllUsers();
        Assertions.assertFalse(users.isEmpty());
    }
}
