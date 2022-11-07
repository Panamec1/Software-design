package ru.akirakozov.sd.refactoring.datamodule;

public class SqlTemBlock {
    public static String idPrim   = "id integer primary key autoincrement not null";
    public static String nameText = "name text not null";
    public static String selector = "select * from %s";
    public static String inserter = "insert into %s (%s) values (%s);";
    public static String table    = "create table if not exists %s (%s)";
    public static String price    = "price int not null";
}
