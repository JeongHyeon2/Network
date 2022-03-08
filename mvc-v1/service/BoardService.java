package service;

import persistence.dao.BoardDAO;
import persistence.dto.BoardDTO;

import java.util.List;

public class BoardService {
    private final BoardDAO boardDAO;

    public BoardService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }
    public List<BoardDTO> findAll(){
        List<BoardDTO> all = boardDAO.findAll();
        return all;
    }
}
