package org.burrow_studios.gatekeeper.database;

import java.sql.*;

public class Database {
    private static final String STMT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `permissions` (`id` BIGINT(20) NOT NULL , `value` BOOLEAN NOT NULL , PRIMARY KEY (`id`));";
    private static final String STMT_GET = "SELECT `value` FROM `permissions` WHERE `id` = `?`";

    private final Connection connection;

    public Database() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost/gatekeeper");
        this.init();
    }

    public void init() {
        try (PreparedStatement stmt = connection.prepareStatement(STMT_CREATE_TABLE)) {
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Could not create table", e);
        }
    }

    public boolean hasPermission(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(STMT_GET)) {
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next())
                return false;

            return resultSet.getBoolean("value");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
