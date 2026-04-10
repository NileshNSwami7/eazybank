package com.eazybytes.card.service;

import com.eazybytes.card.dto.CardsDto;
import com.eazybytes.card.repository.CardsRepository;

public interface CardsService {

    void createCard(String mobileNumber);

    CardsDto fetchcardsDetials(String mobileNumber);

    boolean updatecardsDetails(CardsDto cardsDto);

    boolean deleteCardDetails(String mobileNumber);

}
