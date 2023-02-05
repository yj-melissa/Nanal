import React, { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

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
            console.log(data.data.userInfo);
            setFriendList(data.data.userInfo);
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

  const addFriend = (e, useridx) => {
    e.preventDefault();

    axios_api
      .post(`notification/friend`, { userIdx: [useridx] })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '알림 저장 성공') {
            alert('친구에게 추가를 요청하였습니다!');
            window.location.reload(false);
          } else {
            console.log('알림 저장 오류 : ');
            console.log(data.statusCode);
            console.log(data.data.responseMessage);
          }
        }
      })
      .catch(({ error }) => {
        console.log('알림 저장 오류 : ' + error);
      });
  };

  useEffect(() => {
    onLogin();
  }, []);
  return (
    <div>
      <p className='flex justify-around box-border h-20 w-70 p-4'>
        찾고자 하는 친구의 아이디를 <br />
        입력 후 검색해주세요
      </p>
      <form
        onSubmit={searchFriend}
        className='flex justify-around h-18 w-70 p-1'
      >
        <input type='text' id='searchId'></input>
        <button type='submit'>검색하기</button>
      </form>
      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />
      {friendList.map((friendItem) => (
        <div key={friendItem.userIdx} className='my-2'>
          <p className='font-semibold'>친구의 </p>
          <p>닉네임 : {friendItem.userId}</p>
          <p>이메일 : {friendItem.email}</p>
          <p className='text-right'>
            <button
              type='button'
              onClick={(e) => addFriend(e, friendItem.userIdx)}
              className='font-semibold'
            >
              친구 추가하기
            </button>
          </p>
        </div>
      ))}
      <div></div>
    </div>
  );
}
export default FriendAdd;
