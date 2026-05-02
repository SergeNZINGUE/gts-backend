package com.gts.backgts.dto;

public record ConducteurImagesResponse(

        Long conducteurId,
        String imgCniUrl,
        String imgConducteurUrl,
        String imgPermisUrl
) {
}
