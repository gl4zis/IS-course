package ru.itmo.is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.is.dto.request.DormitoryRequest;
import ru.itmo.is.dto.response.DormitoryResponse;
import ru.itmo.is.entity.dorm.Dormitory;
import ru.itmo.is.entity.dorm.University;
import ru.itmo.is.exception.NotFoundException;
import ru.itmo.is.repository.DormitoryRepository;
import ru.itmo.is.repository.UniversityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DormitoryService {
    private final DormitoryRepository dormitoryRepository;
    private final UniversityRepository universityRepository;

    public List<DormitoryResponse> getAllDormitories() {
        List<DormitoryResponse> res = new ArrayList<>();
        dormitoryRepository.findAll().forEach(d -> res.add(new DormitoryResponse(d)));
        return res;
    }

    public DormitoryResponse getDormitory(int id) {
        return dormitoryRepository.findById(id)
                .map(DormitoryResponse::new)
                .orElseThrow(() -> new NotFoundException("Dormitory not found"));
    }

    public void addDormitory(DormitoryRequest req) {
        List<University> universities = universityRepository.getByIdIn(req.getUniversityIds());
        if (universities.size() != req.getUniversityIds().size()) {
            throw new NotFoundException("University not found");
        }

        Dormitory dormitory = new Dormitory();
        dormitory.setAddress(req.getAddress());
        dormitory.setUniversities(universities);
        universities.forEach(university -> university.getDormitories().add(dormitory));
        dormitoryRepository.save(dormitory);
    }

    public void updateDormitory(int id, DormitoryRequest req) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dormitory not found"));

        List<University> universities = universityRepository.getByIdIn(req.getUniversityIds());
        if (universities.size() != req.getUniversityIds().size()) {
            throw new NotFoundException("University not found");
        }

        dormitory.setAddress(req.getAddress());

        dormitory.getUniversities().clear();
        dormitory.setUniversities(universities);
        universities.forEach(university -> university.getDormitories().add(dormitory));
        dormitoryRepository.save(dormitory);
    }

    public void deleteDormitory(int id) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dormitory not found"));
        dormitoryRepository.delete(dormitory);
    }
}
