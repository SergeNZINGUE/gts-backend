package com.gts.backgts.dto;

import java.util.List;

public record FactureMissionsRequest(
        List<Long> missionIds
) {
}
