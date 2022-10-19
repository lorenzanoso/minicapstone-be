package lorenz.example.minicapstone.repository;

import lorenz.example.minicapstone.entity.PopularEntity;
import lorenz.example.minicapstone.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.UUID;

public interface PopularRepository extends JpaRepository<PopularEntity, BigInteger> {
    PopularEntity findByProductId(UUID BlogId);

    @Transactional
    void deleteByProductId(UUID popularId);
}
