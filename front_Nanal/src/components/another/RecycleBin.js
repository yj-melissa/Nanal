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
  }, []);

  return (
    <div>
      <div className='flex justify-center my-2'>
        <button
          className='px-2 py-1 text-white bg-rose-600 rounded-xl'
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
      <h4 className='py-2 font-bold text-center'>
        휴지통에 일기가 {trashDiary.length}개 있습니다.
      </h4>
      <div className='mt-5'>
        {trashDiary.map((diary, idx) => (
          <TrashItem key={idx} {...diary} />
        ))}
      </div>
    </div>
  );
}

export default RecycleBin;
