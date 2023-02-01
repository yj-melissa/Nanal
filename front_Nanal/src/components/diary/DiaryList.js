import { useState, useEffect } from 'react';
import DiaryItem from './DiaryItem';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function DiaryList({ curDate }) {
  // 일기 데이터 받기
  const [diaryList, setDiaryList] = useState([]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`diary/list/date/${curDate}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          // 초기화 필요!
          setDiaryList(null);
          if (data.data.responseMessage === '일기 리스트 조회 성공') {
            setDiaryList(data.data.diary); // 데이터는 response.data.data 안에 들어있음
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ err }) => {
        console.log('일기 리스트 불러오기 오류: ', err);
      });
  }, [curDate]);

  return (
    <div className='DiaryList'>
      <h4>
        {curDate} | {diaryList.length}개의 일기가 있습니다.
      </h4>
      <div>
        {diaryList.map((diary) => (
          <DiaryItem key={diary.diaryIdx} {...diary} />
        ))}
      </div>
    </div>
  );
}

DiaryList.defaultProps = {
  diaryList: [],
};

export default DiaryList;
