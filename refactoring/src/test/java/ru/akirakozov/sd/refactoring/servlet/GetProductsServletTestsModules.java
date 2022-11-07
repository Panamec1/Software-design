package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.akirakozov.sd.refactoring.datamodule.ComponentsOfProd;
import ru.akirakozov.sd.refactoring.datamodule.ProdData;
import ru.akirakozov.sd.refactoring.html.ProdForm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GetProductsServletTestsModules extends ServletTestsModules<GetProductsServlet> {
    private ProdForm formatter = new ProdForm();

    @Override
    protected GetProductsServlet initServlet() {return new GetProductsServlet(prod);}


    @Test
    public void emptySelector() throws SQLException, IOException {
        Mockito.when(prod.selector()).thenReturn(List.of());
        servlet.doGet(request, response);
        assertThat(servWrite.toString()).isEqualToNormalizingWhitespace(formatter.formation(List.of()));
    }

    @Test
    public void realSelector() throws SQLException, IOException {
        List<ComponentsOfProd> listprod = List.of(
                new ComponentsOfProd("a", Long.parseLong("1")),
                new ComponentsOfProd("b", Long.parseLong("2")),
                new ComponentsOfProd("c", Long.parseLong("3"))
        );
        Mockito.when(prod.selector()).thenReturn(listprod);
        servlet.doGet(request, response);

        assertThat(servWrite.toString()).isEqualToNormalizingWhitespace(formatter.formation(listprod));
    }

}