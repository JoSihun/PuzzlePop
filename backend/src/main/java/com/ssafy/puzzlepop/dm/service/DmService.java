package com.ssafy.puzzlepop.dm.service;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmException;

import java.util.List;

public interface DmService {

    DmReadResponseDto createDm(DmCreateDto dmCreateDto) throws DmException;

    Long updateDm(DmUpdateDto dmUpdateDto) throws DmException;

    void deleteDm(Long id) throws DmException;

    DmDto getDmById(Long id) throws DmException;

    List<DmReadResponseDto> getDmsByUserIdAndFriendUserId(DmReadRequestDto dmReadRequestDto) throws DmException;
}
