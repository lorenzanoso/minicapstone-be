package lorenz.example.minicapstone.repository;

import lorenz.example.minicapstone.entity.ProductEntity;
import lorenz.example.minicapstone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, BigInteger> {

    ProductEntity findByProductId(UUID productId);

    @Transactional
    void deleteByProductId(UUID productId);
}
