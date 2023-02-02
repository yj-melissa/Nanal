import { useState, useRef, useEffect } from "react";
import axios_api from "../../config/Axios";
import CommentList from "./CommentList";

function Comment({ diaryIdx, groupIdx }) {
  // 댓글 내용
  const [content, setContent] = useState("");
  const onChange = (e) => setContent(e.target.value);
  // 포커싱 기능
  const commentRef = useRef();

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
        groupIdx: 1,
        content: content,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === "일기 댓글 저장 성공") {
            alert("댓글 작성 완료");
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      });
    // 저장 후 댓글 데이터 초기화
    setContent("");
  };

  return (
    <div className="comment-container">
      <div className="comments-body">
        <CommentList diaryIdx={diaryIdx} groupIdx={groupIdx} />
      </div>
      <form className="comment-wrap" onClick={handleSubmit}>
        <input
          type="text"
          ref={commentRef}
          placeholder="댓글을 입력하세요"
          value={content}
          onChange={onChange}
        />
        <button onClick={handleSubmit}>등록</button>
      </form>
    </div>
  );
}

export default Comment;
