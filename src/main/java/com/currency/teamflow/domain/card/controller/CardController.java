package com.currency.teamflow.domain.card.controller;

import com.currency.teamflow.domain.card.dto.CardRequestDto;
import com.currency.teamflow.domain.card.dto.CardResponseDto;
import com.currency.teamflow.domain.card.dto.CardSearchRequestDto;
import com.currency.teamflow.domain.card.dto.CardUpdateRequestDto;
import com.currency.teamflow.domain.card.service.CardService;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.global.annotation.CheckMemberRole;
import com.currency.teamflow.global.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
    @PostMapping("/cards")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardRequestDto cardRequestDto,
                                                      HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        WorkspaceUser workspaceUser = (WorkspaceUser) session.getAttribute("workspaceUser");

        CardResponseDto cardResponseDto = cardService.createCard(cardRequestDto, workspaceUser.getWorkspace().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponseDto);
    }

    /**
     * 리스트 내의 카드 전체 조회 API
     *
     * @param boardListId 리스트 식별자
     * @return ResponseEntity<List<CardResponseDto>> 리스트 내의 카드들 정보 전달
     */
    @GetMapping("/list/{boardListId}/cards")
    public ResponseEntity<List<CardResponseDto>> getCards(@PathVariable Long boardListId) {

        List<CardResponseDto> cardResponseDtoList = cardService.getCards(boardListId);

        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDtoList);
    }

    /**
     * 카드 전체 조건 조회 API
     *
     * @param cardSearchRequestDto 조건 내용 dto
     * @return ResponseEntity<List<CardResponseDto>>
     */
    @GetMapping("/cards")
    public ResponseEntity<List<CardResponseDto>> getSearchCards(@PageableDefault Pageable pageable, @ModelAttribute CardSearchRequestDto cardSearchRequestDto) {

        List<CardResponseDto> cardResponseDtoList = cardService.getSearchCards(pageable, cardSearchRequestDto);

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
    @CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId,
                                                      @RequestBody CardUpdateRequestDto cardUpdateRequestDto,
                                                      HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        WorkspaceUser workspaceUser = (WorkspaceUser) session.getAttribute("workspaceUser");

        CardResponseDto cardResponseDto = cardService.updateCard(
                cardId,
                cardUpdateRequestDto,
                workspaceUser.getWorkspace().getId()
        );

        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDto);
    }


    /**
     * 카드 단건 삭제 API
     *
     * @param cardId 카드 식별자
     * @return ResponseEntity<String>
     */
    @CheckMemberRole(requiredRoles = {Role.WORKSPACE_ADMIN, Role.BOARD_USER})
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {

        cardService.deleteCard(cardId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("카드 삭제가 완료되었습니다.");
    }
}
