import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onWebLogin } from '../../config/Login';
import DiaryItem from './DiaryItem';

function DiaryList({ isToggle, curDate, groupIdx }) {
  const { state } = useLocation();

  if (state !== null) {
    isToggle = state.isToggle;
  }

  const arrAxios = [
    `diary/list/date/${curDate}`,
    `diary/list/user`,
    `diary/list/${groupIdx}`,
  ];

  // 일기 데이터 받기
  const [diaryList, setDiaryList] = useState([]);

  useEffect(() => {
    onWebLogin();
    axios_api
      .get(arrAxios[isToggle])
      .then(({ data }) => {
        if (data.statusCode === 200) {
          // 초기화 필요!
          setDiaryList(null);
          if (data.data.responseMessage === '일기 리스트 조회 성공') {
            console.log(data.data.diary);
            setDiaryList(data.data.diary);
          }
        } else {
          console.log('일기 리스트 조회 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 리스트 불러오기 오류: ', error);
      });
  }, [curDate]);

  return (
    <div className='DiaryList'>
      {isToggle === 0 ? (
        <p className='text-2xl font-bold text-center'>
          {diaryList.length}개의 일기가 있습니다.
        </p>
      ) : isToggle === 1 ? (
        <p className='my-5 text-2xl font-bold text-center'>
          내가 쓴 일기 개수는 총 {diaryList.length}개 입니다.
        </p>
      ) : (
        <></>
      )}
      <div className='my-5'>
        {diaryList.map((diary) => (
          <DiaryItem
            key={diary.diaryIdx}
            isToggle={isToggle}
            groupIdx={groupIdx}
            {...diary}
          />
        ))}
      </div>
    </div>
  );
}

DiaryList.defaultProps = {
  diaryList: [],
};

export default DiaryList;
