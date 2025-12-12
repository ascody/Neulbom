package com.fourth.neulbom.service;

import com.fourth.neulbom.dto.SampleDto;
import com.fourth.neulbom.repository.SampleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {
    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public List<SampleDto> getSamplesByUnitAndType(String unit, String type) {
        return sampleRepository.findByUnitAndType(unit, type)
                .stream()
                .map(s -> new SampleDto(s.getId(), s.getUnit(), s.getType(), s.getScript()))
                .toList();
    }
}
