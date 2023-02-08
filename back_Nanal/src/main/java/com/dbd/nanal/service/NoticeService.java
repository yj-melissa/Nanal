package com.dbd.nanal.service;

import com.dbd.nanal.dto.NotificationRequestDTO;
import com.dbd.nanal.dto.NotificationResponseDTO;
import com.dbd.nanal.model.*;
import com.dbd.nanal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void saveFriendNotice(NotificationRequestDTO notice){
        for(int userIdx: notice.getUserIdx()) {
            NoticeEntity noticeEntity = NoticeEntity.builder()
                    .user(UserEntity.builder().userIdx(userIdx).build())
                    .requestUserIdx(notice.getRequest_user_idx())
                    .content(userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname())
                    .build();
            noticeRepository.save(noticeEntity);
        }
    }

    //save group notice
    public void saveGroupNotice(NotificationRequestDTO notice){
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String groupName=groupRepository.getReferenceById(notice.getRequest_group_idx()).getGroupName();

        String str=nickname+","+groupName;
        for(int userIdx: notice.getUserIdx()){
            NoticeEntity noticeEntity=NoticeEntity.builder()
                    .user(UserEntity.builder().userIdx(userIdx).build())
                    .requestUserIdx(notice.getRequest_user_idx())
                    .requestGroupIdx(notice.getRequest_group_idx())
                    .requestDiaryIdx(notice.getRequest_diary_idx())
                    .noticeType(notice.getNotice_type())
                    .content(str)
                    .build();
            noticeRepository.save(noticeEntity);
        }
    }

    //save comment notice
    public void saveCommentNotice(NotificationRequestDTO notice){
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(notice.getRequest_diary_idx());
        String userName=userProfileRepository.getReferenceById(diaryEntity.getUser().getUserIdx()).getNickname();
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();

        String str=userName +","+nickname;
        NoticeEntity noticeEntity=NoticeEntity.builder()
                .user(UserEntity.builder().userIdx(diaryEntity.getUser().getUserIdx()).build())
                .requestUserIdx(notice.getRequest_user_idx())
                .requestGroupIdx(notice.getRequest_group_idx())
                .requestDiaryIdx(notice.getRequest_diary_idx())
                .content(str)
                .build();
        noticeRepository.save(noticeEntity);
    }

    // save diary notice
    public void saveDiaryNotice(NotificationRequestDTO notice){
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String groupName=groupRepository.getReferenceById(notice.getRequest_group_idx()).getGroupName();
        List<GroupUserRelationEntity> groupUserRelationEntityList=groupUserRelationRepository.findByGroupDetail(GroupDetailEntity.builder().groupIdx(notice.getRequest_group_idx()).build());

        String str=nickname+","+groupName;
        for(GroupUserRelationEntity groupUser: groupUserRelationEntityList){
            NoticeEntity noticeEntity=NoticeEntity.builder()
                    .user(UserEntity.builder().userIdx(groupUser.getUser().getUserIdx()).build())
                    .requestUserIdx(notice.getRequest_user_idx())
                    .requestGroupIdx(notice.getRequest_group_idx())
                    .requestDiaryIdx(notice.getRequest_diary_idx())
                    .content(str)
                    .build();
            noticeRepository.save(noticeEntity);
        }
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
    public void saveNoticeCheck(int noticeIdx){
        NoticeEntity noticeEntity=noticeRepository.getReferenceById(noticeIdx);
        noticeEntity.setIsChecked(true);
        noticeRepository.save(noticeEntity);
    }
}
