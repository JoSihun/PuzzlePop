package com.ssafy.puzzlepop.dm.service;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmException;

import java.util.List;

public interface DmService {

    int createDm(DmCreateDto dmCreateDto) throws DmException;

    int updateDm(DmUpdateDto dmUpdateDto) throws DmException;

    void deleteDm(int id) throws DmException;

    DmDto getDmById(int id) throws DmException;

    List<DmReadResponseDto> getDmsByFriendId(DmReadRequestDto dmReadRequestDto) throws DmException;
}
