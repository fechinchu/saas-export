package com.fechin.dao.system.dept;

import com.fechin.domain.system.dept.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptDao {

    List<Dept> findAll(String companyId);

    Dept findById(String deptId);

    Dept findByIdBeforeUpdate(@Param("deptId") String id, @Param("companyId") String companyId);

    void update(Dept dept);

    void add(Dept dept);

    void delete(@Param("deptId") String id,@Param("companyId") String companyId);

    String findByName(String deptName);
}
