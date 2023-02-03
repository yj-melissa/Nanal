import React, { useEffect } from 'react';
import { useState } from 'react';
import { getCookie } from '../../config/Cookie';
import AlarmList from './AlarmList';
import axios_api from '../../config/Axios';

// import { onLogin } from '../../config/Login';

const Alarm = () => {
  const [isAlarmList, setIsAlarmList] = useState([
    {
      checked: false,
      content: 'test1님이 fdsa 그룹 일기에 초대했습니다.',
      creationDate: '2023-02-02T01:45:30.095+00:00',
      noticeIdx: 13,
      noticeType: 0,
      requestDiaryIdx: 0,
      requestGroupIdx: 0,
      requestUserIdx: 1,
      userIdx: 2,
    },
  ]);

  const accessToken = getCookie('accessToken');
  axios_api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

  // DB에서 알람 리스트 다 땡겨와야함.
  useEffect(() => {
    // onLogin();
    axios_api
      .get('notification')
      .then(({ data }) => {
        // console.log(data.data.diary);
        setIsAlarmList(data.data.diary);
      })
      .catch((err) => console.log(err));
  }, []);
  console.log(isAlarmList);

  //알람은 최근 30일 것까지만...
  return (
    <div className='grid grid-cols-1 divide-y divide-slate-800'>
      <p className='text-xl text-center m-auto'>알람 목록</p>
      <div>
        {isAlarmList.map((ar) => (
          <AlarmList key={ar.noticeIdx} {...ar} />
        ))}
      </div>
    </div>
  );
};

export default Alarm;
