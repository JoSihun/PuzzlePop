package com.ssafy.puzzlepop.dm.service;

import com.ssafy.puzzlepop.dm.domain.*;
import com.ssafy.puzzlepop.dm.exception.DmBadRequestException;
import com.ssafy.puzzlepop.dm.exception.DmException;
import com.ssafy.puzzlepop.dm.exception.DmNotFoundException;
import com.ssafy.puzzlepop.dm.repository.DmRepository;
import com.ssafy.puzzlepop.friend.domain.FriendDto;
import com.ssafy.puzzlepop.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    public DmReadResponseDto createDm(Long friendId, DmCreateDto dmCreateDto) throws DmException, DmBadRequestException {
        if (dmCreateDto.getFromUserId() == null || dmCreateDto.getToUserId() == null || dmCreateDto.getContent() == null) {
            throw new DmBadRequestException();
        }

        try {
            // DM 발신자-수신자가 친구 관계 맞는지 확인
            FriendDto friendDto = friendService.getFriendById1AndId2(dmCreateDto.getFromUserId(), dmCreateDto.getToUserId());
            if (friendDto == null) {
                throw new DmBadRequestException("bad request");
            }
            if(!friendId.equals(friendDto.getId())) {
                throw new DmBadRequestException("bad request - not in friend relationship");
            }

            // DB에 저장
            Dm dm = new Dm();

            dm.setFromUserId(dmCreateDto.getFromUserId());
            dm.setToUserId(dmCreateDto.getToUserId());
            dm.setContent(dmCreateDto.getContent());

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
    public Long updateDm(DmUpdateDto dmUpdateDto) throws DmException, DmNotFoundException, DmBadRequestException {
        if (dmUpdateDto.getId() <= 0 || dmUpdateDto.getContent() == null) {
            throw new DmBadRequestException();
        }

        Dm existDm;
        try {
            existDm = dmRepository.findById(dmUpdateDto.getId()).orElse(null);
        } catch (Exception e) {
            throw new DmException("error occurred during finding data");
        }

        if (existDm == null) {
            throw new DmNotFoundException("dm matches to id doesn't exist");
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
    public void deleteDm(Long id) throws DmException, DmNotFoundException {

        Dm existDm = dmRepository.findById(id).orElse(null);
        if (existDm == null) {
            throw new DmNotFoundException();
        }

        try {
            dmRepository.deleteById(id);
        } catch (Exception e) {
            throw new DmException("error occurred during delete data");
        }

    }

    @Override
    public DmDto getDmById(Long id) throws DmException, DmNotFoundException {

        Dm existDm;
        try {
            existDm = dmRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new DmException("error occurred during inquiring to db");
        }

        if (existDm == null) {
            throw new DmNotFoundException();
        }

        return new DmDto(existDm);
    }

    @Override
    public List<DmReadResponseDto> getDmsByUserIdAndFriendUserId(DmReadRequestDto dmReadRequestDto) throws DmException, DmBadRequestException {

        Long userId = dmReadRequestDto.getUserId();
        Long friendUserId = dmReadRequestDto.getFriendUserId();

        if (userId == null || friendUserId == null) {
            throw new DmBadRequestException();
        }

        try {
            // 둘이 친구인지 확인
            if (friendService.getFriendById1AndId2(userId, friendUserId) == null) {
                throw new DmBadRequestException();
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
