package com.currency.teamflow.domain.card.service;

import com.currency.teamflow.domain.card.dto.CardResponseDto;
import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.card.entity.CardManager;
import com.currency.teamflow.domain.card.repository.CardManagerRepository;
import com.currency.teamflow.domain.card.repository.CardRepository;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardManagerRepository cardManagerRepository;

    public CardService(CardRepository cardRepository,
                       UserRepository userRepository,
                       CardManagerRepository cardManagerRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.cardManagerRepository = cardManagerRepository;
    }

    /**
     * 카드 생성 서비스 메서드
     *
     * @param cardTitle 카드 제목
     * @param cardExplanation 카드 내용
     * @param endAt 마감일
     * @param userIds 담당자
     * @return CardResponseDto
     */
    @Transactional
    public CardResponseDto createCard(String cardTitle, String cardExplanation, LocalDateTime endAt, List<Long> userIds) {

        // 카드 생성
        Card card = new Card(cardTitle, cardExplanation, endAt);

        // 담당자 정보 가져오기
        List<User> users = userRepository.findAllById(userIds);

        // 카드 담당자 중간테이블 데이터 리스트 생성
        List<CardManager> cardManagers = new ArrayList<>();

        // 카드매니저 등록하기
        for (User user : users) {
            CardManager cardManager = new CardManager(card, user);
            cardManagers.add(cardManager);
        }

        // 카드 담당자 중간 테이블 저장
        cardManagerRepository.saveAll(cardManagers);
        card.addCardManagers(cardManagers);

        // 카드 저장
        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }
}
