package ru.akirakozov.sd.refactoring.datamodule;

import ru.akirakozov.sd.refactoring.datamodule.SqlTemBlock;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class DataBase<T> {


    protected abstract String tableReturn();
    protected abstract List<String> fieldsReturn();
    protected abstract List<String> valsReturn(T entity);
    protected abstract List<String> fields();
    protected abstract T transfromer(Map<String, Object> map);

    public void inserter(T entity) throws SQLException {
        String query = String.format(SqlTemBlock.inserter,
                tableReturn(),
                String.join(", ", fieldsReturn()),
                valsReturn(entity).stream().map(value -> "\"" + value + "\"").collect(Collectors.joining(", "))
        );
        executeInsertQuery(query);
    }
    
    public List<T> selector() throws SQLException {
        String query = String.format(SqlTemBlock.selector,
                tableReturn());
        List<T> listRes = new ArrayList<>();
        
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Map<String, Object> map = new HashMap<>();
                    for (String field : fields()) {
                        Object value = resultSet.getObject(field);
                        map.put(field, value);
                    }
                    listRes.add(transfromer(map));
                }
                return listRes;
            }
        }
    }

    protected void executeInsertQuery(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(query);
            }
        }
    }
}