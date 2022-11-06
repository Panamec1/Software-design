package akirakozov.refactoring.servlet;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class ServletTestsModules<Serv extends HttpServlet> {

    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;

    protected abstract Serv initServlet();
    protected Serv servlet;
    protected PrintWriter servWrite;

    static class ServWrite extends PrintWriter {
        public ServWrite() {super(new StringWriter());}

        @Override
        public String toString() {return out.toString();}
    }



    @Before
    public void testInit() throws IOException {
        servWrite = new ServWrite();
        servlet = initServlet();
        Mockito.when(response.getWriter()).thenReturn(servWrite);
    }
}