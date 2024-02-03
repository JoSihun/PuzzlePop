package com.ssafy.puzzlepop.dm.service;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmException;
import com.ssafy.puzzlepop.dm.repository.DmRepository;
import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: delete 작업 시 soft delete 되도록 리팩터링&컬럼 추가 필요

@Service
public class DmServiceImpl implements DmService {

    private final DmRepository dmRepository;
    private final FriendService friendService;

    @Autowired
    private DmServiceImpl(DmRepository dmRepository, FriendService friendService) {
        this.dmRepository = dmRepository;
        this.friendService = friendService;
    }

    //////////

    @Override
    public DmReadResponseDto createDm(DmCreateDto dmCreateDto) throws DmException {
        if (dmCreateDto.getFromUserId() == null || dmCreateDto.getToUserId() == null || dmCreateDto.getContent() == null) {
            throw new DmException("bad request");
        }

        // DM 발신자-수신자가 친구 관계 맞는지 확인
        FriendDto friendDto = friendService.getFriendById1AndId2(dmCreateDto.getFromUserId(), dmCreateDto.getToUserId());
        if (friendDto == null) {
            throw new DmException("허용되지 않은 요청");
        }

        // DB에 저장
        Dm dm = new Dm();

        dm.setFromUserId(dmCreateDto.getFromUserId());
        dm.setToUserId(dmCreateDto.getToUserId());
        dm.setContent(dmCreateDto.getContent());

        try {
            dmRepository.save(dm);

            // friendId 소켓방 구독하는 사용자에게 내용 뿌리기 위해, ReadResponse 형태로 가공
            DmReadResponseDto dmDto = new DmReadResponseDto();
            dmDto.setId(dm.getId());
            dmDto.setFromUserId(dm.getFromUserId());
            dmDto.setToUserId(dm.getToUserId());
            dmDto.setContent(dm.getContent());
            dmDto.setCreateTime(dm.getCreateTime());

            return dmDto;
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
    public List<DmReadResponseDto> getDmsByUserIdAndFriendUserId(DmReadRequestDto dmReadRequestDto) throws DmException {

        Long userId = dmReadRequestDto.getUserId();
        Long friendUserId = dmReadRequestDto.getFriendUserId();

        if (userId == null || friendUserId == null) {
            throw new DmException("bad request");
        }

        try {
            // 둘이 친구인지 확인
            if(friendService.getFriendById1AndId2(userId, friendUserId) == null) {
                throw new DmException("허용되지 않은 요청");
            }

            List<Dm> dmList = dmRepository.getDmsByuserIdAndFriendUserId(userId, friendUserId);

            List<DmReadResponseDto> dmResponseList = new ArrayList<>();
            for (Dm dm : dmList) {
                dmResponseList.add(new DmReadResponseDto(dm.getId(), dm.getFromUserId(), dm.getToUserId(), dm.getContent(), dm.getCreateTime()));
            }

            return dmResponseList;
        } catch (Exception e) {
            throw new DmException("error occurred during get DMs");
        }

    }

}
