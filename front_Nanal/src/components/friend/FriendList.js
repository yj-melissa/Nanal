import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import FriendItem from './FriendItem';

function FriendList() {
  const [friendList, setFriendList] = useState([]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`friend/list`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setFriendList(null);
          if (data.data.responseMessage === '친구 리스트 조회 성공') {
            setFriendList(data.data.friendList);
          } else if (data.data.responseMessage === '데이터 없음') {
            setFriendList(['아직은 친구가 없습니다.']);
          }
        } else {
          console.log('친구 리스트 조회 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('친구 리스트 조회 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <h1 className='m-1 font-bold'>친구 리스트 조회</h1>
      <Link to='/Friend/Add'>
        <button type='button'>친구 추가하기</button>
      </Link>

      {friendList.map((friendItem) => (
        <FriendItem key={friendItem.userIdx} item={friendItem} />
      ))}
    </div>
  );
}

export default FriendList;
