package com.dbd.nanal.config.common;

public class ResponseMessage {
    public static final String SUCCESS = "성공";
    public static final String GROUP_SAVE_SUCCESS = "그룹 생성 성공";
    public static final String GROUP_SAVE_FAIL = "그룹 생성 실패";
    public static final String GROUP_FIND_SUCCESS = "그룹 조회 성공";
    public static final String GROUP_FIND_FAIL = "그룹 조회 실패";
    public static final String GROUP_JOIN_SUCCESS = "그룹 가입 성공";
    public static final String GROUP_JOIN_FAIL = "이미 가입한 그룹";
    public static final String GROUP_LIST_FIND_SUCCESS = "그룹 리스트 조회 성공";
    public static final String GROUP_LIST_FIND_FAIL = "그룹 리스트 조회 실패";
    public static final String GROUP_UPDATE_SUCCESS = "그룹 수정 성공";
    public static final String GROUP_UPDATE_FAIL = "그룹 수정 실패";
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String LOGOUT_SUCCESS = "로그아웃 성공";
    public static final String USER_FIND_SUCCESS = "회원 정보 조회 성공";
    public static final String USER_FIND_FAIL = "회원을 찾을 수 없음";
    public static final String USER_UNAUTHROIZED = "접근 권한 없음";
    public static final String USER_CREATE_SUCCESS = "회원 가입 성공";
    public static final String USER_UPDATE_SUCCESS = "회원 정보 수정 성공";
    public static final String USER_DELETE_SUCCESS = "회원 탈퇴 성공";
    public static final String NOT_VALID_KEY = "입력 조건 불일치";
    public static final String DUPLICATED_KEY = "사용 불가";
    public static final String USUABLE_KEY = "사용 가능";
    public static final String USER_LIST_FIND_SUCCESS = "유저 목록 조회 성공";
    public static final String PASSWORD_UPDATE_SUCEESS = "비밀번호 변경 성공";

    public static final String DIARY_SAVE_SUCCESS = "일기 생성 성공";
    public static final String DIARY_SAVE_FAIL="일기 생성 실패";
    public static final String DIARY_GET_SUCCESS = "일기 조회 성공";
    public static final String DIARY_GET_FAIL = "일기 조회 실패";
    public static final String DIARY_UPDATE_SUCCESS = "일기 수정 성공";
    public static final String DIARY_UPDATE_FAIL = "일기 수정 실패";
    public static final String DIARY_DELETE_SUCCESS = "일기 삭제 성공";
    public static final String DIARY_DELETE_FAIL = "일기 삭제 성공";
    public static final String DIARY_LIST_FIND_SUCCESS = "일기 리스트 조회 성공";
    public static final String DIARY_LIST_FIND_FAIL = "일기 리스트 조회 실패";
    public static final String DIARY_COUNT_SUCCESS="일기 개수 조회 성공";
    public static final String DIARY_COUNT_FAIL="일기 개수 조회 실패";

    public static final String DIARY_COMMENT_SAVE_SUCCESS = "일기 댓글 저장 성공";
    public static final String DIARY_COMMENT_SAVE_FAIL = "일기 댓글 저장 실패";
    public static final String DIARY_COMMENT_UPDATE_SUCCESS="일기 댓글 수정 성공";
    public static final String DIARY_COMMENT_UPDATE_FAIL="일기 댓글 수정 실패";
    public static final String DIARY_COMMENT_LIST_FIND_SUCCESS = "일기 그룹에 해당하는 댓글 리스트 조회 성공";
    public static final String DIARY_COMMENT_LIST_FIND_FAIL="일기 그룹에 해당하는 댓글 리스트 조회 실패";
    public static final String DIARY_COMMENT_DELETE_SUCCESS = "일기 댓글 삭제 성공";
    public static final String DIARY_COMMENT_DELETE_FAIL = "일기 댓글 삭제 실패";
    public static final String DIARY_RETURN_SUCCESS="일기 복구 성공";
    public static final String DIARY_RETURN_FAIL="일기 복구 실패";
    public static final String TOKEN_NOT_VALID ="유효하지 않은 토큰";
    public static final String TOKEN_EXPIRED = "만료된 토큰";


    public static final String DIARY_BOOKMARK_SAVE_SUCCESS="일기 북마크 저장 성공";
    public static final String DIARY_BOOKMARK_SAVE_FAIL="일기 북마크 저장 실패";
    public static final String DIARY_BOOKMARK_LIST_SUCCESS="일기 북마크 리스트 조회 성공";
    public static final String DIARY_BOOKMARK_LIST_FAIL="일기 북마크 리스트 조회 실패";
    public static final String DIARY_BOOKMARK_DELETE_SUCCESS="일기 북마크 삭제 성공";
    public static final String DIARY_BOOKMARK_DELETE_FAIL="일기 북마크 삭제 실패";
    public static final String DIARY_BOOKMARK_COUNT_SUCCESS="일기 북마크 개수 조회 성공";
    public static final String DIARY_BOOKMARK_COUNT_FAIL="일기 북마크 개수 조회 실패";
    public static final String NOTICE_SAVE_SUCCESS="알림 저장 성공";
    public static final String NOTICE_SAVE_FAIL="알림 저장 실패";
    public static final String NOTICE_GET_SUCCESS="알림 조회 성공";
    public static final String NOTICE_GET_FAIL="알림 조회 실패";
    public static final String NOTICE_DELETE_SUCCESS="알림 삭제 성공";
    public static final String NOTICE_DELETE_FAIL="알림 삭제 실패";

    // NULL
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";

    // Exception
    public static final String EXCEPTION = "exception 발생";
    public static final String EMPTY = "EMPTY";
    public static final String RUNTIME = "RUNTIME";

    public static final String GROUP_USER_DELETE_SUCCESS = "그룹 탈퇴 성공";
    public static final String FRIEND_SAVE_SUCCESS = "친구 등록 성공";
    public static final String FRIEND_SAVE_FAIL = "친구 등록 실패";
    public static final String FRIEND_LIST_FIND_SUCCESS = "친구 리스트 조회 성공";
    public static final String FRIEND_LIST_FIND_FAIL = "친구 리스트 조회 실패";
    public static final String FRIEND_FIND_SUCCESS = "친구 조회 성공";
    public static final String FRIEND_FIND_SUCCESS2 = "이미 친구로 등록됨";
    public static final String FRIEND_FIND_FAIL = "친구 조회 실패";
    public static final String NONE_DATA = "데이터 없음";
    public static final String GROUP_USER_FIND_SUCCESS = "그룹 유저 조회 성공";
    public static final String EMAIL_SEND_SUCCESS = "이메일 발송 성공";
    public static final String EMAIL_SEND_FAIL = "이메일 발송 실패";
    public static final Object PAINTING_SAVE_SUCCESS = "그림 저장 성공";
    public static final Object PAINTING_SAVE_FAIL = "그림 저장 실패";
    public static final String PAINTING_UPDATE_SUCCESS = "그림 저장 성공";
    public static final String PAINTING_UPDATE_FAIL = "그림 저장 실패";
    public static final String EMOTION_FRIEND_LIST_FIND_SUCCESS = "감정 조회 성공";
    public static final String EMOTION_FRIEND_LIST_FIND_FAIL = "감정 조회 실패";
}
