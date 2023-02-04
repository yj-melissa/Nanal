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
      {friend.nickname}
      <br />
      {friend.img}
      <br />
      {friend.introduction}
      <br />
      {friend.shortContent}
      <br />
      <br />
    </div>
  );
}

export default FriendDetail;
