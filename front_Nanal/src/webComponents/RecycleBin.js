import React, { useEffect, useState } from 'react';
import axios_api from '../config/Axios';
import { onLogin } from '../config/Login';
import TrashItem from '../webComponents/another/TrashItem';
import Swal from 'sweetalert2';
import nmb from '../src_assets/img/bookmark-name/name-mark-blue.svg';
import diaryImgBlue from '../src_assets/img/diary-img/diary-img-blue.svg';
import styled from 'styled-components';

const Div = styled.div`
  overflow: scroll;
  &::-webkit-scrollbar {
    width: 8px;
    height: 8px;
    border-radius: 6px;
    background: rgba(255, 255, 255, 0.4);
  }
  &::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.3);
    border-radius: 6px;
  }
`;

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
            console.log(data.data.diary);
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
    <div className='relative w-[1440px] mx-auto'>
      <p className='absolute z-30 left-[330px] inset-y-28'>휴지통</p>
      <img src={nmb} className='absolute z-20 left-60 inset-y-20' />
      <img src={diaryImgBlue} className='absolute w-[1280px] z-10 left-12' />

      <div className='flex justify-center my-2'>
        <div className='absolute z-30 left-[280px] inset-y-48'>
          <button
            className='px-2 py-1 my-2 text-xl font-bold text-white bg-rose-500 hover:bg-rose-600 rounded-xl'
            onClick={() => {
              Swal.fire({
                text: '휴지통을 정말 비우시겠습니까?',
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
        <p className='absolute z-20 py-2 text-2xl font-bold text-center top-24 right-80'>
          휴지통에 일기가 {trashDiary.length}개 있습니다.
        </p>
      </div>
      <div className='absolute z-20 justify-between mt-5 inset-y-32 right-60'>
        <Div className='overflow-auto h-96'>
          {trashDiary.map((diary, idx) => (
            <TrashItem key={idx} {...diary} />
          ))}
        </Div>
      </div>
    </div>
  );
};

export default RecycleBin;
