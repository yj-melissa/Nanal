// import { useState } from 'react';
import axios_api from '../../config/Axios';
import emo_joy from '../../src_assets/img/emo_joy.png';

function TrashItem({ diaryIdx, diaryDate, content }) {
  return (
    <div className='my-2'>
      <div className='flex p-2 m-1 mb-3 item-center'>
        <img src={emo_joy} alt='DALL:E2' className='w-16 h-16 p-1 rounded-lg' />
        <div className='px-1 m-1 text-sm max-w-160'>
          <div className='flex justify-between'>
            <span className='mb-2 text-sm'>{diaryDate}</span>
            <span>
              <button
                className='bg-cyan-600 text-white px-2.5 py-1 rounded-xl'
                onClick={() => {
                  axios_api
                    .put(`diary/trashbin/${diaryIdx}`)
                    .then(({ data }) => {
                      if (data.statusCode === 200) {
                        if (data.data.responseMessage === '일기 복구 성공') {
                          return;
                        }
                      } else {
                        console.log('일기 복구 실패 : ');
                        console.log(data.statusCode);
                        console.log(data.data.responseMessage);
                      }
                    });
                }}
              >
                복구
              </button>
            </span>
          </div>
          <strong className='truncate ... mt-2'>{content}</strong>
        </div>
      </div>
    </div>
  );
}

export default TrashItem;
