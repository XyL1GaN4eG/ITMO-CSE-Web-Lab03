package lab03.db;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    private static final String resFields = "x, y, r, isIn, executionTime, serverTime";
    private static final String INSERT_QUERY =       "INSERT INTO result (" + resFields + ") VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY =   "SELECT " + resFields + " FROM result";
    private static final String DELETE_ALL_QUERY =   "DELETE FROM result";

    public void addNewResult(ResultEntityDAO result) {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            preparedStatement.setDouble(1, result.getX());
            preparedStatement.setDouble(2, result.getY());
            preparedStatement.setDouble(3, result.getR());
            preparedStatement.setLong(4, result.getExecutionTime());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(result.getServerTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении результата в базу данных", e);
        }
    }

    public List<ResultEntityDAO> getAllResults() {
        List<ResultEntityDAO> results = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                results.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех результатов", e);
        }
        return results;
    }

    public void clearResults() {
        try (Connection connection = JDBCConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_QUERY)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистке таблицы результатов", e);
        }
    }

    private ResultEntityDAO mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new ResultEntityDAO(
                resultSet.getDouble("x"),
                resultSet.getDouble("y"),
                resultSet.getDouble("r"),
                resultSet.getBoolean("isIn"),
                resultSet.getLong("executionTime"),
                resultSet.getTimestamp("serverTime").toLocalDateTime());
    }
}
