package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.ClaimDto;

public interface ApplyingService {

    void applyForSpeciality(int studentId, ClaimDto claimDto);

    void cancelApplication(int studentId, int specialityId);
}
