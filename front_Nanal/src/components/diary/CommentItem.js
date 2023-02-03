import { useState } from "react";
import axios_api from "../../config/Axios";

function CommentItem({ diaryIdx, groupIdx, content, commentIdx }) {
  // 댓글 상세 데이터 받기
  const [commentDetail, setCommentDetail] = useState({});
  // 댓글 수정
  const [isEdit, setIsEdit] = useState(false);
  const toggleIsEdit = () => {
    setIsEdit(!isEdit);
    setLocalContent(localContent);
  };

  const [localContent, setLocalContent] = useState(content);
  // 수정 취소 시 초기화 함수
  const handleQuitEdit = () => {
    setIsEdit(false);
    setLocalContent(content);
  };

  return (
    <div>
      {isEdit ? (
        <>
          <button onClick={handleQuitEdit}>수정 취소</button>
          <button
            onClick={() => {
              axios_api
                .put(`diary/comment`, {
                  commentIdx: commentIdx,
                  content: localContent,
                })
                .then(({ data }) => {
                  setCommentDetail(data.data.diaryComment);
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
                  `${commentIdx}번째 댓글을 정말 삭제하시겠습니까?`
                )
              ) {
                axios_api
                  .delete(`diary/comment/${commentIdx}`)
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (data.data.responseMessage === "일기 댓글 삭제 성공") {
                        return;
                      }
                    } else {
                      console.log("일기 댓글 삭제 실패 : ");
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
            <form>
              <input
                type="text"
                value={localContent}
                onChange={(e) => setLocalContent(e.target.value)}
              />
            </form>
          </>
        ) : (
          <>{localContent}</>
        )}
      </div>
    </div>
  );
}

export default CommentItem;
