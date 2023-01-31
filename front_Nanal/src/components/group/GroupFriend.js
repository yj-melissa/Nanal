import React, { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function GroupFriend({ changeData, toggle }) {
  const [friendList, setFriendList] = useState([]);
  const [includeFriend, setIncludeFriend] = useState([]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`friend/list/`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setFriendList(null);
          if (data.data.responseMessage === '친구 리스트 조회 성공') {
            // console.log(data.data.friendList);
            setFriendList(data.data.friendList);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('친구 리스트 조회 오류: ' + error);
      });
  }, []);

  const addFriend = (idx) => {
    let addfriendList = [...includeFriend];
    addfriendList.push(friendList[idx]);
    setIncludeFriend(addfriendList);
  };

  const onChangeFRemove = (idx) => {
    let addfriendList = [...includeFriend];
    addfriendList.splice(idx, 1);
    setIncludeFriend(addfriendList);
  };

  const onChangeToggle = () => {
    toggle(true);
    changeData(includeFriend);
  };

  return (
    <div>
      {/* {friendList.map((friendItem) => (
        <FriendItem key={friendItem.userIdx} item={friendItem} />
      ))} */}

      <p className='mb-0.5'>✨ 추가 된 사용자 ✨</p>
      <button onClick={onChangeToggle}>사용자 추가 완료</button>
      <br />

      {includeFriend.map((friendItem, idx) => {
        return (
          <button
            key={idx}
            onClick={() => {
              onChangeFRemove(idx);
            }}
          >
            {friendItem.nickname}
          </button>
        );
      })}

      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />

      <p className='mb-0.5'>내 친구 목록 -----------------------</p>

      {friendList.map((friendItem, idx) => {
        return (
          <button
            key={idx}
            onClick={() => {
              addFriend(idx);
            }}
          >
            {friendItem.nickname}
          </button>
        );
      })}
    </div>
  );
}

export default GroupFriend;
