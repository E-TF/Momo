package com.project.momo.repository;

import com.project.momo.common.exception.DistributedLockException;
import com.project.momo.common.exception.ErrorCode;
import com.project.momo.common.lock.DistributedLockManager;
import com.project.momo.common.lock.ThreadLocalConnectionHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class DistributedLockManagerImpl implements DistributedLockManager {
    private static final String GET_LOCK = "SELECT GET_LOCK(?,?)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";
    private static final int LOCK_NAME_IDX = 1;
    private static final int TIMEOUT_IDX = 2;
    private static final int RESULT_IDX = 1;
    private static final int SUCCESS = 1;
    private final DataSource dataSource;

    @Override
    public void getLock(String lockName, Duration timeout) throws DistributedLockException {
        try {
            Connection con = dataSource.getConnection();
            ThreadLocalConnectionHolder.save(con);
            PreparedStatement statement = con.prepareStatement(GET_LOCK);
            statement.setString(LOCK_NAME_IDX, lockName);
            statement.setInt(TIMEOUT_IDX, (int) timeout.getSeconds());
            check(statement.executeQuery());
        } catch (SQLException e) {
            throw new DistributedLockException(e);
        }
    }

    @Override
    public void releaseLock(String lockName) {
        try {
            Connection connection = ThreadLocalConnectionHolder.get();
            PreparedStatement statement = connection.prepareStatement(RELEASE_LOCK);
            statement.setString(LOCK_NAME_IDX, lockName);
            ResultSet rs = statement.executeQuery();
            check(rs);
        } catch (SQLException | DistributedLockException e) {
            //TODO 개발자 알림 추가
        } finally {
            ThreadLocalConnectionHolder.clear();
        }
    }

    private void check(ResultSet rs) throws SQLException, DistributedLockException {
        if (!rs.next() || rs.getInt(RESULT_IDX) != SUCCESS) {
            throw new DistributedLockException(ErrorCode.LOCK_FAILURE);
        }
    }
}
