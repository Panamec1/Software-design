package ru.akirakozov.sd.refactoring.datamodule;

import ru.akirakozov.sd.refactoring.datamodule.SqlTemBlock;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DataBase<T> {


    protected abstract String tableReturn();
    protected abstract List<String> fieldsReturn();
    protected abstract List<String> valsReturn(T entity);

    public void inserter(T entity) throws SQLException {
        String query = String.format(SqlTemBlock.inserter,
                tableReturn(),
                String.join(", ", fieldsReturn()),
                valsReturn(entity).stream().map(value -> "\"" + value + "\"").collect(Collectors.joining(", "))
        );
        executeInsertQuery(query);
    }


    protected void executeInsertQuery(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                statement.executeUpdate(query);
            }
        }
    }
}