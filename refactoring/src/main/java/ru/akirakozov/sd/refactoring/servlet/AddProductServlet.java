package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.datamodule.ProdData;
import ru.akirakozov.sd.refactoring.datamodule.ComponentsOfProd;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import ru.akirakozov.sd.refactoring.datamodule.DataBase;
import ru.akirakozov.sd.refactoring.datamodule.ComponentsOfProd;
import ru.akirakozov.sd.refactoring.datamodule.ProdData;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    private final ProdData prodData;
    public AddProductServlet(ProdData prodData) {
        this.prodData = prodData;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ComponentsOfProd product = new ComponentsOfProd(request.getParameter("name"),
                Long.parseLong(request.getParameter("price")));
        try {
            prodData.inserter(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
