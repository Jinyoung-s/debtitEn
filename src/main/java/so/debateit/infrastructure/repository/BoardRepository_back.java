package so.debateit.infrastructure.repository;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.DateUtils;
import so.debateit.domain.model.board.Board;
import so.debateit.domain.model.user.SessionUser;

import javax.persistence.EntityManager;
import java.util.List;

/*
 * Hibernate repository about User
 */
@Repository
public class BoardRepository_back extends HibernateSupport {
    public BoardRepository_back(EntityManager entityManager) {
        super(entityManager);
    }

    public Board findByBoardId(String boardId) {
        Query<Board> query = getSession().createQuery("from Board where boadNmbr = :boardId", Board.class);
        query.setParameter("boardId", boardId);
        return query.uniqueResult();
    }

    public Board writeBoard(Board board, SessionUser session) {
        board.setCretDate(DateUtils.createNow().getTime());
        board.getFirstUser().setUserNmbr(session.getUserNmbr());
        save(board);
        return board;
    }

    public List<Board> getPopularBoard() {
        Query<Board> query = getSession().createQuery("from Board", Board.class);
        return query.getResultList();
    }

}
