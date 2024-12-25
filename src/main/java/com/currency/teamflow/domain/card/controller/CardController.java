package com.currency.teamflow.domain.card.controller;

import com.currency.teamflow.domain.card.dto.CardRequestDto;
import com.currency.teamflow.domain.card.dto.CardResponseDto;
import com.currency.teamflow.domain.card.dto.CardUpdateRequestDto;
import com.currency.teamflow.domain.card.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        CardResponseDto cardResponseDto = cardService.createCard(cardRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponseDto);
    }

    /**
     * 리스트 내의 카드 전체 조회 API
     *
     * @param listId 리스트 식별자
     * @return ResponseEntity<List<CardResponseDto>> 리스트 내의 카드들 정보 전달
     */
    @GetMapping("list/{listId}/cards")
    public ResponseEntity<List<CardResponseDto>> getCards(@PathVariable Long listId) {

        List<CardResponseDto> cardResponseDtoList = cardService.getCards(listId);

        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDtoList);
    }

    /**
     * 카드 단건 조회 API
     *
     * @param cardId 카드 식별자
     * @return ResponseEntity<CardResponseDto>
     */
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long cardId) {

        CardResponseDto cardResponseDto = cardService.getCard(cardId);

        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDto);
    }

    /**
     * 카드 단건 수정 API
     *
     * @param cardId 카드 식별자
     * @param cardUpdateRequestDto 수정할 카드 내용 dto
     * @return ResponseEntity<CardResponseDto>
     */
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto cardUpdateRequestDto) {

        CardResponseDto cardResponseDto = cardService.updateCard(cardId, cardUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDto);
    }


    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {

        cardService.deleteCard(cardId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("카드 삭제가 완료되었습니다.");
    }
}
