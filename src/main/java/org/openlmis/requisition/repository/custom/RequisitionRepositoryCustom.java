package org.openlmis.requisition.repository.custom;

import org.openlmis.requisition.domain.Requisition;
import org.openlmis.requisition.domain.RequisitionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface RequisitionRepositoryCustom {

  Page<Requisition> searchRequisitions(UUID facility, UUID program,
                                       ZonedDateTime initiatedDateFrom,
                                       ZonedDateTime initiatedDateTo,
                                       UUID processingPeriod,
                                       UUID supervisoryNode,
                                       RequisitionStatus[] requisitionStatuses,
                                       Boolean emergency,
                                       Pageable pageable);

  List<Requisition> searchByProcessingPeriodAndType(UUID processingPeriod, Boolean emergency);

  List<Requisition> searchApprovedRequisitions(String filterBy,
                                               List<UUID> desiredUuids);

  Requisition getLastRegularRequisition(UUID facility, UUID program);
}
