import React, { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';

function GroupFriend() {
  const [friendList, setFriendList] = useState([]);
  const [includeFriend, setIncludeFriend] = useState([]);
  const userIdx = 1;

  useEffect(() => {
    axios_api
      .get(`friend/list/${userIdx}`)
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
    console.log(friendList[idx]);
    addfriendList.push(friendList[idx]);
    console.log(addfriendList);
    console.log(includeFriend);
    setIncludeFriend(addfriendList);
    console.log(includeFriend);
  };

  return (
    <div>
      {/* {friendList.map((friendItem) => (
        <FriendItem key={friendItem.userIdx} item={friendItem} />
      ))} */}

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

      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />

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
