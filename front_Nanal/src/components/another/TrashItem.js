// import { useState } from 'react';
import axios_api from '../../config/Axios';
import emo_joy from '../../src_assets/img/emo_joy.png';
import Swal from 'sweetalert2';

function TrashItem({ diaryIdx, diaryDate, content }) {
  // 일기 복구하기
  const recovery = () => {
    Swal.fire({
      title: `일기를 정말 복구하시겠습니까?`,
      text: '복구한 일기는 리스트에서 확인 가능합니다.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '복구',
      cancelButtonText: '취소',
    }).then((result) => {
      if (result.isConfirmed) {
        axios_api
          .put(`diary/trashbin/${diaryIdx}`)
          .then(({ data }) => {
            if (data.statusCode === 200) {
              if (data.data.responseMessage === '일기 복구 성공') {
                window.location.reload();
              }
            } else {
              console.log('일기 복구 실패 : ');
              console.log(data.statusCode);
              console.log(data.data.responseMessage);
            }
          })
          .catch(({ error }) => {
            console.log('일기 복구 오류: ' + error);
          });
      }
    });
  };

  return (
    <div className='my-2'>
      <div className='flex p-2 m-1 mb-3 item-center'>
        <img
          src={emo_joy}
          alt='DALL:E2'
          className='w-16 h-16 p-1 rounded-lg hover:translate-y-2'
        />
        <div className='w-9/12 px-1 m-1 text-sm '>
          <div className='flex justify-between mb-2'>
            <span className='text-sm'>{diaryDate}</span>
            <button
              className='bg-cyan-600  text-white px-2.5 py-1 rounded-xl'
              onClick={recovery}
            >
              복구
            </button>
          </div>
          <p className='w-11/12 mt-1 font-bold truncate'>{content}</p>
        </div>
      </div>
    </div>
  );
}

export default TrashItem;
