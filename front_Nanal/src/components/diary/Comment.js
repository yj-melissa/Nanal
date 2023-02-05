import { useState, useRef, useEffect } from "react";
import axios_api from "../../config/Axios";

function Comment({ diaryIdx }) {
  // 댓글 내용
  const [content, setContent] = useState("");
  const onChange = (e) => setContent(e.target.value);
  // 포커싱 기능
  const commentRef = useRef();

  // 댓글 리스트 데이터 받기
  const [diaryComment, setDiaryComment] = useState([]);

  // 등록 버튼 누르면 실행되는 함수
  const handleSubmit = (e) => {
    e.preventDefault();
    if (content.length < 1) {
      commentRef.current.focus();
      return;
    }
    axios_api
      .post("diary/comment", {
        diaryIdx: diaryIdx,
        content: content,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === "일기 댓글 저장 성공") {
            // 저장 후 댓글 데이터 초기화
            setContent("");
            axios_api
              .get(`diary/comment/1/${diaryIdx}`)
              .then(({ data }) => {
                if (data.statusCode === 200) {
                  if (
                    data.data.responseMessage ===
                    "일기 그룹에 해당하는 댓글 리스트 조회 성공"
                  ) {
                    setDiaryComment(data.data.diaryComment);
                  }
                } else {
                  console.log("일기 그룹에 해당하는 댓글 리스트 조회 실패 : ");
                  console.log(data.statusCode);
                  console.log(data.data.responseMessage);
                }
              })
              .catch((err) => console.log(err));
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch((err) => console.log(err));
  };

  // 디테일 페이지에서 댓글 있으면 보여줘야 함
  useEffect(() => {
    axios_api
      .get(`diary/comment/1/${diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (
            data.data.responseMessage ===
            "일기 그룹에 해당하는 댓글 리스트 조회 성공"
          ) {
            setDiaryComment(data.data.diaryComment);
          }
        } else {
          console.log("일기 그룹에 해당하는 댓글 리스트 조회 실패 : ");
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch((err) => console.log(err));
  }, []);

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
    <div className="comment-container">
      <div className="comments-body">
        {diaryComment.map((comment) => {
          {
            isEdit ? (
              <>
                <button onClick={handleQuitEdit}>수정 취소</button>
                <button
                  onClick={() => {
                    axios_api
                      .put(`diary/comment`, {
                        commentIdx: comment.commentIdx,
                        content: localContent,
                      })
                      .then(({ data }) => {
                        if (data.statusCode === 200) {
                          if (
                            data.data.responseMessage === "일기 댓글 수정 성공"
                          ) {
                            setCommentDetail(data.data.diaryComment);
                            setIsEdit(false);
                          }
                        } else {
                          console.log("일기 댓글 수정 실패 : ");
                          console.log(data.statusCode);
                          console.log(data.data.responseMessage);
                        }
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
                        `${comment.commentIdx}번째 댓글을 정말 삭제하시겠습니까?`
                      )
                    ) {
                      axios_api
                        .delete(`diary/comment/${comment.commentIdx}`)
                        .then(({ data }) => {
                          if (data.statusCode === 200) {
                            if (
                              data.data.responseMessage ===
                              "일기 댓글 삭제 성공"
                            ) {
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
            );
          }
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
          </div>;
        })}
      </div>
      <form className="comment-wrap" onSubmit={handleSubmit}>
        <input
          type="text"
          ref={commentRef}
          placeholder="댓글을 입력하세요"
          value={content}
          onChange={onChange}
        />
        <button type="submit">등록</button>
      </form>
    </div>
  );
}

export default Comment;
