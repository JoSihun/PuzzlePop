package com.ssafy.puzzlepop.dm.service;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmBadRequestException;
import com.ssafy.puzzlepop.dm.exception.DmException;
import com.ssafy.puzzlepop.dm.exception.DmNotFoundException;

import java.util.List;

public interface DmService {

    DmReadResponseDto createDm(Long friendId, DmCreateDto dmCreateDto) throws DmException, DmBadRequestException;

    Long updateDm(DmUpdateDto dmUpdateDto) throws DmException, DmNotFoundException, DmBadRequestException;

    void deleteDm(Long id) throws DmException, DmNotFoundException;

    DmDto getDmById(Long id) throws DmException, DmNotFoundException;

    List<DmReadResponseDto> getDmsByUserIdAndFriendUserId(DmReadRequestDto dmReadRequestDto) throws DmException, DmBadRequestException;
}
