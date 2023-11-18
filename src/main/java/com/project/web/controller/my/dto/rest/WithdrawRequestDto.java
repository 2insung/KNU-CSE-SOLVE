package com.project.web.controller.my.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class WithdrawRequestDto {
    @NotNull(message = "멤버 아이디를 입력해주세요.")
    private Integer memberId;
}
