package com.wdx.provider.repository;

import com.wdx.provider.bean.Depart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: wdx
 * @Data: 2020/5/3 16:09
 */
public interface DepartRepository extends JpaRepository<Depart, Integer> {
}
