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


    //save group notice
    public void saveGroupNotice(NotificationRequestDTO notice){
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String groupName=groupRepository.getReferenceById(notice.getRequest_group_idx()).getGroupName();

        String str=nickname+"님이 "+groupName+" 그룹 일기에 초대했습니다.";

        for(int userIdx: notice.getUserIdx()){
            NoticeEntity noticeEntity=NoticeEntity.builder()
                    .user(UserEntity.builder().userIdx(userIdx).build())
                    .requestUserIdx(notice.getRequest_user_idx())
                    .requestGroupIdx(notice.getRequest_group_idx())
                    .requestDiaryIdx(notice.getRequest_diary_idx())
                    .content(str)
                    .build();
            noticeRepository.save(noticeEntity);
        }
    }

    //save comment notice
    public void saveCommentNotice(NotificationRequestDTO notice){
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String groupName=groupRepository.getReferenceById(notice.getRequest_group_idx()).getGroupName();
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(notice.getRequest_diary_idx());
        String diaryDate=diaryEntity.getCreationDate().toString();

        String str=nickname+"님이 "+groupName+"의 "+diaryDate+"일기에 새로운 댓글을 남겼습니다.";

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

        String str=nickname+"님이 "+groupName+" 그룹에 "+" 새 글을 등록하였습니다.";
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
        List<NoticeEntity> noticeEntityList=noticeRepository.findByUser(UserEntity.builder().userIdx(userIdx).build());
        return noticeEntityList.stream().map(x->new NotificationResponseDTO(x)).collect(Collectors.toList());
    }

    //delete notice
    public void deleteNotice( int noticeIdx){
        noticeRepository.deleteById(noticeIdx);
    }
}
