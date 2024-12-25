package com.currency.teamflow.domain.boardlist.entity;

import com.currency.teamflow.domain.board.entity.Board;
import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
@Table(name = "board_list")
public class BoardList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;//보드 id(외래키)

    @NotNull
    private String listTitle;//리스트 제목

    @NotNull
    private Long array;//리스트 순서

    @OneToMany(mappedBy = "boardList")
    private List<Card> cards = new ArrayList<>();

    public BoardList() {
    }

    public BoardList(Board board, String listTitle) {
        updateBoard(board);
        this.listTitle = listTitle;
    }

    public void updateBoard(Board board){
        this.board = board;
        board.getBoardLists().add(this);
    }

    public void addArrayNumber(long array) {
        this.array = array;
    }
}
