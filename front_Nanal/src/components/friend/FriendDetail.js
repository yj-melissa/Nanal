import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function FriendDetail() {
  const { state } = useLocation();

  const [friend, setFriend] = useState([]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`/friend/${state.friendIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setFriend(null);
          if (data.data.responseMessage === '친구 조회 성공') {
            setFriend(data.data.friend);
          }
        } else {
          console.log('친구 상세 보기 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('친구 상세 보기 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <div className='flex justify-evenly mt-5'>
        <img src={friend.img} className='w-28 h-28 p-1 rounded-full'></img>
        <p className='my-auto text-2xl font-bold p-1'>
          {friend.nickname} 님의
          <br />
          일기장
        </p>
      </div>
      <div className='my-3 flex justify-between'>
        <p>{friend.introduction}</p>
      </div>
      <hr className='border-slate-400/75 w-65 my-5' />
    </div>
  );
}

export default FriendDetail;
