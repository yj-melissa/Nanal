import { useState, useEffect } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import { BookFilled, BookOutlined } from '@ant-design/icons';
import emo_joy from '../../src_assets/img/emo_joy.png';

function DiaryDetail() {
  const location = useLocation();
  const [diaryDetail, setDiaryDetail] = useState({});
  const [originGroupList, setOriginGroupList] = useState();

  const navigate = useNavigate();
  // 북마크 여부 데이터
  const [isBook, setIsBook] = useState(false);

  // 일기 상세 페이지 불러오기
  useEffect(() => {
    onLogin();
    axios_api
      .get(`diary/${location.state.diaryIdx}`)
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
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch((err) => console.log(err));
  }, []);

  // 일기 전체 댓글 리스트 조회
  const [commentList, setCommentList] = useState([]);
  useEffect(() => {
    onLogin();
    axios_api
      .get(`diary/comment/${location.state.diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setCommentList(null);
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
      })
      .catch(({ error }) => {
        console.log('일기 그룹에 해당하는 댓글 리스트 불러오기 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <div className='flex justify-between my-2'>
        <div className='text-sm'>{diaryDetail.diaryDate} 일기</div>
        {isBook ? (
          <>
            <BookFilled
              onClick={() =>
                axios_api
                  .delete(`diary/bookmark/${location.state.diaryIdx}`)
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (
                        data.data.responseMessage === '일기 북마크 삭제 성공'
                      ) {
                        setIsBook(!isBook);
                      }
                    } else {
                      console.log(data.statusCode);
                      console.log(data.data.responseMessage);
                    }
                  })
              }
            />
          </>
        ) : (
          <>
            <BookOutlined
              onClick={() =>
                axios_api
                  .get(`diary/bookmark/${location.state.diaryIdx}`)
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (
                        data.data.responseMessage === '일기 북마크 저장 성공'
                      ) {
                        setIsBook(!isBook);
                      }
                    } else {
                      console.log(data.statusCode);
                      console.log(data.data.responseMessage);
                    }
                  })
              }
            />
          </>
        )}
      </div>
      <div className='flex items-center justify-center my-5'>
        <img src={emo_joy} alt='DALL:E2' />
      </div>
      {/* <span>{diaryDetail.nickname}</span> */}
      <div className='flex items-center justify-end'>
        <Link
          to={'/Diary/Edit'}
          state={{
            diaryDetail: diaryDetail,
            originGroupList: originGroupList,
          }}
        >
          <button className='mr-2'>수정</button>
        </Link>
        <button
          onClick={() => {
            if (
              window.confirm(
                `${diaryDetail.diaryIdx}번째 일기를 정말 삭제하시겠습니까?`
              )
            ) {
              axios_api
                .delete(`diary/${location.state.diaryIdx}`)
                .then(({ data }) => {
                  if (data.statusCode === 200) {
                    if (data.data.responseMessage === '일기 삭제 성공') {
                      setDiaryDetail(data.data.diary);
                      navigate('/', { replace: true });
                    }
                  } else {
                    console.log(data.statusCode);
                    console.log(data.data.responseMessage);
                  }
                });
            }
          }}
        >
          삭제
        </button>
      </div>
      <div className='my-5 text-xl text-center'>{diaryDetail.content}</div>
      {/* 댓글 보여주는 곳 */}
      <div className='my-5'>
        {commentList.map((comment, idx) => {
          return (
            <div key={idx} className='my-2'>
              {comment.nickname} : {comment.content}
            </div>
          );
        })}
      </div>
    </div>
  );
}

DiaryDetail.defaultProps = {
  diaryDetail: [],
};

export default DiaryDetail;
