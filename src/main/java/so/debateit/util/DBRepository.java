/**
 * The DBRepository class for save the User Info
 *
 * @author  Jinyoung So
 * @version 1.1
 * @since   5/02/2022
 *
 * This class is for the test of JPA and will be deleted
 */
package so.debateit.util;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import so.debateit.common.model.User;

import javax.sql.DataSource;

@Component
public class DBRepository
{
    private static final Log logger = LogFactory.getLog(DBRepository.class);


    @Autowired
    private SessionFactory sessionFactory;


    public User insertUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);

        return user;
    }


    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public  void setDatasource(DataSource datasource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }


    public User saveUser(User user) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getUserId());
        params.addValue("userPass", user.getUserPass());
        params.addValue("cretDate", user.getCretDate());

        String insertSQL = "INSERT INTO user_info (user_nmbr, user_id, user_pass, cret_date) VALUES (null, :userId, :userPass, :cretDate)";

        try {
            this.jdbcTemplate.update(insertSQL, params, holder);
        } catch (Exception ex) {
            logger.error("INSERT ERROR!");
            return null;
        }

        return user;
    }




}
