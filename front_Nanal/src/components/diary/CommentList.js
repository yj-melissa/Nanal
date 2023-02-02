import { useState, useEffect } from "react";
import axios_api from "../../config/Axios";
import CommentItem from "./CommentItem";

function CommentList({ diaryIdx, groupIdx }) {
  // 댓글 리스트 데이터 받기
  const [diaryComment, setDiaryComment] = useState([]);

  // 디테일 페이지에서 댓글 있으면 보여줘야 함
  useEffect(() => {
    axios_api.get(`diary/comment/1/${diaryIdx}`).then(({ data }) => {
      if (data.statusCode === 200) {
        if (
          data.data.responseMessage ===
          "일기 그룹에 해당하는 댓글 리스트 조회 성공"
        ) {
          setDiaryComment(data.data.diaryComment);
        }
      } else {
        console.log(data.statusCode);
        console.log(data.data.responseMessage);
      }
    });
  }, [diaryIdx, diaryComment]);

  return (
    <div>
      {diaryComment.map((comment) => (
        <CommentItem key={comment.commentIdx} {...comment} />
      ))}
    </div>
  );
}

export default CommentList;
