package ru.akirakozov.sd.refactoring.datamodule;

import ru.akirakozov.sd.refactoring.datamodule.ComponentsOfProd;

import java.util.List;
import java.util.Map;

public class ProdData extends DataBase<ComponentsOfProd> {

    @Override
    protected String tableReturn() { return "PRODUCT";}

    @Override
    protected List<String> fieldsReturn() { return List.of("NAME", "PRICE"); }

    @Override
    protected List<String> valsReturn(ComponentsOfProd entity) {
        String name = entity.getName();
        String price = String.valueOf(entity.getPrice());
        return List.of(name, price);
    }

    @Override
    protected List<String> fields() {return List.of("NAME", "PRICE");}

    @Override
    protected ComponentsOfProd transfromer(Map<String, Object> map) {
        List<String> fields = fields();
        String name = map.get(fields.get(0)).toString();
        Long price = (Long) map.get(fields.get(1));
        return new ComponentsOfProd(name, price);
    }
}