package com.dbd.nanal.service;

import com.dbd.nanal.dto.NotificationRequestDTO;
import com.dbd.nanal.dto.NotificationResponseDTO;
import com.dbd.nanal.model.*;
import com.dbd.nanal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserProfileRepository userProfileRepository;
    private final GroupRepository groupRepository;
    private final DiaryRepository diaryRepository;
    private final GroupUserRelationRepository groupUserRelationRepository;


    //save friend notice
    public NotificationResponseDTO saveFriendNotice(NotificationRequestDTO notice){
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .user(UserEntity.builder().userIdx(notice.getUserIdx().get(0)).build())
                .requestUserIdx(notice.getRequest_user_idx())
                .noticeType(notice.getNotice_type())
                .content(userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname())
                .build();

        return new NotificationResponseDTO(noticeRepository.save(noticeEntity));
    }

    //save group notice
    public List<NotificationResponseDTO> saveGroupNotice(NotificationRequestDTO notice){
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String groupName=groupRepository.getReferenceById(notice.getRequest_group_idx().get(0)).getGroupName();

        String str=nickname+","+groupName;

        List<NoticeEntity> noticeEntityList =new ArrayList<>();
        for(int userIdx: notice.getUserIdx()){
            NoticeEntity noticeEntity=NoticeEntity.builder()
                    .user(UserEntity.builder().userIdx(userIdx).build())
                    .requestUserIdx(notice.getRequest_user_idx())
                    .requestGroupIdx(notice.getRequest_group_idx().get(0))
                    .requestDiaryIdx(notice.getRequest_diary_idx())
                    .noticeType(notice.getNotice_type())
                    .content(str)
                    .build();
            noticeEntityList.add(noticeRepository.save(noticeEntity));
        }
        return noticeEntityList.stream().map(x-> new NotificationResponseDTO(x)).collect(Collectors.toList());
    }

    //save comment notice
    public NotificationResponseDTO saveCommentNotice(NotificationRequestDTO notice){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(notice.getRequest_diary_idx());
        if(notice.getRequest_user_idx() == diaryEntity.getUser().getUserIdx()) {
            return new NotificationResponseDTO();
        }
        String userName=userProfileRepository.getReferenceById(diaryEntity.getUser().getUserIdx()).getNickname();
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();

        String str=userName +","+nickname;

        NoticeEntity noticeEntity=NoticeEntity.builder()
                .user(UserEntity.builder().userIdx(diaryEntity.getUser().getUserIdx()).build())
                .requestUserIdx(notice.getRequest_user_idx())
                .requestGroupIdx(notice.getRequest_group_idx().get(0))
                .requestDiaryIdx(notice.getRequest_diary_idx())
                .noticeType(notice.getNotice_type())
                .content(str)
                .build();
        return new NotificationResponseDTO(noticeRepository.save(noticeEntity));
    }

    // save diary notice
    public List<List<NotificationResponseDTO>> saveDiaryNotice(NotificationRequestDTO notice){
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();

        List<List<NotificationResponseDTO>> list=new ArrayList<>();
        for(int i: notice.getRequest_group_idx()){
            String groupName=groupRepository.getReferenceById(i).getGroupName();
            List<GroupUserRelationEntity> groupUserRelationEntityList=groupUserRelationRepository.findByGroupDetail(GroupDetailEntity.builder().groupIdx(i).build());

            String str=nickname+","+groupName;
            List<NoticeEntity> noticeEntityList= new ArrayList<>();
            for(GroupUserRelationEntity groupUser: groupUserRelationEntityList){
                if(groupUser.getUser().getUserIdx() == notice.getRequest_user_idx()) continue;
                NoticeEntity noticeEntity=NoticeEntity.builder()
                        .user(UserEntity.builder().userIdx(groupUser.getUser().getUserIdx()).build())
                        .requestUserIdx(notice.getRequest_user_idx())
                        .requestGroupIdx(i)
                        .requestDiaryIdx(notice.getRequest_diary_idx())
                        .noticeType(notice.getNotice_type())
                        .content(str)
                        .build();
                noticeEntityList.add(noticeRepository.save(noticeEntity));
            }
            list.add(noticeEntityList.stream().map(x-> new NotificationResponseDTO(x)).collect(Collectors.toList()));
        }
        return list;
    }

    //get notice
    public List<NotificationResponseDTO> getNotice(int userIdx){
        List<NoticeEntity> noticeEntityList=noticeRepository.findByUserAndIsCheckedOrderByCreationDateDesc(UserEntity.builder().userIdx(userIdx).build(), false);
        return noticeEntityList.stream().map(x->new NotificationResponseDTO(x)).collect(Collectors.toList());
    }

    //delete notice
    public void deleteNotice( int noticeIdx){
        noticeRepository.deleteById(noticeIdx);
    }

    // save notice check
    public NotificationResponseDTO saveNoticeCheck(int noticeIdx){
        NoticeEntity noticeEntity=noticeRepository.getReferenceById(noticeIdx);
        noticeEntity.setIsChecked(true);

        return new NotificationResponseDTO(noticeRepository.save(noticeEntity));
    }
}