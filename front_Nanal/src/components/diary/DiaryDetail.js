import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { BookFilled, BookOutlined } from '@ant-design/icons';

const getStringDate = (date) => {
  return date.toISOString().slice(0, 10);
};

function DiaryDetail() {
  const location = useLocation();
  const [diaryDetail, setDiaryDetail] = useState({});

  // 작성 일자 표현법
  const strDate = new Date(diaryDetail.diaryDate).toLocaleString().slice(0, 10);
  // 삭제 버튼 클릭 시 URL 이동
  const navigate = useNavigate();
  // 일기 수정
  const [isEdit, setIsEdit] = useState(false);
  const toggleIsEdit = () => {
    setIsEdit(!isEdit);
    setLocalContent(diaryDetail.content);
  };
  // 수정된 날짜 데이터
  const [localDate, setLocalDate] = useState(getStringDate(new Date()));
  // 수정된 일기 데이터
  const [localContent, setLocalContent] = useState('');

  const handleQuitEdit = () => {
    setIsEdit(false);
    setLocalContent(diaryDetail.content);
  };
  // 북마크 여부 데이터
  const [isBook, setIsBook] = useState(false);

  // 일기 상세 페이지 불러오기
  useEffect(() => {
    axios_api
      .get(`diary/${location.state.diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 조회 성공') {
            setDiaryDetail(data.data.diary); // 데이터는 response.data.data 안에 들어있음
            console.log(data);
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

  return (
    <div>
      <div className='flex justify-between'>
        <span>그림 들어갈 자리</span>
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
      <div>작성자 프로필 사진 | </div>
      <span>{diaryDetail.nickname}</span>
      <div>
        {isEdit ? (
          <>
            <button className='border-rose-500' onClick={handleQuitEdit}>
              수정 취소
            </button>
            <button
              onClick={() => {
                axios_api
                  .put('diary', {
                    userIdx: diaryDetail.userIdx,
                    diaryIdx: diaryDetail.diaryIdx,
                    content: localContent,
                    diaryDate: diaryDetail.diaryDate,
                  })
                  .then(({ data }) => {
                    setDiaryDetail(data.data.diary);
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
            <button onClick={toggleIsEdit}>수정</button>
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
          </>
        )}
      </div>
      <div>
        {isEdit ? (
          <>
            <input
              value={localDate}
              onChange={(e) => setLocalDate(e.target.value)}
              type='date'
            />
            <textarea
              value={localContent}
              onChange={(e) => setLocalContent(e.target.value)}
            />
          </>
        ) : (
          <>
            <div className='text-sm'>{strDate}</div>
            {diaryDetail.content}
          </>
        )}
      </div>
    </div>
  );
}

DiaryDetail.defaultProps = {
  diaryDetail: [],
};

export default DiaryDetail;
