package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.AccessControlContext;

public class JdbcAccountDaoTest extends BaseDaoTests{

    private static final User USER_1 = new User(1, "user1", "user1", "ROLE_USER");
    protected static final Account ACCOUNT_1 = new Account(1, 1001, BigDecimal.valueOf(500));
    protected static final Account ACCOUNT_2 = new Account(2, 3, BigDecimal.valueOf(100));
    protected static final Account ACCOUNT_3 = new Account(3, 2, BigDecimal.valueOf(200));
    private JdbcAccountDao dao;

    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcAccountDao(jdbcTemplate);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void getAccountBalanceByUsername_returns_balance() {
        Account acc = dao.getAccountByUserId(1001);
        System.out.println("Account Id: " + acc.getAcccount_id());
        Account account = dao.getAccountBalanceByUserName(USER_1.getUsername());
        BigDecimal account1 = dao.getAccountBalanceByAccountId(ACCOUNT_1.getAcccount_id());
        Assert.assertEquals(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.CEILING), account.getBalance());
    }

    @Test
    public void getAccountBalanceById_returns_Balance_By_Id() {
        BigDecimal account = dao.getAccountBalanceByAccountId(ACCOUNT_1.getAcccount_id());

        Assert.assertEquals(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.CEILING), account);
    }

    @Test
    public void getAccountByUserId_returns_Account_By_Id() {
        Account account = dao.getAccountByUserId(ACCOUNT_1.getUser_id());

        Assert.assertNotNull(account);
    }

}
