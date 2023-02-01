import { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import TrashItem from './TrashItem';

function RecycleBin() {
  // 휴지통 내 일기 데이터 받기
  const [trashDiary, setTrashDiary] = useState([]);

  useEffect(() => {
    axios_api.get('diary/trashbin').then(({ data }) => {
      if (data.statusCode === 200) {
        // 초기화 필요!
        setTrashDiary(null);
        if (data.data.responseMessage === '일기 리스트 조회 성공') {
          setTrashDiary(data.data.diary); // 데이터는 response.data.data 안에 들어있음
        }
      } else {
        console.log(data.statusCode);
        console.log(data.data.responseMessage);
      }
    });
  }, [trashDiary]);

  return (
    <div>
      <h4>휴지통에 {trashDiary.length}개가 있습니다.</h4>
      <div>
        <button
          onClick={() => {
            axios_api.delete('diary/trashbin').then(({ data }) => {
              if (data.statusCode === 200) {
                if (data.data.responseMessage === '일기 삭제 성공') {
                  return;
                }
              } else {
                console.log(data.statusCode);
                console.log(data.data.responseMessage);
              }
            });
          }}
        >
          전체 삭제
        </button>
      </div>
      <div>
        {trashDiary.map((diary, idx) => (
          <TrashItem key={idx} {...diary} />
        ))}
      </div>
    </div>
  );
}

export default RecycleBin;
