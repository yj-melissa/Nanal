package com.dbd.nanal.service;

import com.dbd.nanal.dto.NotificationRequestDTO;
import com.dbd.nanal.model.NoticeEntity;
import com.dbd.nanal.model.UserEntity;
import com.dbd.nanal.repository.DiaryRepository;
import com.dbd.nanal.repository.GroupRepository;
import com.dbd.nanal.repository.NoticeRepository;
import com.dbd.nanal.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserProfileRepository userProfileRepository;
    private final GroupRepository groupRepository;
    private final DiaryRepository diaryRepository;

    public void saveNotice(NotificationRequestDTO notice){
        String str;
        String nickname=userProfileRepository.getReferenceById(notice.getRequest_user_idx()).getNickname();
        String groupName=groupRepository.getReferenceById(notice.getRequest_group_idx()).getGroupName();
        String diaryDate="";
        if(notice.getRequest_diary_idx()!=0){
            diaryDate=diaryRepository.getReferenceById(notice.getRequest_diary_idx()).getCreationDate().toString();
        }
        // group 초대
        if(notice.getNotice_type()==0){
            str=nickname+"님이 "+groupName+" 그룹 일기에 초대했습니다.";
        }
        // 그룹 새 댓글
        else{
            str=nickname+"님이 "+groupName+"의 "+diaryDate+"일기에 새로운 댓글을 남겼습니다.";
        }
        NoticeEntity noticeEntity=NoticeEntity.builder()
                .user(UserEntity.builder().userIdx(notice.getUserIdx()).build())
                .requestUserIdx(notice.getRequest_user_idx())
                .requestGroupIdx(notice.getRequest_group_idx())
                .requestDiaryIdx(notice.getRequest_diary_idx())
                .content(str)
                .build();
        noticeRepository.save(noticeEntity);
    }
}
