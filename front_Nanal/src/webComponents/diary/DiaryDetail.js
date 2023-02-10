import { useState, useEffect } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import Swal from 'sweetalert2';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
// import CommentList from './CommentList';
import emo_joy from '../../src_assets/img/emotion/emo_joy.png';
import bookmark from '../../src_assets/img/bookmark.png';
import bookmark_filled from '../../src_assets/img/bookmark_fill.png';

function DiaryDetail() {
  // const location = useLocation();
  const { state } = useLocation();
  const navigate = useNavigate();
  const diaryIdx = state.diaryIdx;
  const isToggle = state.isToggle;
  const groupIdx = state.groupIdx;

  const [diaryDetail, setDiaryDetail] = useState({});
  const [originGroupList, setOriginGroupList] = useState();

  // 북마크 여부 데이터
  const [isBook, setIsBook] = useState(false);

  // 북마크 저장 함수
  const bookmarkSave = () => {
    axios_api
      .get(`diary/bookmark/${diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 북마크 저장 성공') {
            setIsBook(!isBook);
          }
        } else {
          console.log('일기 북마크 저장 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 북마크 저장 오류: ' + error);
      });
  };

  // 북마크 삭제 함수
  const bookmarkDelete = () => {
    axios_api
      .delete(`diary/bookmark/${diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 북마크 삭제 성공') {
            setIsBook(!isBook);
          }
        } else {
          console.log('일기 북마크 삭제 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 북마크 삭제 오류: ' + error);
      });
  };

  // 일기 삭제 함수
  const diaryDelete = () => {
    axios_api
      .delete(`diary/${diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 삭제 성공') {
            setDiaryDetail(data.data.diary);
            navigate('/', { replace: true });
          }
        } else {
          console.log('일기 삭제 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 삭제 오류: ' + error);
      });
  };

  // 일기 상세 페이지 불러오기
  useEffect(() => {
    onLogin();
    axios_api
      .get(`diary/${diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 조회 성공') {
            setDiaryDetail(data.data.diary);
            setOriginGroupList(data.data.groupList);

            if (data.data.isBookmark === true) {
              setIsBook(!isBook);
            }
          }
        } else {
          console.log('일기 조회 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 조회 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <div className='flex justify-between my-2'>
        <strong>{diaryDetail.diaryDate}일</strong>
        {/* 감정 넣는 곳 */}
        <span>{diaryDetail.emo}</span>
        {isBook ? (
          <img
            src={bookmark_filled}
            alt='bookmark_filled'
            onClick={bookmarkDelete}
            className='w-1/12'
          />
        ) : (
          <img
            src={bookmark}
            alt='bookmark'
            onClick={() => bookmarkSave}
            className='w-1/12'
          />
        )}
      </div>
      <div className='flex items-center justify-center my-5'>
        <img src={emo_joy} alt='DALL:E2' />
      </div>
      {/* <span>{diaryDetail.nickname}</span> */}
      <div className='flex items-center justify-end my-10'>
        <Link
          to={'/Diary/Edit'}
          state={{
            diaryDetail: diaryDetail,
            originGroupList: originGroupList,
          }}
        >
          <button className='hover:bg-sky-700 bg-cyan-600 text-white px-2.5 py-1 rounded-3xl m-auto block'>
            수정
          </button>
        </Link>
        <button
          className='bg-rose-600 text-white px-2.5 py-1 rounded-3xl mx-2 inline-block'
          onClick={() => {
            Swal.fire({
              title: `일기를 정말 삭제하시겠습니까?`,
              text: '삭제한 일기는 휴지통에서 확인 가능합니다.',
              icon: 'warning',
              showCancelButton: true,
              confirmButtonColor: '#3085d6',
              cancelButtonColor: '#d33',
              confirmButtonText: '삭제',
              cancelButtonText: '취소',
            }).then((result) => {
              if (result.isConfirmed) {
                diaryDelete();
              }
            });
          }}
        >
          삭제
        </button>
      </div>
      <div className='my-10 text-xl text-left underline underline-offset-8'>
        {diaryDetail.content}
      </div>
      {/* 댓글 보여주는 곳 */}
      {/* <div className='my-5'>
        {commentList.map((comment, idx) => {
          return (
            <div key={idx} className='my-2'>
              {comment.nickname} : {comment.content}
            </div>
          );
        })}
      </div> */}
      {/* <div className='my-5'>
        <CommentList
          diaryIdx={diaryIdx}
          isToggle={isToggle}
          groupIdx={groupIdx}
        />
      </div> */}
    </div>
  );
}

DiaryDetail.defaultProps = {
  diaryDetail: [],
};

export default DiaryDetail;
