package com.currency.teamflow.domain.card.service;

import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.boardlist.repository.BoardListRepository;
import com.currency.teamflow.domain.card.dto.CardRequestDto;
import com.currency.teamflow.domain.card.dto.CardResponseDto;
import com.currency.teamflow.domain.card.dto.CardSearchRequestDto;
import com.currency.teamflow.domain.card.dto.CardUpdateRequestDto;
import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.card.entity.CardManager;
import com.currency.teamflow.domain.card.repository.CardRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final BoardListRepository boardListRepository;

    private final CardManagerService cardManagerService;

    public CardService(CardRepository cardRepository,
                       BoardListRepository boardListRepository,
                       CardManagerService cardManagerService) {
        this.cardRepository = cardRepository;
        this.cardManagerService = cardManagerService;
        this.boardListRepository = boardListRepository;
    }

    /**
     * 카드 생성 서비스 메서드
     *
     * @param cardRequestDto 카드 내용
     * @return CardResponseDto
     */
    @Transactional
    public CardResponseDto createCard(CardRequestDto cardRequestDto) {

        // 카드 생성
        Card card = new Card(cardRequestDto.getCardTitle(), cardRequestDto.getCardExplanation(), cardRequestDto.getEndAt());

        // 카드 담당자 중간테이블 데이터 리스트 생성
        List<CardManager> cardManagers = cardManagerService.createCardManager(card, cardRequestDto.getUserIds());

        // 카드 담당자 등록
        card.updateCardManagers(cardManagers);

        // 리스트 저장
        BoardList boardList = boardListRepository.findByIdOrElseThrow(cardRequestDto.getListId());
        card.addBoardList(boardList);

        // 카드 저장
        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }

    /**
     * 카드 단건 조회 서비스 메서드
     *
     * @param cardId 카드 식별자
     * @return CardResponseDto
     */
    public CardResponseDto getCard(Long cardId) {

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        return CardResponseDto.toDto(card);
    }

    /**
     * 리스트 내의 카드 전체 조회 서비스 메서드
     *
     * @param listId 리스트 식별자
     * @return List<CardResponseDto>
     */
    public List<CardResponseDto> getCards(Long listId) {

        List<Card> cardList = cardRepository.findAllByBoardListId(listId);

        return cardList.stream().map(CardResponseDto::toDto).toList();
    }

    /**
     * 카드 단건 수정 서비스 메서드
     *
     * @param cardId 카드 식별자
     * @param cardUpdateRequestDto 수정할 카드 내용 dto
     * @return CardResponseDto
     */
    @Transactional
    public CardResponseDto updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto) {

        Card card = cardRepository.findByIdOrElseThrow(cardId);

        // 카드 담당자 변경
        List<CardManager> cardManagers = cardManagerService.updateCardManager(card, cardUpdateRequestDto.getUserIds());

        // 카드 내용 수정
        card.updateCard(cardUpdateRequestDto.getCardTitle(),
                cardUpdateRequestDto.getCardExplanation(),
                cardUpdateRequestDto.getEndAt(),
                cardManagers
                );

        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }


    /**
     * 카드 단건 삭제 서비스 메서드
     *
     * @param cardId 카드 식별자
     */
    public void deleteCard(Long cardId) {

        cardRepository.deleteById(cardId);
    }


    /**
     * 카드 조건 조회 서비스 메서드
     *
     * @param cardSearchRequestDto 조건 dto
     * @return List<CardResponseDto>
     */
    public List<CardResponseDto> getSearchCards(Pageable pageable, CardSearchRequestDto cardSearchRequestDto) {

        List<Card> cards = cardRepository.findAllSearchByConditions(pageable,
                cardSearchRequestDto.getBoardId(),
                cardSearchRequestDto.getCardTitle(),
                cardSearchRequestDto.getCardExplanation(),
                cardSearchRequestDto.getEndAt(),
                cardSearchRequestDto.getCardManagerName()
        );

        return cards.stream().map(CardResponseDto::toDto).toList();
    }
}
