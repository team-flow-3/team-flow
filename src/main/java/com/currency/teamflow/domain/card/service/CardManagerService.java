package com.currency.teamflow.domain.card.service;

import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.card.entity.CardManager;
import com.currency.teamflow.domain.card.repository.CardManagerRepository;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardManagerService {

    private final UserRepository userRepository;
    private final CardManagerRepository cardManagerRepository;

    public CardManagerService(UserRepository userRepository, CardManagerRepository cardManagerRepository) {
        this.userRepository = userRepository;
        this.cardManagerRepository = cardManagerRepository;
    }

    /**
     * 카드 담당자 데이터 생성 서비스 메서드
     *
     * @param card 카드 객체
     * @param userIds 유저 식별자 리스트
     * @return List<CardManager>
     */
    public List<CardManager> createCardManager(Card card, List<Long> userIds, Long workspaceId) {

        // 담당자 정보 가져오기
        List<User> users = userRepository.findAllById(userIds);

        // 카드 담당자 중간테이블 데이터 리스트 생성
        List<CardManager> cardManagers = new ArrayList<>();

        // 카드매니저 등록하기
        for (User user : users) {
            boolean isUserInWorkspace = user.getWorkspaceUsers().stream()
                    .anyMatch(wu -> wu.getWorkspace().getId().equals(workspaceId));

            if(!isUserInWorkspace) {
                throw new CustomException(ErrorCode.FORBIDDEN_PERMISSION);
            }

            CardManager cardManager = new CardManager(card, user);
            cardManagers.add(cardManager);

        }

        // 카드 담당자 중간 테이블 저장
        cardManagerRepository.saveAll(cardManagers);

        return cardManagers;
    }


    /**
     * 카드 담당자 데이터 수정 서비스 메서드
     *
     * @param card 카드 객체
     * @param userIds 유저 식별자 리스트
     * @return List<CardManager>
     */
    public List<CardManager> updateCardManager(Card card, List<Long> userIds, Long workspaceId) {

        // 기존 담당자 삭제
        cardManagerRepository.deleteAllByCardCardId(card.getCardId());

        // 새로 담당자 등록
        return createCardManager(card, userIds, workspaceId);
    }
}
