package com.fechin.service.cargo;

import com.fechin.domain.cargo.Factory;
import com.fechin.domain.cargo.FactoryExample;

import java.util.List;

public interface FactoryService {
    List<Factory> findByCtype(FactoryExample factoryExample);

}
