import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios_api from "../../config/Axios";

function CommentItem({ commentIdx, input, diaryIdx, groupIdx, userIdx }) {
  // 댓글 수정
  const [isEdit, setIsEdit] = useState(false);
  const toggleIsEdit = () => setIsEdit(!isEdit);

  const [localContent, setLocalContent] = useState(input);
  // 수정 취소 시 초기화 함수
  const handleQuitEdit = () => {
    setIsEdit(false);
    setLocalContent(input);
  };

  const navigate = useNavigate();

  return (
    <div>
      <span>작성자 정보</span>
      <div>
        {isEdit ? (
          <>
            <button onClick={handleQuitEdit}>수정 취소</button>
            <button
              onClick={() => {
                axios_api.put(`diary/comment`, {
                  userIdx: 1,
                  diaryIdx: diaryIdx,
                  groupIdx: 1,
                  content: input,
                });
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
                        if (
                          data.data.responseMessage === "일기 댓글 삭제 성공"
                        ) {
                          navigate("/", { replace: true });
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
      </div>
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
          <>{input}</>
        )}
      </div>
    </div>
  );
}

export default CommentItem;
