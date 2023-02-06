import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios_api from '../../config/Axios';

// 친구 등록 = friend
// 그룹 초대 = group/join
// 새 글 알림 = 해당 글로 이동
// 새 댓글 알림 = 해달 글로 이동

function AlarmItem({
  content,
  noticeType,
  requestDiaryIdx,
  requestGroupIdx,
  requestUserIdx,
  userIdx,
  noticeIdx,
}) {
  // 새 글이나 댓글은 해당 글로 이동 시켜줘야함.
  const navigate = useNavigate();

  // 친구 등록 성공.
  const addFriend = () => {
    axios_api
      .post('friend', {
        friendIdx: requestUserIdx,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '친구 등록 성공') {
            alert('친구 등록에 성공하셨습니다.');
            navigate(`/Friend/List`);
          }
        } else {
          console.log('친구 등록 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('친구 등록 오류: ' + error);
      });
  };

  //그룹 초대 성공.
  const addGroup = () => {
    axios_api
      .post('group/join', {
        groupIdx: requestGroupIdx,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '그룹 가입 성공') {
            alert('그룹 가입 등록에 성공하셨습니다.');
            navigate(`/Group/List`);
          }
        } else {
          console.log('그룹 가입 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 가입 오류: ' + error);
      });
  };

  //알람 읽음 처리
  const checkAlarm = () => {
    axios_api
      .get(`notification/${noticeIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '알림 읽음 처리 성공') {
            Location.reload();
          }
        } else {
          console.log('알림 읽음 처리 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('알림 읽음 처리 오류: ' + error);
      });
  };

  //알람 읽음 처리 후 해당 글로.
  const linkedDiary = () => {
    axios_api
      .get(`notification/${noticeIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '알림 읽음 처리 성공') {
            navigate(`diary/${requestDiaryIdx}`);
          }
        } else {
          console.log('알림 읽음 처리 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('알림 읽음 처리 오류: ' + error);
      });
  };

  if (noticeType === 0) {
    return (
      <div>
        <div className='flex justify-between'>
          <p>{content}</p>
          <button className='grid content-start'>X</button>
        </div>
        <div className='flex justify-end'>
          <button
            onClick={() => {
              addFriend();
              checkAlarm();
            }}
          >
            수락
          </button>
          <button onClick={checkAlarm}>거절</button>
        </div>
      </div>
    );
  } else if (noticeType === 1) {
    return (
      <div>
        <div className='flex justify-between'>
          <p>{content}</p>
          <button className='grid content-start'>X</button>
        </div>
        <div className='flex justify-end'>
          <button
            onClick={() => {
              addGroup();
              checkAlarm();
            }}
          >
            수락
          </button>
          <button onClick={checkAlarm}>거절</button>
        </div>
      </div>
    );
  } else if (noticeType === 2) {
    return (
      <div>
        <div className='flex justify-between'>
          <p>{content}</p>
          <button className='grid content-start'>X</button>
        </div>
        <button onClick={linkedDiary} className='flex justify-end'>
          바로가기
        </button>
      </div>
    );
  } else if (noticeType === 3) {
    return (
      <div>
        <div className='flex justify-between'>
          <p>{content}</p>
          <button className='grid content-start'>X</button>
        </div>
        <button onClick={linkedDiary} className='flex justify-end'>
          바로가기
        </button>
      </div>
    );
  } else {
    return null;
  }
}

export default AlarmItem;
