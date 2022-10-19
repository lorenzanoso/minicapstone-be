package lorenz.example.minicapstone.repository;

import lorenz.example.minicapstone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.math.BigInteger;

public interface UserRepository extends JpaRepository<UserEntity, BigInteger> {
    UserEntity findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}