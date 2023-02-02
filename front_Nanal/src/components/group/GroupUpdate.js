import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function GroupUpdate() {
  const { state } = useLocation();

  const [groupName, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState([]);
  const [tagNum, setTagNum] = useState(0);
  let [tagNew, setTagNew] = useState('');
  const [groupFriendList, setGroupFriendList] = useState([]);
  const [friendList, setFriendList] = useState([]);
  const [includeFriend, setIncludeFriend] = useState([]);
  const [includeFriendIdx, setIncludeFriendIdx] = useState([]);

  // 그룹명
  const [currentGMessage, setCurrentGMessage] = useState('');
  const [isCurrentGName, setIsCurrentGName] = useState(false);
  const onChangeName = (e) => {
    const currentName = e.target.value;
    setGroupName(e.target.value);

    if (currentName.length >= 15) {
      setCurrentGMessage('그룹명은 15글자 이하로 입력해주세요!');
      setIsCurrentGName(false);
    } else {
      setCurrentGMessage('');
      setIsCurrentGName(true);
    }
  };

  // 태그명 작성 부분
  const [currentGTMessage, setCurrentGTMessage] = useState('');
  const [isCurrentGTName, setIsCurrentGTName] = useState(false);
  const onChangeTagNew = (e) => {
    const currentName = e.target.value;
    setTagNew(e.target.value);

    if (currentName.length >= 10) {
      setCurrentGTMessage('태그명은 10글자 이하로 입력해주세요!');
      setIsCurrentGTName(false);
    } else {
      setCurrentGTMessage('');
      setIsCurrentGTName(true);
    }
  };

  // 그룹 태그 추가
  function addTag(e) {
    e.preventDefault();

    if (groupTag.length === 5) {
      alert('태그는 5개까지만 가능합니다.');
      setTagNew('');
    } else if (tagNew !== '' && isCurrentGTName === true) {
      let tagList = [...groupTag];
      tagList.push(tagNew);
      setGroupTag(tagList);
      setTagNew('');
    }
  }

  // 그룹 태그 제거
  const onChangeTagRemove = (id) => {
    // tag.id 가 파라미터로 일치하지 않는 원소만 추출해서 새로운 배열을 만듬
    // = tag.id 가 id 인 것을 제거함
    let tagList = [...groupTag];
    tagList.splice(id, 1);
    setGroupTag(tagList);
  };

  // 초대할 사용자 추가
  const addFriend = (idx) => {
    if (!includeFriend.includes(friendList[idx])) {
      let addfriendList = [...includeFriend];
      addfriendList.push(friendList[idx]);
      setIncludeFriend(addfriendList);

      let addfriendListIdx = [...includeFriendIdx];
      addfriendListIdx.push(friendList[idx].userIdx);
      setIncludeFriendIdx(addfriendListIdx);
    }
  };

  // 초대할 사용자 제거
  const onChangeFRemove = (idx) => {
    let addfriendList = [...includeFriend];
    addfriendList.splice(idx, 1);
    setIncludeFriend(addfriendList);

    let addfriendListIdx = [...includeFriendIdx];
    addfriendListIdx.splice(idx, 1);
    setIncludeFriendIdx(addfriendListIdx);
  };

  // 그룹 생성 요청 함수
  const GroupCreate = (e) => {
    e.preventDefault();

    if (groupTag.length !== 5) {
      const len = 5 - groupTag.length;
      for (let i = 0; i < len; i++) {
        groupTag.push('');
      }
    }

    if (isCurrentGName === true) {
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

              const groupidx = data.data.groupDetail.groupIdx;

              if (includeFriend.size !== 0) {
                // 그룹에 추가할 친구가 있는 경우
                axios_api
                  .post('notification/group', {
                    request_group_idx: groupidx,
                    userIdx: includeFriendIdx,
                  })
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (data.data.responseMessage === '알림 저장 성공') {
                        window.location.replace('/Group/List');
                      }
                    } else {
                      console.log('알림 저장 오류 : ');
                      console.log(data.statusCode);
                      console.log(data.data.responseMessage);
                    }
                  })
                  .catch(({ error }) => {
                    console.log('알림 저장 오류 : ' + error);
                  });
              }
            }
          } else {
            console.log('그룹 수정 오류: ');
            console.log(data.statusCode);
            console.log(data.data.responseMessage);
          }
        })
        .catch(({ error }) => {
          console.log('그룹 수정 성공: ' + error);
        });
    }
  };

  useEffect(() => {
    onLogin();
    axios_api
      .get(`/group/${state.groupDetail}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupName(null);
          setGroupTag(null);
          if (data.data.responseMessage === '그룹 조회 성공') {
            setGroupName(data.data.groupDetail.groupName);
            setGroupTag(data.data.tags);
            const groupidx = data.data.groupDetail.groupIdx;

            axios_api
              .get(`group/user/${groupidx}`)
              .then(({ data }) => {
                if (data.statusCode === 200) {
                  setGroupFriendList(null);
                  if (data.data.responseMessage === '그룹 유저 조회 성공') {
                    setGroupFriendList(data.data.groupUserList);
                  } else if (data.data.responseMessage === '데이터 없음') {
                    const item = [
                      {
                        nickName: '아직은 친구가 없습니다.',
                      },
                    ];
                    setGroupFriendList(item);
                  }
                } else {
                  console.log('그룹 유저 조회 오류: ');
                  console.log(data.statusCode);
                  console.log(data.data.responseMessage);
                }
              })
              .catch(({ error }) => {
                console.log('그룹 유저 조회 오류: ' + error);
              });
          }
        } else {
          console.log('그룹 상세보기 조회 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 상세 보기 오류: ' + error);
      });
  }, []);

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
              placeholder={groupName}
            ></input>
            <p className='message'>{currentGMessage}</p>
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
            <p className='message'>{currentGTMessage}</p>
            {/* {groupTag.map((tagging) => {
            return (
              <button
                onClick={() => {
                  setTagNum(tagging.tagIdx);
                  onChangeTagRemove(tagging.tagIdx);
                }}
                key={tagging.tagIdx}
                className='mr-2'
              >
                #{tagging}
              </button>
            );
          })} */}
            {groupFriendList.map((friendItem, idx) => {
              return (
                <span key={idx} className='mr-2'>
                  {friendItem.nickName}
                </span>
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
                  className='mr-2'
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
        <hr className='border-solid border-1 border-slate-800 w-80 my-5' />

        <p className='mb-0.5'>내 친구 목록 -----------------------</p>

        {friendList.map((friendItem, idx) => {
          return (
            <button
              key={idx}
              onClick={() => {
                addFriend(idx);
              }}
              className='mr-2'
            >
              {friendItem.nickname}
            </button>
          );
        })}
      </div>
    </div>
  );
}

export default GroupUpdate;
