import { useState, useRef, useEffect } from 'react';
import axios_api from '../../config/Axios';
import CommentItem from './CommentItem';

function Comment({ diaryIdx, groupIdx }) {
  // 댓글을 모으는 리스트
  const [commentList, setCommentList] = useState([]);
  // 댓글 내용
  const [content, setContent] = useState('');
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
      .post('diary/comment', {
        diaryIdx: diaryIdx,
        groupIdx: groupIdx,
        content: content,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 댓글 저장 성공') {
            setCommentList(data.data.diaryComment);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      });
    // 저장 후 댓글 데이터 초기화
    setContent('');
  };
  console.log(commentList);
  // 디테일 페이지에서 댓글 있으면 보여줘야 함
  useEffect(() => {
    axios_api.get(`diary/comment//${diaryIdx}`).then(({ data }) => {
      if (data.statusCode === 200) {
        if (
          data.data.responseMessage ===
          '일기 그룹에 해당하는 댓글 리스트 조회 성공'
        ) {
          setCommentList(data.data.diaryComment);
        }
      } else {
        console.log(data.statusCode);
        console.log(data.data.responseMessage);
      }
    });
  }, []);

  return (
    <div className='comment-container'>
      <div className='comments-body'>
        {commentList.map((comment, idx) => (
          <CommentItem key={idx} {...comment} />
        ))}
      </div>
      <form className='comment-wrap' onClick={handleSubmit}>
        <input
          type='text'
          ref={commentRef}
          placeholder='댓글을 입력하세요'
          value={content}
          onChange={onChange}
        />
        <button onClick={handleSubmit}>등록</button>
      </form>
    </div>
  );
}

export default Comment;
