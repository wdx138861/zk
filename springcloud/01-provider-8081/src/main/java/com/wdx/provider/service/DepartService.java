package com.wdx.provider.service;

import com.wdx.provider.bean.Depart;
import java.util.List;

/**
 * @Author: wdx
 * @Data: 2020/5/3 16:11
 */
public interface DepartService {

    boolean saveDepart(Depart depart);

    boolean removeDepartById(Integer id);

    boolean modifyDepart(Depart depart);

    Depart getDepartById(Integer id);

    List<Depart> findAllDepartList();
}
