package systems_engineering_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import systems_engineering_spring.entities.SubSection;

@Repository
public interface SubSectionsRepository extends JpaRepository<SubSection, Long> {}
