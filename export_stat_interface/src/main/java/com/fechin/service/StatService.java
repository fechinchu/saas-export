package com.fechin.service;

import java.util.List;
import java.util.Map;

public interface StatService {

    List<Map<String,Object>> findFactoryWithSumCnumber(String companyId);

    List<Map<String, Object>> findSell(String companyId);

    List<Map<String, Object>> findOnline(String companyId);
}
