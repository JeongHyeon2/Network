
import persistence.dao.BoardDAO;
import persistence.dto.BoardDTO;
import service.BoardService;
import view.BoardView;

import java.util.List;

public class DSConnection {
    public static void main(String[] args) {
        BoardDAO boardDAO = new BoardDAO();
        BoardView boardView= new BoardView();
        BoardService boardService = new BoardService(boardDAO);
        List<BoardDTO> all = boardService.findAll();
        boardView.printAll(all);
    }
}