import { useState } from 'react';
import axios_api from '../../config/Axios';
import emo_joy from '../../src_assets/img/emo_joy.png';
import Swal from 'sweetalert2';

function CommentDetail({ item }) {
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

  return (
    <div>
      {isEdit ? (
        <>
          <button onClick={handleQuitEdit}>수정 취소</button>
          <button
            onClick={() => {
              axios_api
                .put('diary/comment', {
                  commentIdx: item.commentIdx,
                  content: localContent,
                })
                .then(({ data }) => {
                  setCommentDetail(data.data.diaryComment.content);
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
          <div className='text-right'>
            <div className='flex items-center p-2 m-1 mb-3'>
              <img
                src={emo_joy}
                alt='DALL:E2'
                className='w-16 h-16 p-1 rounded-lg'
              />
              <div className='px-1 m-1 text-sm text-left'>
                <div className='font-bold text-sm'>{item.nickname}</div>
                <button
                  className='mr-2 bg-violet-100 text-violet-700 rounded-3xl cursor-pointer px-2.5 py-1 whitespace-nowrap font-bold'
                  onClick={toggleIsEdit}
                >
                  수정
                </button>
                <button
                  className='ml-2 bg-rose-600 text-white px-2.5 py-1 rounded-3xl'
                  onClick={() => {
                    Swal.fire({
                      title: `댓글을 정말 삭제하시겠습니까?`,
                      text: '삭제한 댓글은 다시 확인할 수 없습니다.',
                      icon: 'warning',
                      showCancelButton: true,
                      confirmButtonColor: '#3085d6',
                      cancelButtonColor: '#d33',
                      confirmButtonText: '삭제',
                      cancelButtonText: '취소',
                    }).then((result) => {
                      if (result.isConfirmed) {
                        axios_api
                          .delete(`diary/comment/${item.commentIdx}`)
                          .then(({ data }) => {
                            if (data.statusCode === 200) {
                              if (
                                data.data.responseMessage ===
                                '일기 댓글 삭제 성공'
                              ) {
                                window.location.reload();
                              }
                            } else {
                              console.log(data.statusCode);
                              console.log(data.data.responseMessage);
                            }
                          })
                          .catch((err) => console.log(err));
                      }
                    });
                  }}
                >
                  삭제
                </button>
              </div>
            </div>
            <p className='text-left text-lg font-bold'>{commentDetail}</p>
          </div>
        </>
      )}
      <div>
        {isEdit ? (
          <>
            <input
              type='text'
              value={localContent}
              onChange={(e) => setLocalContent(e.target.value)}
            />
          </>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}

export default CommentDetail;
