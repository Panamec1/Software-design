package ru.akirakozov.sd.refactoring.servlet;

import        java.io.IOException;
import        org.junit.Test;
import        org.junit.runner.RunWith;
import        org.mockito.Mockito;
import        org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class AddProductServletTestsModules extends ServletTestsModules<AddProductServlet> {

    private static String paramOfName = "name";
    private static String paramOfPrice = "price";

    @Override
    protected AddProductServlet initServlet() { return new AddProductServlet(prod); }

    @Test
    public void failOfAdding() throws IOException {
        Mockito.when(request.getParameter(paramOfPrice)).thenReturn("fd");
        Mockito.when(request.getParameter(paramOfName)).thenReturn("name12");
        assertThrows(NumberFormatException.class,() -> servlet.doGet(request, response));
    }

    @Test
    public void normalAdding() throws IOException {
        Mockito.when(request.getParameter(paramOfPrice)).thenReturn("20");
        Mockito.when(request.getParameter(paramOfName)).thenReturn("name12");
        servlet.doGet(request, response);
        assertThat(servWrite.toString()).isEqualToNormalizingWhitespace("OK");
    }
}