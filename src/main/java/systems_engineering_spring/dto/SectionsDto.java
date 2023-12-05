package systems_engineering_spring.dto;

import lombok.Data;
import systems_engineering_spring.entities.MainSection;
import systems_engineering_spring.entities.SubSection;

import java.util.List;

@Data
public class SectionsDto {
  private List<MainSection> mainSections;
  private List<SubSection> subSections;
  public SectionsDto(List<MainSection> mainSections, List<SubSection> subSections) {
    this.mainSections = mainSections;
    this.subSections = subSections;
  }
}
