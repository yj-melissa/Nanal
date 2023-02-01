import React, { useEffect, useState } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function GroupCreate() {
  const [groupName, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState([]);
  const [tagNum, setTagNum] = useState(0);
  let [tagNew, setTagNew] = useState('');
  const [friendList, setFriendList] = useState([]);
  const [includeFriend, setIncludeFriend] = useState([]);

  const onChangeName = (e) => {
    setGroupName(e.target.value);
  };

  function addTag(e) {
    e.preventDefault();
    if (groupTag.length === 5) {
      alert('태그는 5개까지만 가능합니다.');
      setTagNew('');
    } else if (tagNew !== '') {
      let tagList = [...groupTag];
      tagList.push(tagNew);
      setGroupTag(tagList);
      setTagNew('');
    }
  }

  const onChangeTagNew = (e) => {
    setTagNew(e.target.value);
  };

  const onChangeTagRemove = (id) => {
    // tag.id 가 파라미터로 일치하지 않는 원소만 추출해서 새로운 배열을 만듬
    // = tag.id 가 id 인 것을 제거함
    let tagList = [...groupTag];
    tagList.splice(id, 1);
    setGroupTag(tagList);
  };

  const GroupCreate = (e) => {
    e.preventDefault();

    if (groupTag.length !== 5) {
      const len = 5 - groupTag.length;
      for (let i = 0; i < len; i++) {
        groupTag.push('');
      }
    }

    axios_api
      .post('/group', {
        groupName: groupName,
        tags: groupTag,
      })
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '그룹 생성 성공') {
            // console.log(data.data.groupDetail);
            // console.log(data.data.tags);
            alert('그룹을 생성하였습니다!');
            window.location.replace('/Group/List');
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 생성 성공: ' + error);
      });
  };

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
    if (!includeFriend.includes(friendList[idx])) {
      let addfriendList = [...includeFriend];
      addfriendList.push(friendList[idx]);
      setIncludeFriend(addfriendList);
    }
  };

  const onChangeFRemove = (idx) => {
    let addfriendList = [...includeFriend];
    addfriendList.splice(idx, 1);
    setIncludeFriend(addfriendList);
  };

  return (
    <div id='group-Profile'>
      <h2> 그룹 생성 </h2>
      <div>
        <form onSubmit={GroupCreate}>
          <div id='group-name-div'>
            <label htmlFor='group-name'>그룹 이름 : </label>
            <input
              type='text'
              id='group-name'
              className='font-bold m-0.5'
              onChange={onChangeName}
            ></input>
          </div>
          <div id='group-tag-div'>
            <label htmlFor='group-tag'>그룹 태그 : (5개까지 가능)</label>
            <input
              type='text'
              id='group-tag'
              onChange={onChangeTagNew}
              value={tagNew}
            />
            &nbsp;
            <button onClick={addTag}>추가</button>
            <br />
            {groupTag.map((tagging, idx) => {
              return (
                <button
                  onClick={() => {
                    setTagNum(idx);
                    onChangeTagRemove(idx);
                  }}
                  key={idx}
                >
                  #{tagging}&nbsp;
                </button>
              );
            })}
          </div>
          <div>
            <p className='my-1'>✨ 추가 된 사용자 ✨</p>
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
          </div>

          <button type='submit' className='my-2'>
            생성
          </button>
        </form>
      </div>

      <div id='group-Friend'>
        {/* {friendList.map((friendItem) => (
        <FriendItem key={friendItem.userIdx} item={friendItem} />
      ))} */}

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
    </div>
  );
}

export default GroupCreate;
