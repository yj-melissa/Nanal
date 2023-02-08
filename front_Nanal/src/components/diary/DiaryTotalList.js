import { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import DiaryTotalItem from './DiaryTotalItem';

function DiaryTotalList() {
  // 전체 일기 데이터 받기
  const [totalDiary, setTotalDiary] = useState([]);

  useEffect(() => {
    axios_api.get('diary/list/user').then(({ data }) => {
      if (data.statusCode === 200) {
        setTotalDiary(null);
        if (data.data.responseMessage === '일기 리스트 조회 성공') {
          setTotalDiary(data.data.diary);
        }
      } else {
        console.log(data.statusCode);
        console.log(data.data.responseMessage);
      }
    });
  }, []);

  return (
    <div>
      <h4 className='text-center font-bold my-5'>
        내가 쓴 일기 개수는 총 {totalDiary.length}개 입니다.
      </h4>
      <div>
        {totalDiary.map((diary, idx) => (
          <DiaryTotalItem key={idx} {...diary} />
        ))}
      </div>
    </div>
  );
}

export default DiaryTotalList;
