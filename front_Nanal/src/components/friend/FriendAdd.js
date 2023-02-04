import React, { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import FriendItem from './FriendItem';

function FriendAdd() {
  const [friendList, setFriendList] = useState([]);

  const searchFriend = (e) => {
    e.preventDefault();
    const userIdx = e.target.searchId.value;

    axios_api
      .get(`search/user/${userIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '친구 조회 성공') {
            console.log(data.data);
          } else {
            console.log('친구 조회 오류 : ');
            console.log(data.statusCode);
            console.log(data.data.responseMessage);
          }
        }
      })
      .catch(({ error }) => {
        console.log('친구 조회 오류 : ' + error);
      });
  };

  useEffect(() => {
    onLogin();
  }, []);
  return (
    <div>
      <p>
        찾고자 하는 친구의 아이디를 <br />
        입력 후 검색해주세요
      </p>
      <form onSubmit={searchFriend}>
        <input type='text' id='searchId'></input>
        <button type='submit'>검색하기</button>
      </form>
      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />
      {friendList.map((friendItem) => (
        <FriendItem key={friendItem.userIdx} item={friendItem} />
      ))}
    </div>
  );
}
export default FriendAdd;
