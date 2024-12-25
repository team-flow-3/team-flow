package com.currency.teamflow.domain.card.controller;

import com.currency.teamflow.domain.card.dto.CardRequestDto;
import com.currency.teamflow.domain.card.dto.CardResponseDto;
import com.currency.teamflow.domain.card.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * 카드 생성 API
     *
     * @param cardRequestDto 생성할 카드 정보 dto
     * @return ResponseEntity<CardResponseDto> 저장된 카드 정보 전달
     *
     */
    @PostMapping("/cards")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardRequestDto cardRequestDto) {

        CardResponseDto cardResponseDto = cardService.createCard(cardRequestDto.getCardTitle(),
                cardRequestDto.getCardExplanation(),
                cardRequestDto.getEndAt(),
                cardRequestDto.getUserIds()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponseDto);
    }
}
