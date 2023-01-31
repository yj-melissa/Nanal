import React from 'react';
import { useState } from 'react';
import AlarmList from './AlarmList';

// DB에서 알람 리스트 다 땡겨와야함.
const Alarm = () => {
  const [arr, setArr] = useState([0, 1, 2]);
  //알람은 최근 30일 것까지만...
  return (
    <div className='grid grid-cols-1 divide-y divide-slate-800'>
      <p className='text-xl text-center m-auto'>알람 목록</p>
      <div>
        {arr.map((ar) => {
          // console.log(ar);
          <AlarmList key={ar} />;
        })}
      </div>
    </div>
  );
};

export default Alarm;
