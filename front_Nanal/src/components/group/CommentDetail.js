import { useState } from 'react';
import axios_api from '../../config/Axios';

function CommentDetail({ item }) {
  // 댓글 상세 데이터 받기
  const [commentDetail, setCommentDetail] = useState(item.content);
  // 댓글 수정
  const [isEdit, setIsEdit] = useState(false);
  const toggleIsEdit = () => {
    setIsEdit(!isEdit);
    setLocalContent(localContent);
  };
  const [localContent, setLocalContent] = useState(item.content);
  // 수정 취소 시 초기화 함수
  const handleQuitEdit = () => {
    setIsEdit(false);
    setLocalContent(item.content);
  };

  return (
    <div>
      {isEdit ? (
        <>
          <button className='border-rose-500' onClick={handleQuitEdit}>
            수정 취소
          </button>
          <button
            onClick={() => {
              axios_api
                .put('diary/comment', {
                  commentIdx: item.commentIdx,
                  content: localContent,
                })
                .then(({ data }) => {
                  setCommentDetail(data.data.diaryComment.content);
                  setIsEdit(false);
                })
                .catch((err) => console.log(err));
            }}
          >
            수정 완료
          </button>
        </>
      ) : (
        <>
          <button onClick={toggleIsEdit}>수정</button>
          <button
            onClick={() => {
              if (
                window.confirm(
                  `${item.commentIdx}번째 댓글을 정말 삭제하시겠습니까?`
                )
              ) {
                axios_api
                  .delete(`diary/comment/${item.commentIdx}`)
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (data.data.responseMessage === '일기 댓글 삭제 성공') {
                        return;
                      }
                    } else {
                      console.log(data.statusCode);
                      console.log(data.data.responseMessage);
                    }
                  });
              }
            }}
          >
            삭제
          </button>
        </>
      )}
      <div>
        {isEdit ? (
          <>
            <input
              type='text'
              value={localContent}
              onChange={(e) => setLocalContent(e.target.value)}
            />
          </>
        ) : (
          <>
            <div>{item.nickname}</div>
            <div>{commentDetail}</div>
          </>
        )}
      </div>
    </div>
  );
}

export default CommentDetail;
