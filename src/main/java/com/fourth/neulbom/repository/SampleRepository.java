package com.fourth.neulbom.repository;

import com.fourth.neulbom.dto.SampleDto;
import com.fourth.neulbom.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {
    List<Sample> findByUnitAndType(String unit, String type);
}
