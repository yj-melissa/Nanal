// import { useState } from 'react';
import axios_api from '../../config/Axios';

function TrashItem({ diaryIdx, creationDate, content }) {
  const strDate = new Date(creationDate).toLocaleString();

  return (
    <div>
      {/* <div>
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
      </div> */}
      <div>일기 번호: {diaryIdx}</div>
      <span>그림 들어갈 자리</span>
      <div>작성 시간: {strDate}</div>
      <div>{content}</div>
      <div>
        <button
          onClick={() => {
            axios_api.put(`diary/trashbin/${diaryIdx}`).then(({ data }) => {
              if (data.statusCode === 200) {
                if (data.data.responseMessage === '일기 생성 성공') {
                  return;
                }
              } else {
                console.log(data.statusCode);
                console.log(data.data.responseMessage);
              }
            });
          }}
        >
          복구
        </button>
      </div>
    </div>
  );
}

export default TrashItem;
