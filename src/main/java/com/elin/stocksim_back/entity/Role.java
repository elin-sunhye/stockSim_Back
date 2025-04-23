package com.elin.stocksim_back.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "권한")
public class Role {
    @Schema(description = "권한 고유 ID")
    private int roleId;

    @Schema(description = "권한명")
    private String roleName;

    @Schema(description = "권한명-kor")
    private String roleNameKor;
}
