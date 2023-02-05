import React, { useEffect, useRef, useState } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function GroupUpdate() {
  const { state } = useLocation();

  const [groupIdx, setGroupIdx] = useState(0);
  const [groupName, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState([]);

  const currentName = useRef('');
  const currentTag = useRef(['', '', '', '', '']);

  const [groupFriendList, setGroupFriendList] = useState([]);
  const [groupNotFriendList, setGroupNotFriendList] = useState([]);
  const [includeFriend, setIncludeFriend] = useState([]);
  const [includeFriendIdx, setIncludeFriendIdx] = useState([]);

  // const currentFriend = useRef([{}]);

  // tag 변경 시 요청되는 함수
  const updateTag = (e, idx) => {
    const newTag = e.target.value;
    // currentTag.current[idx].tag = newTag;
    currentTag.current[idx] = newTag;

    // setGroupTag(
    //   groupTag.map((it) => (it.tagIdx === idx ? { ...it, tag: newTag } : it))
    // );
  };

  // 초대할 사용자 추가
  const addFriend = (idx) => {
    if (!includeFriend.includes(groupNotFriendList[idx])) {
      let addfriendList = [...includeFriend];
      addfriendList.push(groupNotFriendList[idx]);
      setIncludeFriend(addfriendList);

      let addfriendListIdx = [...includeFriendIdx];
      addfriendListIdx.push(groupNotFriendList[idx].userIdx);
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

  // 그룹 수정 요청 함수
  const GroupUpdate = (e) => {
    e.preventDefault();

    const isCurrentGTName = true;

    for (const idx in groupTag) {
      if (groupTag[idx].tag.length >= 10) {
        isCurrentGTName = false;
      }
    }

    if (groupName.length >= 15) {
      alert('그룹명은 15글자 이하로 입력해주세요!');
    } else if (isCurrentGTName === false) {
      alert('태그명은 10글자 이하로 입력해주세요!');
    } else {
      // setGroupName(currentName.current);
      // setGroupTag(currentTag.current);

      axios_api
        .put('/group', {
          groupIdx: groupIdx,
          // groupName: groupName,
          // tags: groupTag,
          groupName: currentName.current,
          tags: currentTag.current,
        })
        .then(({ data }) => {
          if (data.statusCode === 200) {
            if (data.data.responseMessage === '그룹 수정 성공') {
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
          currentName.current = [];
          if (data.data.responseMessage === '그룹 조회 성공') {
            // console.log(data.data.groupDetail);
            setGroupIdx(data.data.groupDetail.groupIdx);
            setGroupName(data.data.groupDetail.groupName);
            setGroupTag(data.data.tags);

            const groupidx = data.data.groupDetail.groupIdx;
            currentName.current = data.data.groupDetail.groupName;
            // currentTag.current = data.data.tags;

            for (const tagging in data.data.tags) {
              currentTag.current[tagging] = data.data.tags[tagging].tag;
            }

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

            axios_api
              .get(`friend/list/${groupidx}`)
              .then(({ data }) => {
                if (data.statusCode === 200) {
                  setGroupNotFriendList(null);
                  if (data.data.responseMessage === '친구 리스트 조회 성공') {
                    setGroupNotFriendList(data.data.friendList);
                  } else if (data.data.responseMessage === '데이터 없음') {
                    const item = [
                      {
                        nickName: '모든 친구에 그룹에 속해있습니다.',
                      },
                    ];
                    setGroupNotFriendList(item);
                  }
                } else {
                  console.log('그룹에 속하지 않은 친구 리스트 조회 오류: ');
                  console.log(data.statusCode);
                  console.log(data.data.responseMessage);
                }
              })
              .catch(({ error }) => {
                console.log(
                  '그룹에 속하지 않은 친구 리스트 조회 오류: ' + error
                );
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
      <h2> 그룹 수정 </h2>
      <div>
        <form onSubmit={GroupUpdate}>
          <div id='group-name-div'>
            <label htmlFor='group-name'>그룹 이름 : </label>
            <input
              type='text'
              id='group-name'
              className='font-bold m-0.5'
              defaultValue={currentName.current || ''}
              onChange={(e) => (currentName.current = e.target.value)}
            ></input>
          </div>
          <div id='group-tag-div'>
            {/* {groupTag.map((tagging) => {
              return (
                <input
                  type='text'
                  className='mr-2 mb-2'
                  key={tagging.tagIdx}
                  defaultValue={tagging.tag || ''}
                  onChange={(e) => {
                    updateTag(e, tagging.tagIdx);
                  }}
                ></input>
              );
            })} */}
            {currentTag.current.map((tagging, idx) => {
              return (
                <input
                  type='text'
                  className='mr-2 mb-2'
                  key={idx}
                  // defaultValue={tagging.tag || ''}
                  defaultValue={tagging || ''}
                  onChange={(e) => {
                    updateTag(e, idx);
                  }}
                ></input>
              );
            })}
          </div>
          <div>
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
                  type='button'
                  key={idx}
                  onClick={() => {
                    onChangeFRemove(idx);
                  }}
                  className='mr-2'
                >
                  {friendItem.nickName}
                </button>
              );
            })}
          </div>

          <button type='submit' className='my-2'>
            수정
          </button>
        </form>
      </div>

      <div id='group-Friend'>
        <hr className='border-solid border-1 border-slate-800 w-80 my-5' />

        <p className='mb-0.5'>내 친구 목록 -----------------------</p>

        {groupNotFriendList.map((friendItem, idx) => {
          return (
            <button
              type='button'
              key={idx}
              onClick={() => {
                addFriend(idx);
              }}
              className='mr-2'
            >
              {friendItem.nickName}
            </button>
          );
        })}
      </div>
    </div>
  );
}

export default GroupUpdate;
