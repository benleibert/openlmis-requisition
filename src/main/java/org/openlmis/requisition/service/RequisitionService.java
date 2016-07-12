package org.openlmis.requisition.service;

import org.openlmis.requisition.domain.Requisition;
import org.openlmis.requisition.domain.RequisitionStatus;
import org.openlmis.requisition.repository.RequisitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequisitionService {

  Logger logger = LoggerFactory.getLogger(RequisitionService.class);

  @Autowired
  RequisitionRepository requisitionRepository;

  public boolean skip(UUID requisitionId) {
    Requisition requisition = requisitionRepository.findOne(requisitionId);
    if (requisition == null) {
      logger.debug("Skip failed - requisition cannot be null");
    } else if (!requisition.getStatus().equals(RequisitionStatus.INITIATED)) {
      logger.debug("Skip failed - requisition has bad status");
    } else if (!requisition.getProgram().getSkippable()) {
      logger.debug("Skip failed - requisition program does not allow skipping");
    } else {
      logger.debug("Requisition skipped");
      requisition.setStatus(RequisitionStatus.SKIPPED);
      requisitionRepository.save(requisition);
      return true;
    }
    return false;
  }

  public boolean reject(UUID requisitionId) {

    Requisition requisition = requisitionRepository.findOne(requisitionId);
    if (requisition == null) {
      logger.debug("Reject failed - requisition cannot be null");
    } else if (!requisition.getStatus().equals(RequisitionStatus.AUTHORIZED)) {
      logger.debug("Reject failed - requisition must waiting for approve to be rejected");
    } else {
      logger.debug("Requisition rejected");
      requisition.setStatus(RequisitionStatus.INITIATED);
      requisitionRepository.save(requisition);
      return true;
    }
    return false;
  }

}