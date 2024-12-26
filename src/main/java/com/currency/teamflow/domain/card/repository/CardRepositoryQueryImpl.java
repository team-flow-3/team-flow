package com.currency.teamflow.domain.card.repository;

import com.currency.teamflow.domain.card.entity.Card;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static com.currency.teamflow.domain.card.entity.QCard.card;
import static com.currency.teamflow.domain.card.entity.QCardManager.cardManager;
import static com.currency.teamflow.domain.user.entity.QUser.user;

public class CardRepositoryQueryImpl implements CardRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    public CardRepositoryQueryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Card> findAllSearchByConditions(Pageable pageable, Long boardId, String cardTitle, String cardExplanation, String endAt, String cardMangerName) {
        return queryFactory.selectFrom(card)
                .leftJoin(card.cardManagers, cardManager)
                .fetchJoin()
                .leftJoin(cardManager.user, user)
                .fetchJoin()
                .where(
                        boardIdEq(boardId),
                        cardTitleEq(cardTitle),
                        cardExplanationEq(cardExplanation),
                        endAtEq(endAt),
                        cardManagerNicknameEq(cardMangerName)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(card.modifiedAt.desc())
                .fetch();

    }


    private BooleanExpression boardIdEq(Long boardId) {
        return boardId != null ? card.boardList.board.id.eq(boardId) : null;
    }

    private BooleanExpression cardTitleEq(String cardTitle) {
        return cardTitle != null ? card.cardTitle.contains(cardTitle) : null;
    }

    private BooleanExpression cardExplanationEq(String cardExplanation) {
        return cardExplanation != null ? card.cardExplanation.contains(cardExplanation) : null;
    }

    private BooleanExpression endAtEq(String endAt) {
        if (endAt == null) {
            return null;
        }
        LocalDate ConvertedEndAt = LocalDate.parse(endAt);
        return card.endAt.eq(ConvertedEndAt);
    }

    private BooleanExpression cardManagerNicknameEq(String userNickname) {
        return userNickname != null ? user.nickName.eq(userNickname) : null;
    }
}
