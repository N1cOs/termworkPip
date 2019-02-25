package ru.ifmo.se.termwork.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.ifmo.se.termwork.domain.Worker;
import ru.ifmo.se.termwork.dto.WorkerDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkerMapper {

    Worker toWorker(WorkerDto workerDto);
}
