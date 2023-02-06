import { useState, useRef, useEffect } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import CommentDetail from './CommentDetail';

function Comment({ diaryIdx, groupIdx }) {
  // 댓글 내용
  const [content, setContent] = useState('');
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
      .post('diary/comment', {
        diaryIdx: diaryIdx,
        groupIdx: groupIdx,
        content: content,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 댓글 저장 성공') {
            axios_api
              .get(`diary/comment/${groupIdx}/${diaryIdx}`)
              .then(({ data }) => {
                if (data.statusCode === 200) {
                  setDiaryComment(null);
                  if (
                    data.data.responseMessage ===
                    '일기 그룹에 해당하는 댓글 리스트 조회 성공'
                  ) {
                    setDiaryComment(data.data.diaryComment);
                  }
                } else {
                  console.log('일기 그룹에 해당하는 댓글 리스트 조회 실패 : ');
                  console.log(data.statusCode);
                  console.log(data.data.responseMessage);
                }
              })
              .catch((err) => console.log(err));

            // 저장 후 댓글 데이터 초기화
            setContent('');
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
    onLogin();
    axios_api
      .get(`diary/comment/${groupIdx}/${diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setDiaryComment(null);
          if (
            data.data.responseMessage ===
            '일기 그룹에 해당하는 댓글 리스트 조회 성공'
          ) {
            setDiaryComment(data.data.diaryComment);
          }
        } else {
          console.log('일기 그룹에 해당하는 댓글 리스트 조회 실패 : ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <div className='comment-container'>
      <div className='comments-body'>
        {diaryComment.map((comment, idx) => (
          <CommentDetail key={idx} item={comment} />
        ))}
      </div>
      <form className='comment-wrap' onSubmit={handleSubmit}>
        <input
          type='text'
          ref={commentRef}
          placeholder='댓글을 입력하세요'
          value={content}
          onChange={onChange}
        />
        <button type='submit'>등록</button>
      </form>
    </div>
  );
}

export default Comment;
