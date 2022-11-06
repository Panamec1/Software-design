package ru.akirakozov.sd.refactoring.datamodule;

public class SqlTemBlock {
    public static String selector = "select * from %s";
    public static String inserter = "insert into %s (%s) values (%s);";
}
