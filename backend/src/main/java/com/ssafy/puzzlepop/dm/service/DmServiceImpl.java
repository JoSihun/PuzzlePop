package com.ssafy.puzzlepop.dm.service;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmException;
import com.ssafy.puzzlepop.dm.repository.DmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

// TODO: delete 작업 시 soft delete 되도록 리팩터링&컬럼 추가 필요

@Service
public class DmServiceImpl implements DmService {

    private final DmRepository dmRepository;

    @Autowired
    private DmServiceImpl(DmRepository dmRepository) {
        this.dmRepository = dmRepository;
    }

    //////////

    @Override
    public Long createDm(DmCreateDto dmCreateDto) throws DmException {
        if (dmCreateDto.getFromUserId() == null || dmCreateDto.getToUserId() == null || dmCreateDto.getContent() == null) {
            throw new DmException("bad request");
        }

        Dm dm = new Dm();

        dm.setFromUserId(dmCreateDto.getFromUserId());
        dm.setToUserId(dmCreateDto.getToUserId());
        dm.setContent(dmCreateDto.getContent());

        try {
            dmRepository.save(dm);
            return dm.getId();
        } catch (Exception e) {
            throw new DmException("error occurred while create dm");
        }
    }

    @Override
    public Long updateDm(DmUpdateDto dmUpdateDto) throws DmException {
        if (dmUpdateDto.getId() <= 0 || dmUpdateDto.getContent() == null) {
            throw new DmException("bad request");
        }

        Dm existDm = dmRepository.findById(dmUpdateDto.getId()).orElse(null);

        if (existDm == null) {
            throw new DmException("dm matches to id doesn't exist");
        }

        existDm.setContent(dmUpdateDto.getContent());
        existDm.setUpdateTime(new Date());

        try {
            dmRepository.save(existDm);
            return existDm.getId();
        } catch (Exception e) {
            throw new DmException("error occurred during update database");
        }
    }

    @Override
    public void deleteDm(Long id) throws DmException {

        Dm existDm = dmRepository.findById(id).orElse(null);
        if (existDm == null) {
            throw new DmException("dm matches to id doesn't exist");
        }

        try {
            dmRepository.deleteById(id);
        } catch (Exception e) {
            throw new DmException("error occurred during delete data");
        }

    }

    @Override
    public DmDto getDmById(Long id) throws DmException {

        Dm existDm = dmRepository.findById(id).orElse(null);

        if (existDm == null) {
            throw new DmException("dm matches to id doesn't exist");
        }

        return new DmDto(existDm);
    }

    @Override
    public List<DmReadResponseDto> getDmsByFriendId(DmReadRequestDto dmReadRequestDto) throws DmException {

        Long userId = dmReadRequestDto.getUserId();
        Long friendId = dmReadRequestDto.getFriendId();

        if (userId == null || friendId == null) {
            throw new DmException("bad request");
        }

        try {
            List<Dm> dmList = dmRepository.getDmsByFriendId(userId, friendId);

            List<DmReadResponseDto> dmResponseList = new ArrayList<>();
            for (Dm dm : dmList) {
                dmResponseList.add(new DmReadResponseDto(dm.getId(), dm.getFromUserId(), dm.getToUserId(), dm.getContent(), dm.getCreateTime()));
            }

            return dmResponseList;
        } catch (Exception e) {
            throw new DmException("error occurred during get DMs by friendid");
        }

    }

}
