import { useState } from 'react';
import axios_api from '../../config/Axios';
import Swal from 'sweetalert2';

function CommentDetail({ item, userIdx }) {
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

  // 댓글 수정 함수
  const commentModify = () => {
    axios_api
      .put('diary/comment', {
        commentIdx: item.commentIdx,
        content: localContent,
      })
      .then(({ data }) => {
        if (data.data.responseMessage === '일기 댓글 수정 성공') {
          setCommentDetail(data.data.diaryComment.content);
          setIsEdit(false);
        } else {
          console.log('일기 댓글 수정 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 댓글 수정 오류: ' + error);
      });
  };

  // 댓글 삭제 함수
  const commentDelete = () => {
    axios_api
      .delete(`diary/comment/${item.commentIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 댓글 삭제 성공') {
            window.location.reload();
          }
        } else {
          console.log('일기 댓글 삭제 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 댓글 삭제 오류: ' + error);
      });
  };

  return (
    <div className='m-1 my-2'>
      {isEdit ? (
        <div>
          <div className='flex items-center justify-between mx-1'>
            <div>
              <img
                src={item.img}
                alt='UserProfielImage'
                className='w-1/12 m-1 rounded-lg h-1/12'
              />
              <span className='text-sm font-bold mr-'>{item.nickname}</span>
            </div>
            <div className='m-1 text-sm'>
              <button
                onClick={handleQuitEdit}
                className='bg-rose-100 text-rose-700 text-sm font-bold rounded-3xl px-2.5 py-1  whitespace-nowrap'
              >
                취소
              </button>
              <button
                onClick={commentModify}
                className='ml-2 bg-violet-100 text-violet-700 text-sm font-bold rounded-3xl px-2.5 py-1 whitespace-nowrap cursor-pointer'
              >
                등록
              </button>
            </div>
          </div>
          <input
            type='text'
            value={localContent}
            onChange={(e) => setLocalContent(e.target.value)}
            className='w-full p-1.5 rounded-md'
          />
        </div>
      ) : (
        <div>
          <div className='flex items-center'>
            <img
              src={item.img}
              alt='DALL:E2'
              className='w-1/12 m-1 rounded-lg h-1/12'
            />

            {userIdx === item.userIdx ? (
              <div className='mx-1 text-sm w-full'>
                <span className='ml-2 font-bold text-center'>
                  {item.nickname}
                </span>
                <div className='flex justify-between items-center'>
                  <div>
                    <p className='px-1 text-sm font-bold'>{commentDetail}</p>
                  </div>
                  <div className='place-content-end'>
                    <button
                      className='m-1 bg-violet-100 text-violet-700 rounded-3xl cursor-pointer px-2.5 py-1 whitespace-nowrap font-bold'
                      onClick={toggleIsEdit}
                    >
                      수정
                    </button>
                    <button
                      className='ml-1 bg-rose-100 text-rose-700 px-2.5 py-1 rounded-3xl whitespace-nowrap font-bold'
                      onClick={() => {
                        Swal.fire({
                          icon: 'warning',
                          text: '댓글을 정말 삭제하시겠습니까?',
                          showCancelButton: true,
                          confirmButtonColor: '#3085d6',
                          cancelButtonColor: '#d33',
                          confirmButtonText: '삭제',
                          cancelButtonText: '취소',
                        }).then((result) => {
                          if (result.isConfirmed) {
                            commentDelete();
                          }
                        });
                      }}
                    >
                      삭제
                    </button>
                  </div>
                </div>
              </div>
            ) : (
              <p className='float-right mx-1 my-2 text-sm'>&nbsp;</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default CommentDetail;
