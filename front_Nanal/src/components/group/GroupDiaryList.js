import { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import { onLogin } from '../../config/Login';
import axios_api from '../../config/Axios';
import GroupDiaryItem from './GroupDiaryItem';

function GroupDiaryList() {
  const location = useLocation();

  // 일기 데이터 받기
  const [diaryList, setDiaryList] = useState([]);

  // 그룹에 속한 일기 작성하기
  useEffect(() => {
    onLogin();
    axios_api
      .get(`diary/list/${location.state.groupDetail.groupIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          // 초기화 필요!
          setDiaryList(null);
          if (data.data.responseMessage === '일기 리스트 조회 성공') {
            setDiaryList(data.data.diary);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ err }) => {
        console.log('일기 리스트 불러오기 오류: ', err);
      });
  }, [location.state.groupDetail.groupIdx]);

  return (
    <div>
      <div>
        {diaryList.map((diary) => (
          <GroupDiaryItem key={diary.diaryIdx} item={diary} />
        ))}
      </div>
    </div>
  );
}

export default GroupDiaryList;
