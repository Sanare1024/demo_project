package hello.demo_project.domain.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdOptionRepository extends JpaRepository<ProdOption, Long> {
    List<ProdOption> findByProduct_ProductId(@Param("productId") Long productId);
}
