import { useEffect, useState } from "react";
import axios_api from "../../config/Axios";
import CommentItem from "./CommentItem";

function CommentList({ diaryIdx, groupIdx }) {
  // 댓글 데이터 받기
  const [commentList, setCommentList] = useState([]);

  useEffect(() => {
    // 그룹 Idx 는 추후 진행 예정
    axios_api
      .get(`diary/comment/1/${diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (
            data.data.responseMessage ===
            "일기 그룹에 해당하는 댓글 리스트 조회 성공"
          ) {
            setCommentList(data.data.diaryComment);
          } else {
            console.log(data.statusCode);
            console.log(data.data.responseMessage);
          }
        }
      })
      .catch(({ err }) => {
        console.log("일기 그룹에 해당하는 댓글 리스트 조회 오류: ", err);
      });
  }, []);

  return (
    <div>
      {commentList.map((comments) => (
        <CommentItem key={comments.commentIdx} {...comments} />
      ))}
    </div>
  );
}

export default CommentList;
