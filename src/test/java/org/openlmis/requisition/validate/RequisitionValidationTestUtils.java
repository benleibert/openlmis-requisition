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

package org.openlmis.requisition.validate;

import org.openlmis.requisition.domain.AvailableRequisitionColumn;
import org.openlmis.requisition.domain.AvailableRequisitionColumnOption;
import org.openlmis.requisition.domain.RequisitionLineItem;
import org.openlmis.requisition.domain.RequisitionTemplateColumn;
import org.openlmis.requisition.domain.SourceType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequisitionValidationTestUtils {

  static Map<String, RequisitionTemplateColumn> initiateColumns() {
    Map<String, RequisitionTemplateColumn> columns = new HashMap<>();
    columns.put(RequisitionLineItem.REQUESTED_QUANTITY,
        generateTemplateColumn(RequisitionLineItem.REQUESTED_QUANTITY,
            SourceType.USER_INPUT, "J"));
    columns.put(RequisitionLineItem.TOTAL_RECEIVED_QUANTITY,
        generateTemplateColumn(RequisitionLineItem.TOTAL_RECEIVED_QUANTITY,
            SourceType.USER_INPUT, "B"));
    RequisitionTemplateColumn stockOnHandColumn =
        generateTemplateColumn(RequisitionLineItem.STOCK_ON_HAND, SourceType.USER_INPUT, "E");
    stockOnHandColumn.getColumnDefinition().getSources().add(SourceType.CALCULATED);
    columns.put(RequisitionLineItem.STOCK_ON_HAND, stockOnHandColumn);
    columns.put(RequisitionLineItem.BEGINNING_BALANCE,
        generateTemplateColumn(RequisitionLineItem.BEGINNING_BALANCE, SourceType.USER_INPUT, "A"));
    columns.put(RequisitionLineItem.REQUESTED_QUANTITY_EXPLANATION,
        generateTemplateColumn(RequisitionLineItem.REQUESTED_QUANTITY_EXPLANATION,
            SourceType.USER_INPUT, "W"));
    columns.put(RequisitionLineItem.TOTAL_CONSUMED_QUANTITY,
        generateTemplateColumn(RequisitionLineItem.TOTAL_CONSUMED_QUANTITY,
            SourceType.USER_INPUT, "C"));
    columns.put(RequisitionLineItem.TOTAL_LOSSES_AND_ADJUSTMENTS,
        generateTemplateColumn(RequisitionLineItem.TOTAL_LOSSES_AND_ADJUSTMENTS,
            SourceType.USER_INPUT, "D"));
    columns.put(RequisitionLineItem.APPROVED_QUANTITY,
        generateTemplateColumn(RequisitionLineItem.APPROVED_QUANTITY,
            SourceType.USER_INPUT, "K"));
    columns.put(RequisitionLineItem.TOTAL_STOCKOUT_DAYS,
        generateTemplateColumn(RequisitionLineItem.TOTAL_STOCKOUT_DAYS,
            SourceType.USER_INPUT, "X"));
    columns.put(RequisitionLineItem.TOTAL_COLUMN,
        generateTemplateColumn(RequisitionLineItem.TOTAL_COLUMN,
            SourceType.CALCULATED, "Y"));
    columns.put(RequisitionLineItem.NUMBER_OF_NEW_PATIENTS_ADDED,
        generateTemplateColumn(RequisitionLineItem.NUMBER_OF_NEW_PATIENTS_ADDED,
            SourceType.USER_INPUT, "F"));
    columns.put(RequisitionLineItem.SKIPPED_COLUMN,
        generateTemplateColumn(RequisitionLineItem.SKIPPED_COLUMN,
            SourceType.USER_INPUT, "S"));

    RequisitionTemplateColumn maximumStockQuantity = generateTemplateColumn(
        RequisitionLineItem.MAXIMUM_STOCK_QUANTITY, SourceType.CALCULATED, "H");
    maximumStockQuantity.setOption(new AvailableRequisitionColumnOption(maximumStockQuantity
        .getColumnDefinition(), "default", "Default"));

    columns.put(RequisitionLineItem.MAXIMUM_STOCK_QUANTITY, maximumStockQuantity);

    columns.put(RequisitionLineItem.CALCULATED_ORDER_QUANTITY,
        generateTemplateColumn(RequisitionLineItem.CALCULATED_ORDER_QUANTITY,
            SourceType.CALCULATED, "I"));
    return columns;
  }

  private static RequisitionTemplateColumn generateTemplateColumn(
      String name, SourceType sourceType, String indicator) {
    AvailableRequisitionColumn columnDefinition = new AvailableRequisitionColumn();
    columnDefinition.setName(name);
    columnDefinition.setIndicator(indicator);
    Set<SourceType> sources = new HashSet<>();
    sources.add(sourceType);
    columnDefinition.setSources(sources);

    RequisitionTemplateColumn requisitionTemplateColumn =
        new RequisitionTemplateColumn(columnDefinition);
    requisitionTemplateColumn.setName(name);
    requisitionTemplateColumn.setIsDisplayed(true);
    return requisitionTemplateColumn;
  }
}
