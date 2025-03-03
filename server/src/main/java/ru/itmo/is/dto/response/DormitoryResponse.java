package ru.itmo.is.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.itmo.is.entity.dorm.Dormitory;
import ru.itmo.is.entity.dorm.University;

import java.util.List;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DormitoryResponse {
    int id;
    String address;
    List<Integer> universityIds;

    public DormitoryResponse(Dormitory dormitory) {
        this.id = dormitory.getId();
        this.address = dormitory.getAddress();
        this.universityIds = dormitory.getUniversities().stream().map(University::getId).toList();
    }
}
