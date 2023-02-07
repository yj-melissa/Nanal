import React, { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import AlarmItem from './AlarmItem';

function AlarmList() {
  const [isAlarmList, setIsAlarmList] = useState([]);

  // DB에서 알람 리스트 다 땡겨와야함.
  // noticeType
  // 0 : 친구 초대
  // 1 : 그룹 초대
  // 2 : 새 글
  // 3 : 새 댓글
  useEffect(() => {
    onLogin();
    axios_api
      .get('notification')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setIsAlarmList(null);
          if (data.data.responseMessage === '알림 조회 성공') {
            setIsAlarmList(data.data.diary);
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

  //알람은 최근 30일 것까지만...
  return (
    <div className='grid grid-cols-1 '>
      <p className='m-auto text-xl text-center'>알람 목록</p>
      <br />
      <div className='divide-y divide-current divide-dashed'>
        {isAlarmList.map((ar) => (
          <AlarmItem key={ar.noticeIdx} {...ar} />
        ))}
      </div>
    </div>
  );
}

export default AlarmList;
