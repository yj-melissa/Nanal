import { useState, useRef } from 'react';
import axios_api from '../../config/Axios';
import CommentItem from './CommentItem';

function Comment({ diaryIdx, groupIdx }) {
  // 댓글 데이터
  const [input, setInput] = useState('');
  const onChange = (e) => setInput(e.target.value);
  // 포커싱 기능
  const commentRef = useRef();

  // 등록 버튼 누르면 실행되는 함수
  const handleSubmit = (e) => {
    e.preventDefault();
    if (input.length < 1) {
      commentRef.current.focus();
      return;
    }
    axios_api
      .post('diary/comment', {
        userIdx: 1,
        diaryIdx: diaryIdx,
        groupIdx: 1,
        content: input,
      })
      .then((response) => {
        console.log(response);
      })
      .catch((err) => {
        console.log(err);
      });
    // 저장 후 댓글 데이터 초기화
    setInput('');
  };

  return (
    <div className='comment-container'>
      <div>
        <CommentItem response={response} />
      </div>
      <form className='comment-wrap' onClick={handleSubmit}>
        <input
          type='text'
          ref={commentRef}
          placeholder='댓글을 입력하세요'
          value={input}
          onChange={onChange}
        />
        <button>등록</button>
      </form>
    </div>
  );
}

export default Comment;
