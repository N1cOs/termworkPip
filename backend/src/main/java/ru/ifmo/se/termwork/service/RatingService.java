package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.RatingsDto;

public interface RatingService {

    RatingsDto getRatings(int specialityId);
}
