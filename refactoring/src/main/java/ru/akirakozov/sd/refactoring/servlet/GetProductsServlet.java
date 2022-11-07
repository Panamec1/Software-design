package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.datamodule.ComponentsOfProd;
import ru.akirakozov.sd.refactoring.datamodule.ProdData;
import ru.akirakozov.sd.refactoring.html.ProdForm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */

public class GetProductsServlet extends HttpServlet {
    private final ProdForm formatter = new ProdForm();
    private final ProdData prodData;

    public GetProductsServlet(ProdData prodData) {
        this.prodData = prodData;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try { response.getWriter().println(formatter.formation(prodData.selector()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
