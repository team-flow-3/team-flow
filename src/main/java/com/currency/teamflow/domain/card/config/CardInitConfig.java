package com.currency.teamflow.domain.card.config;

import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.board.repository.BoardRepository;
import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.boardlist.repository.BoardListRepository;
import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.card.repository.CardRepository;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardInitConfig {

    private final String[] array1 = {"바나나", "사과", "귤", "포도", "키위", "멜론", "두리안", "배", "감", "망고"};
    private final String[] array2 = {"하나", "둘", "셋", "넷", "다섯", "여섯", "일곱", "여덟", "아홉", "열"};
    private final String[] colors = {"빨강", "주황", "노랑", "초록", "파랑", "남색", "보라", "검정", "흰색", "회색"};

    private final WorkspaceRepository workspaceRepository;
    private final BoardRepository boardRepository;
    private final BoardListRepository boardListRepository;
    private final CardRepository cardRepository;


    public CardInitConfig(WorkspaceRepository workspaceRepository, BoardRepository boardRepository, BoardListRepository boardListRepository, CardRepository cardRepository) {
        this.workspaceRepository = workspaceRepository;
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
        this.cardRepository = cardRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {

        // 워크스페이스 생성
        Workspace workspace = workspaceRepository.save(new Workspace("워크스페이스1", "환영"));

        // 리스트 생성
        List<Board> boards = new ArrayList<>();
        for (int i=0; i<1000; i++){
            for (int j=0; j<10; j++){
                Board board = new Board(workspace, "보드", "#000000", "image.jpg");
                boards.add(board);
            }

        }
        boardRepository.saveAll(boards);


        // 리스트 생성
        List<BoardList> boardLists = new ArrayList<>();
        for (int i=0; i<100; i++){
            for (int j=0; j<10; j++){
                BoardList boardList = new BoardList(boards.get(i*10+j), "리스트"+i+array1[j]);
                boardList.addArrayNumber(i);
                boardLists.add(boardList);
            }

        }
        boardListRepository.saveAll(boardLists);

        // 카드 생성
        List<Card> cardList = new ArrayList<>();
        for(int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                for(int k=0; k<100; k++){
                    for (int j2=0; j2<10; j2++){
                        // 카드 생성
                        Card card = new Card("제목 "+ array1[i] + array2[j]+colors[j2],
                                "내용 "+ array1[i] + array2[j],
                                LocalDate.parse("2024-01-01").plusYears(i).plusMonths(j).plusDays(j2)
                        );
                        card.addBoardList(boardLists.get(k*10+j2));

                        // 카드 리스트에 추가
                        cardList.add(card);
                    }
                }

            }
        }
        cardRepository.saveAll(cardList);
    }

}
