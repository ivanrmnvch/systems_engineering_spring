package systems_engineering_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import systems_engineering_spring.entities.MainSection;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<MainSection, Long> {
  List<MainSection> findByActiveTrue();
  List<MainSection> findByActiveFalse();
}
