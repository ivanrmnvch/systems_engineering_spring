package systems_engineering_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import systems_engineering_spring.dto.SectionDto;
import systems_engineering_spring.dto.SectionsDto;
import systems_engineering_spring.entities.MainSection;
import systems_engineering_spring.entities.SubSection;
import systems_engineering_spring.exceptions.Resp;
import systems_engineering_spring.repository.SectionRepository;
import systems_engineering_spring.repository.SubSectionsRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectionService {
  private final SectionRepository sectionRepository;
  private final SubSectionsRepository subSectionsRepository;

  public SectionsDto getSections(boolean active) {
    List<MainSection> mainSections;
    if (active) {
      mainSections = sectionRepository.findByActiveTrue();
    } else {
      mainSections = sectionRepository.findByActiveFalse();
    }

    List<SubSection> subSections = subSectionsRepository.findAll();
    return new SectionsDto(
      mainSections,
      subSections
    );
  }

  public ResponseEntity<?> updateSection(Long id, SectionDto sectionDto) {
    String type = sectionDto.getType();
    if (Objects.equals(type, "main")) {
      Optional<MainSection> mainSection = getMainSectionById(id);
      if (mainSection.isEmpty()) {
        return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Такого раздела не существует"), HttpStatus.BAD_REQUEST);
      }

      MainSection section = mainSection.get();
      section.setName(sectionDto.getName());
      section.setActive(sectionDto.getActive());
      section.setUpdatedAt(new Date());

      sectionRepository.save(section);
      return ResponseEntity.ok(new Resp(HttpStatus.OK.value(), "Раздел успешно обновлен"));
    }
    if (Objects.equals(type, "sub")) {
      Optional<SubSection> subSection = getSubSectionById(id);
      if (subSection.isEmpty()) {
        return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Такого подраздела не существует"), HttpStatus.BAD_REQUEST);
      }

      SubSection section = subSection.get();
      section.setName(sectionDto.getName());
      section.setActive(sectionDto.getActive());
      section.setUpdatedAt(new Date());

      subSectionsRepository.save(section);
      return ResponseEntity.ok(new Resp(HttpStatus.OK.value(), "Подраздел успешно обновлен"));
    }
    return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Ошибка данных"), HttpStatus.BAD_REQUEST);
  }
  public ResponseEntity<?> createSection(SectionDto sectionDto) {
    String type = sectionDto.getType();
    String name = sectionDto.getName();

    if (name.isEmpty() && type.isEmpty()) {
      return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Ошибка данных"), HttpStatus.BAD_REQUEST);
    }

    if (Objects.equals(type, "main")) {
      MainSection mainSection = new MainSection();
      mainSection.setName(name);
      mainSection.setActive(sectionDto.getActive());
      mainSection.setCreatedAt(new Date());
      mainSection.setUpdatedAt(new Date());
      sectionRepository.save(mainSection);
      return new ResponseEntity<>(new Resp(HttpStatus.CREATED.value(), "Раздел успешно создан"), HttpStatus.CREATED);
    }

    if (Objects.equals(type, "sub")) {
      Optional<MainSection> mainSection = getMainSectionById(sectionDto.getMainSectionId());
      if (mainSection.isEmpty()) {
        return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Ошибка данных"), HttpStatus.BAD_REQUEST);
      }
      SubSection subSection = new SubSection();
      subSection.setName(name);
      subSection.setActive(sectionDto.getActive());
      subSection.setMainSection(mainSection.get());
      subSection.setCreatedAt(new Date());
      subSection.setUpdatedAt(new Date());
      subSectionsRepository.save(subSection);
      return new ResponseEntity<>(new Resp(HttpStatus.CREATED.value(), "Подраздел успешно создан"), HttpStatus.CREATED);
    }
    return new ResponseEntity<>(new Resp(HttpStatus.BAD_REQUEST.value(), "Ошибка данных"), HttpStatus.BAD_REQUEST);
  }

  public Optional<MainSection> getMainSectionById(Long id) {
    return sectionRepository.findById(id);
  }

  public Optional<SubSection> getSubSectionById(Long id) {
   return subSectionsRepository.findById(id);
  }
}
