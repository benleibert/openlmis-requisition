/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.requisition.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "template_parameters")
@NoArgsConstructor
@AllArgsConstructor
public class JasperTemplateParameter extends BaseEntity {

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "templateId", nullable = false)
  @Getter
  @Setter
  private JasperTemplate template;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  @Getter
  @Setter
  private String name;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  @Getter
  @Setter
  private String displayName;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  @Getter
  @Setter
  private String defaultValue;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  @Getter
  @Setter
  private String dataType;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  @Getter
  @Setter
  private String selectSql;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  @Getter
  @Setter
  private String description;

  @Transient
  @Getter
  @Setter
  private List<String> selectValues = new ArrayList<>();

  /**
   * Create new instance of JasperTemplateParameter based on given
   * {@link JasperTemplateParameter.Importer}
   *
   * @param importer instance of {@link JasperTemplateParameter.Importer}
   * @return instance of JasperTemplateParameter.
   */
  public static JasperTemplateParameter newInstance(Importer importer) {
    JasperTemplateParameter jasperTemplateParameter = new JasperTemplateParameter();
    jasperTemplateParameter.setId(importer.getId());
    jasperTemplateParameter.setName(importer.getName());
    jasperTemplateParameter.setDisplayName(importer.getDisplayName());
    jasperTemplateParameter.setDefaultValue(importer.getDefaultValue());
    jasperTemplateParameter.setSelectSql(importer.getSelectSql());
    jasperTemplateParameter.setDescription(importer.getDescription());
    jasperTemplateParameter.setDataType(importer.getDataType());
    return jasperTemplateParameter;
  }

  /**
   * Export this object to the specified exporter (DTO).
   *
   * @param exporter exporter to export to
   */
  public void export(Exporter exporter) {
    exporter.setId(id);
    exporter.setName(name);
    exporter.setDescription(description);
    exporter.setDataType(dataType);
    exporter.setDefaultValue(defaultValue);
    exporter.setDisplayName(displayName);
    exporter.setSelectSql(selectSql);
    exporter.setSelectValues(selectValues);
  }

  public interface Exporter {
    void setId(UUID id);

    void setName(String name);

    void setDisplayName(String displayName);

    void setDefaultValue(String defaultValue);

    void setDataType(String dataType);

    void setSelectSql(String selectSql);

    void setDescription(String description);
    
    void setSelectValues(List<String> selectValues);

  }

  public interface Importer {
    UUID getId();

    String getName();

    String getDisplayName();

    String getDefaultValue();

    String getDataType();

    String getSelectSql();

    String getDescription();

  }
}
