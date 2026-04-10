package com.eazybytes.card.repository;

import com.eazybytes.card.dto.CardsDto;
import com.eazybytes.card.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.smartcardio.Card;
import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards,Long> {

    Optional<Cards> findByMobileNumber(String mobileNumber);
   Optional<Cards>findByCardNumber(String cardNumber);
}
