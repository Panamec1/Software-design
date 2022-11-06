package ru.akirakozov.sd.refactoring.html;

import ru.akirakozov.sd.refactoring.datamodule.ComponentsOfProd;

import java.util.List;
import java.util.stream.Collectors;

public class ProdForm extends FormHtml<ComponentsOfProd> {
    @Override
    public String formation(List<ComponentsOfProd> list) {
        String lines = list.stream()
                .map(product -> String.format("%s\t%s</br>", product.getName(), product.getPrice()))
                .collect(Collectors.joining("\n"));
        return String.format("<html>\n<body>\n%s</body>\n</html>", lines);
    }
}