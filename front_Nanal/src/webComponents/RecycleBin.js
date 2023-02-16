import React, { useEffect, useState } from 'react';
import axios_api from '../config/Axios';
import { onLogin } from '../config/Login';
import TrashItem from '../webComponents/another/TrashItem';
import Swal from 'sweetalert2';
import nmb from '../src_assets/img/bookmark-name/name-mark-blue.svg';
import diaryImgBlue from '../src_assets/img/diary-img/diary-img-blue.svg';

const RecycleBin = () => {
  // 휴지통 내 일기 데이터 받기
  const [trashDiary, setTrashDiary] = useState([]);

  const totalDelete = () => {
    axios_api
      .delete('diary/trashbin')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '일기 삭제 성공') {
            window.location.reload();
          }
        } else {
          console.log('일기 전체 삭제 오류');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('일기 전체 삭제 오류: ' + error);
      });
  };

  useEffect(() => {
    onLogin();
    axios_api
      .get('diary/trashbin')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          // 초기화 필요!
          setTrashDiary(null);
          if (data.data.responseMessage === '일기 리스트 조회 성공') {
            setTrashDiary(data.data.diary); // 데이터는 response.data.data 안에 들어있음
          }
        } else {
          console.log('알림 리스트 조회 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('알림 리스트 조회 오류: ' + error);
      });
  }, []);

  return (
    <div className='absolute w-[1440px] mx-auto'>
      <p className='absolute z-30 left-[330px] inset-y-36'>휴지통</p>
      <img src={nmb} alt='bg' className='absolute z-20 left-60 inset-y-28' />
      <img
        src={diaryImgBlue}
        alt='bg'
        className='absolute w-[1280px] z-10 left-12 top-8'
      />

      <div className='flex justify-center my-2'>
        {trashDiary.length > 0 ? (
          <div className='absolute z-30 left-[240px] inset-y-72'>
            <button
              className='px-2 py-1 my-2 text-lg font-bold text-white bg-rose-500 hover:bg-rose-600 rounded-xl'
              onClick={() => {
                Swal.fire({
                  title: '휴지통 비우기',
                  text: '휴지통에서 삭제한 일기들은 복구하실 수 없습니다. 정말 휴지통을 비우시겠습니까?',
                  icon: 'warning',
                  showCancelButton: true,
                  confirmButtonColor: '#d33',
                  cancelButtonColor: '#3085d6',
                  confirmButtonText: '삭제',
                  cancelButtonText: '취소',
                }).then((result) => {
                  if (result.isConfirmed) {
                    totalDelete();
                  }
                });
              }}
            >
              전체 삭제
            </button>
          </div>
        ) : null}

        <p className='absolute z-30 left-[200px] inset-y-60 text-2xl font-bold'>
          휴지통에 일기가 {trashDiary.length}개 있습니다.
        </p>
      </div>
      <div className='absolute z-20 justify-between mt-5 inset-y-28 right-60'>
        <div className='pr-2 overflow-auto h-96'>
          {trashDiary.map((diary, idx) => (
            <TrashItem key={idx} {...diary} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default RecycleBin;
