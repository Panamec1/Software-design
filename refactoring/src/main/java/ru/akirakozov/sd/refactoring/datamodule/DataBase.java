package ru.akirakozov.sd.refactoring.datamodule;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class DataBase<T> {


    protected abstract String       tableReturn();
    protected abstract List<String> def();
    protected abstract List<String> fieldsReturn();
    protected abstract List<String> valsReturn(T entity);
    protected abstract List<String> fields();
    protected abstract T            transfromer(Map<String, Object> map);

    public void inserter(T entity) throws SQLException {
        String query = String.format(SqlTemBlock.inserter, tableReturn(),
                String.join(", ", fieldsReturn()),
                valsReturn(entity).stream().map(value -> "\"" + value + "\"").collect(Collectors.joining(", "))
        );
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(query);
            }
        }
    }
    
    public List<T> selector() throws SQLException {
        String query = String.format(SqlTemBlock.selector, tableReturn());
        List<T> listRes = new ArrayList<>();
        
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Map<String, Object> map = new HashMap<>();
                    for (String singleField : fields()) {
                        Object value = resultSet.getObject(singleField);
                        map.put(singleField, value);
                    }
                    listRes.add(transfromer(map));
                }
                return listRes;
            }
        }
    }

    public void createTable() throws SQLException {
        String joinedFields = String.join(", ", def());
        String query = String.format(SqlTemBlock.table, tableReturn(), joinedFields);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(query);
            }
        }
    }
}