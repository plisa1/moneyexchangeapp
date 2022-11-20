package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests{
    private static final User USER_1 = new User(1, "user1", "user1", "ROLE_USER");
    protected static final Transfer TRANSFER_1 = new Transfer(100, 2, 2, 2001, 2000, BigDecimal.valueOf(1));
    protected static final Transfer TRANSFER_2 = new Transfer(200, 2, 2, 3001, 3002, BigDecimal.valueOf(1));
    protected static final Transfer TRANSFER_3 = new Transfer(300, 2, 2, 3002, 3000, BigDecimal.valueOf(10));

    private JdbcTransferDao dao;
    private JdbcAccountDao accDao;
    private JdbcUserDao userDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferDao(jdbcTemplate);
        accDao = new JdbcAccountDao(jdbcTemplate);
        userDao = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void findAllTransfers_returns_all() {
        List<Transfer> transfer = dao.findAll();

        Assert.assertNotNull(transfer);
//        Assert.assertEquals(3, transfer.size());
//        Assert.assertEquals(TRANSFER_1, transfer.get(0));
//        Assert.assertEquals(TRANSFER_2, transfer.get(1));
//        Assert.assertEquals(TRANSFER_3, transfer.get(2));
    }

    @Test
    public void create_transfer_creates_a_transfer() {
        Transfer newTransfer = new Transfer(1, 2, 2, 1, 2, BigDecimal.valueOf(50));

        dao.create(newTransfer);

        Transfer transfer = dao.getTransferById(3001);

        accDao.updateBalances(newTransfer.getAcct_from(), newTransfer.getAmount().negate());
        accDao.updateBalances(newTransfer.getAcct_to(), (newTransfer.getAmount()));

        // Test Transfer row created in db
        Assert.assertNotNull(transfer);

        // user 1 send 50 to user 2
        Assert.assertEquals(BigDecimal.valueOf(450.00).setScale(2, RoundingMode.CEILING), accDao.getAccountBalanceByAccountId(1));
    }

    @Test
    public void getTransferby_id_returns_right_transfer() {
        Transfer newTransfer = new Transfer(1, 2, 2, 1, 2, BigDecimal.valueOf(50));

        dao.create(newTransfer);

        Transfer transfer = dao.getTransferById(3001);

        accDao.updateBalances(newTransfer.getAcct_from(), newTransfer.getAmount().negate());
        accDao.updateBalances(newTransfer.getAcct_to(), (newTransfer.getAmount()));



        Assert.assertEquals(null, 1);

    }

    @Test
    public void getTransferbyUserId_returns_transfer_by_user_id() {
        Transfer newTransfer = new Transfer(1, 2, 2, 1, 2, BigDecimal.valueOf(50));

        Transfer transfer = dao.getTransferByUserId(USER_1.getId());

//        User user = userDao.getUserById(USER_1.getId());

        Assert.assertNotNull(transfer);

    }


}
