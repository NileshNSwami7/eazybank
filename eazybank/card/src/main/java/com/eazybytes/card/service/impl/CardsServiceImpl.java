package com.eazybytes.card.service.impl;

import com.eazybytes.card.constants.CardsConstants;
import com.eazybytes.card.dto.CardsDto;
import com.eazybytes.card.entity.Cards;
import com.eazybytes.card.exception.CardAlreadyExistsException;
import com.eazybytes.card.repository.CardsRepository;
import com.eazybytes.card.service.CardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements CardsService {

    private CardsRepository cardsRepository;
    public void createCard(String mobileNumber){
        Optional<Cards> optional = cardsRepository.findByMobileNumber(mobileNumber);
        if(optional.isPresent()){
            throw new CardAlreadyExistsException("Card  already registered with given mobile number"+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber){
        Cards newcard = new Cards();
        long randomCardNumber = 1000000000000L + new Random().nextInt(900000000);
        newcard.setCardNumber(Long.toString(randomCardNumber));
        newcard.setMobileNumber(mobileNumber);
        newcard.setCardType(CardsConstants.CREDIT_CARD);
        newcard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newcard.setAmountUsed(0);
        newcard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return  newcard;
    }
}
