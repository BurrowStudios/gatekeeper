package org.burrow_studios.gatekeeper.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final String STMT_CREATE_TABLE_PERMISSIONS        = "CREATE TABLE IF NOT EXISTS `permissions` (`id` INT NOT NULL AUTO_INCREMENT, `name` TEXT NOT NULL, PRIMARY KEY (`id`), UNIQUE (`name`));";
    private static final String STMT_CREATE_TABLE_ENTITY_PERMISSIONS = "CREATE TABLE IF NOT EXISTS `entity_permissions` (`entity_id` BIGINT(20) NOT NULL, `permission_id` INT NOT NULL, `value` BOOLEAN NOT NULL, PRIMARY KEY (`entity_id`, `permission_id`));";
    private static final String STMT_ALTER_TABLE_ENTITY_PERMISSIONS  = "ALTER TABLE `entity_permissions` ADD FOREIGN KEY (`permission_id`) REFERENCES `permissions`(`id`) ON DELETE NO ACTION ON UPDATE RESTRICT;";

    private static final String STMT_GET_ENTITY_ID = "SELECT DISTINCT `entity_id` FROM `entity_permissions` WHERE `entity_id` = ?;";
    private static final String STMT_GET_ENTITIES = "SELECT DISTINCT `entity_id` AS `id` FROM `entity_permissions`;";
    private static final String STMT_GET_ENTITY = "SELECT `permissions`.`name` AS `permission`, `entity_permissions`.`value` FROM `entity_permissions` INNER JOIN `permissions` ON `entity_permissions`.`permission_id` = `permissions`.`id` WHERE `entity_permissions`.`entity_id` = ? ORDER BY `permissions`.`name`;";

    private static final String STMT_DELETE_PERMISSION = "DELETE FROM `entity_permissions` WHERE `entity_id` = ? AND `permission_id` = (SELECT `id` FROM `permissions` WHERE `name` = '?');";

    private static final Logger LOG = Logger.getLogger(Database.class.getSimpleName());

    private final Connection connection;

    public Database(
            @NotNull String host,
            @NotNull String port,
            @NotNull String database,
            @NotNull String user,
            @NotNull String pass
    ) throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);

        LOG.log(Level.INFO, "Initiating database connection to " + url);
        this.connection = DriverManager.getConnection(url, user, pass);

        this.init();

        LOG.log(Level.INFO, "Database is online");
    }

    public void init() {
        LOG.log(Level.INFO, "Creating tables");
        try {
            final PreparedStatement createPermissions       = connection.prepareStatement(STMT_CREATE_TABLE_PERMISSIONS);
            final PreparedStatement createEntityPermissions = connection.prepareStatement(STMT_CREATE_TABLE_ENTITY_PERMISSIONS);
            final PreparedStatement alterEntityPermissions  = connection.prepareStatement(STMT_ALTER_TABLE_ENTITY_PERMISSIONS);

            createPermissions.execute();
            createEntityPermissions.execute();
            alterEntityPermissions.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Could not create tables", e);
        }
    }

    public @NotNull JsonArray getEntities() throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(STMT_GET_ENTITIES)) {
            ResultSet result = stmt.executeQuery();

            JsonArray json = new JsonArray();

            while (result.next()) {
                json.add(result.getLong("id"));
            }

            return json;
        }
    }

    public @Nullable JsonObject getEntity(long id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(STMT_GET_ENTITY_ID)) {
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();

            if (!result.next())
                return null;
        }

        try (PreparedStatement stmt = connection.prepareStatement(STMT_GET_ENTITY)) {
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();

            JsonArray overrides = new JsonArray();

            while (result.next()) {
                JsonObject json = new JsonObject();
                json.addProperty("permission", result.getString("permission"));
                json.addProperty("value", result.getBoolean("value"));
                overrides.add(json);
            }

            JsonObject entity = new JsonObject();

            entity.addProperty("id", id);
            entity.add("overrides", overrides);

            return entity;
        }
    }

    public void removePermission(long id, String permission) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(STMT_DELETE_PERMISSION)) {
            stmt.setLong(1, id);
            stmt.setString(2, permission);

            stmt.execute();
        }
    }

    public void stop() throws SQLException {
        LOG.log(Level.INFO, "Closing connection");
        this.connection.close();
    }
}
