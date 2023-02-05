import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import FriendItem from '../friend/FriendItem';

function GroupSetting() {
  const { state } = useLocation();

  const [groupDetail, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState([]);

  const [friendList, setFriendList] = useState([{}]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`/group/${state.groupIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupName(null);
          setGroupTag(null);
          if (data.data.responseMessage === '그룹 조회 성공') {
            setGroupName(data.data.groupDetail);
            setGroupTag(data.data.tags);
            const groupidx = data.data.groupDetail.groupIdx;

            axios_api
              .get(`group/user/${groupidx}`)
              .then(({ data }) => {
                if (data.statusCode === 200) {
                  setFriendList(null);
                  if (data.data.responseMessage === '그룹 유저 조회 성공') {
                    setFriendList(data.data.groupUserList);
                  } else if (data.data.responseMessage === '데이터 없음') {
                    setFriendList(['아직은 친구가 없습니다.']);
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
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 상세 보기 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <h1>그룹 상세 페이지</h1>

      <div>
        <p>{groupDetail.groupName}</p>

        {groupTag.map((tagging, idx) => {
          return <span key={idx}>#{tagging.tag}&nbsp;</span>;
        })}
      </div>
      <Link
        to={`/Group/Update/${groupDetail.groupIdx}`}
        state={{ groupDetail: groupDetail.groupIdx }}
      >
        <button>수정하기</button>
      </Link>

      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />

      {/* {friendList.map((friendItem, idx) => {
        return (
          <Link
            key={idx}
            to={`/Friend/${friendItem.userIdx}`}
            state={{ friendIdx: friendItem.userIdx }}
          >
            <div>
              {friendItem.img}
              <br />
              {friendItem.nickName}
              <br />
              {friendItem.introduction}
            </div>
          </Link>
        );
      })} */}
      {friendList.map((friendItem, idx) => (
        <FriendItem key={idx} item={friendItem} />
      ))}
    </div>
  );
}

export default GroupSetting;
