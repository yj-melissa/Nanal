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
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String str="[친구 초대] "+ nickname+"님으로부터의 친구 등록 요청입니다.";

        for(int userIdx: notice.getUserIdx()) {
            NoticeEntity noticeEntity = NoticeEntity.builder()
                    .user(UserEntity.builder().userIdx(userIdx).build())
                    .requestUserIdx(notice.getRequest_user_idx())
                    .content(str)
                    .build();
            noticeRepository.save(noticeEntity);
        }
    }

    //save group notice
    public void saveGroupNotice(NotificationRequestDTO notice){
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String groupName=groupRepository.getReferenceById(notice.getRequest_group_idx()).getGroupName();

        String str="[그룹 초대] "+nickname+"님이 "+groupName+" 그룹 일기에 초대했습니다.";

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
        DiaryEntity diaryEntity=diaryRepository.getReferenceById(notice.getRequest_diary_idx());
        String userName=userProfileRepository.getReferenceById(diaryEntity.getUser().getUserIdx()).getNickname();
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();

        String str="[댓글] "+ userName +"님의 일기에 "+ nickname+"님이 댓글을 작성하셨습니다.";

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

        String str="[새글] "+nickname+"님이 "+groupName+" 그룹에 새 글을 등록하였습니다.";
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
        List<NoticeEntity> noticeEntityList=noticeRepository.findByUserAndIsChecked(UserEntity.builder().userIdx(userIdx).build(), false);
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
